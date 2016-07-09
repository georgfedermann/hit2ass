package org.poormanscastle.products.hit2ass.renderer;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.poormanscastle.products.hit2ass.ast.domain.AssignmentStatement;
import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBausteinImpl;
import org.poormanscastle.products.hit2ass.ast.domain.ConditionalStatement;
import org.poormanscastle.products.hit2ass.ast.domain.DynamicValue;
import org.poormanscastle.products.hit2ass.ast.domain.FixedText;
import org.poormanscastle.products.hit2ass.ast.domain.GlobalDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.HitCommandStatement;
import org.poormanscastle.products.hit2ass.ast.domain.IncludeBausteinStatement;
import org.poormanscastle.products.hit2ass.ast.domain.LocalDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.PrintStatement;
import org.poormanscastle.products.hit2ass.renderer.domain.CarriageReturn;
import org.poormanscastle.products.hit2ass.renderer.domain.Container;
import org.poormanscastle.products.hit2ass.renderer.domain.DocumentVariable;
import org.poormanscastle.products.hit2ass.renderer.domain.DynamicContentReference;
import org.poormanscastle.products.hit2ass.renderer.domain.IfElseParagraph;
import org.poormanscastle.products.hit2ass.renderer.domain.IfThenElseParagraph;
import org.poormanscastle.products.hit2ass.renderer.domain.IfThenParagraph;
import org.poormanscastle.products.hit2ass.renderer.domain.Paragraph;
import org.poormanscastle.products.hit2ass.renderer.domain.Text;
import org.poormanscastle.products.hit2ass.renderer.domain.Workspace;

import java.util.Stack;

/**
 * Iterates an AST representing a HIT/CLOU text component and converts it into an
 * Intermediate Representation tree, one more step into the direction of a
 * DocDesign workspace. The IR-tree is a hierarchy built from renderer.domain objects like
 * workspace, paragraph, IfElseParagraph, etc. In the end, the workspace object can be
 * serialized to string using its getContent() method. This string contains the acr XML
 * that can be imported into DocDesign as a DocDesign Workspace.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class IRTransformer extends AstItemVisitorAdapter {

    private final static Logger logger = Logger.getLogger(IRTransformer.class);

    /**
     * While iterating over the HIT/CLOU AST, the renderer will keep a reference
     * to the latest encountered container here, until it is left again.
     */
    private final Stack<Container> containerStack = new Stack<>();

    private Workspace workspace;

    public IRTransformer() {
        super(true);
        initializeVelocity();
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    private final void initializeVelocity() {
        Velocity.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
        Velocity.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        Velocity.init();
    }

    @Override
    public boolean proceedWithConditionalStatement(ConditionalStatement conditionalStatement) {
        IfThenElseParagraph ifParagraph = new IfThenElseParagraph(StringUtils.join(
                "IF-", StringEscapeUtils.escapeXml10(conditionalStatement.getCondition().toXPathString())), conditionalStatement.getCondition());
        containerStack.peek().addContent(ifParagraph);

        if (conditionalStatement.getThenElement() != null) {
            IRTransformer transformer = new IRTransformer();
            transformer.containerStack.push(new IfThenParagraph("THEN"));
            conditionalStatement.getThenElement().accept(transformer);
            ifParagraph.addContent(transformer.containerStack.pop());
        } else {
            ifParagraph.addContent(new IfThenParagraph("THEN EMPTY"));
        }
        if (conditionalStatement.getElseElement() != null) {
            IRTransformer transformer = new IRTransformer();
            transformer.containerStack.push(new IfElseParagraph("ELSE"));
            conditionalStatement.getElseElement().accept(transformer);
            ifParagraph.addContent(transformer.containerStack.pop());
        }
        return false;
    }

    @Override
    public void visitHitCommandStatement(HitCommandStatement hitCommandStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found HitCommandStatement ", hitCommandStatement.toString(),
                    " at ", hitCommandStatement.getCodePosition()));
        }
        switch (hitCommandStatement.getHitCommand()) {
            case RETURN:
                containerStack.peek().addContent(new CarriageReturn("NL", hitCommandStatement.getRepetitor()));
                break;
        }
    }

    /**
     * e.g. #D firstName ""
     *
     * @param globalDeclarationStatement
     */
    @Override
    public void visitGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement) {
        containerStack.peek().addContent(new DocumentVariable(globalDeclarationStatement.getId(),
                StringUtils.join("'", globalDeclarationStatement.getId(), "'"), globalDeclarationStatement.getExpression().toXPathString()));
    }

    /**
     * e.g. #d firstName ""
     *
     * @param localDeclarationStatement
     */
    @Override
    public void visitLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement) {
        containerStack.peek().addContent(new DocumentVariable(localDeclarationStatement.getId(),
                StringUtils.join("'", localDeclarationStatement.getId(), "'"), localDeclarationStatement.getExpression().toXPathString()));
    }

    /**
     * e.g. #X< firstName
     *
     * @param dynamicValue
     */
    @Override
    public void visitDynamicValue(DynamicValue dynamicValue) {
        containerStack.peek().addContent(new DynamicContentReference(
                StringUtils.join("Assign from Userdata XML: ", dynamicValue.getName()),
                StringUtils.join("var:write('", dynamicValue.getName(),
                        "', /UserData/payload/line[@lineNr = var:read('hit2ass_xml_sequence')]) | var:write('hit2ass_xml_sequence', var:read('hit2ass_xml_sequence') + 1)")));
    }

    @Override
    public void visitAssignmentStatement(AssignmentStatement assignmentStatement) {
        containerStack.peek().addContent(new DynamicContentReference(
                StringUtils.join("Assignment: ", assignmentStatement.getId()),
                StringUtils.join("var:write('", assignmentStatement.getId(),
                        "', ", assignmentStatement.getExpression().toXPathString(), ")")
        ));
    }

    @Override
    public void visitPrintStatement(PrintStatement printStatement) {
//        containerStack.peek().addContent(new DynamicContentReference(printStatement.getSymbolId(),
//                StringUtils.join("/xml/", printStatement.getSymbolId())));
        containerStack.peek().addContent(new DynamicContentReference(printStatement.getSymbolId(),
                StringUtils.join("var:read('", printStatement.getSymbolId(), "')")));
    }

    @Override
    public void visitFixedText(FixedText fixedText) {
        containerStack.peek().addContent(new Text("text", fixedText.getText()));
    }

    @Override
    public void visitClouBaustein(ClouBausteinImpl clouBaustein) {
        workspace = new Workspace();
        containerStack.push(new Paragraph("CLOU Component Paragraph"));
    }

    @Override
    public void leaveClouBaustein(ClouBausteinImpl clouBaustein) {
        workspace.setContentContainer(containerStack.pop());
    }

    @Override
    public void visitIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found IncludeBausteinStatement ", includeBausteinStatement.getPathToBaustein()));
        }
    }
}

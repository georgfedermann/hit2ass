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
import org.poormanscastle.products.hit2ass.ast.domain.CodePosition;
import org.poormanscastle.products.hit2ass.ast.domain.ConditionalStatement;
import org.poormanscastle.products.hit2ass.ast.domain.DynamicValue;
import org.poormanscastle.products.hit2ass.ast.domain.ExpressionList;
import org.poormanscastle.products.hit2ass.ast.domain.FixedText;
import org.poormanscastle.products.hit2ass.ast.domain.ForStatement;
import org.poormanscastle.products.hit2ass.ast.domain.GlobalDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.GlobalListDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.HitCommandStatement;
import org.poormanscastle.products.hit2ass.ast.domain.IncludeBausteinStatement;
import org.poormanscastle.products.hit2ass.ast.domain.LastExpressionList;
import org.poormanscastle.products.hit2ass.ast.domain.LocalDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.MacroCallStatement;
import org.poormanscastle.products.hit2ass.ast.domain.NumExpression;
import org.poormanscastle.products.hit2ass.ast.domain.PairExpressionList;
import org.poormanscastle.products.hit2ass.ast.domain.PrintStatement;
import org.poormanscastle.products.hit2ass.ast.domain.SectionStatement;
import org.poormanscastle.products.hit2ass.renderer.domain.CarriageReturn;
import org.poormanscastle.products.hit2ass.renderer.domain.Container;
import org.poormanscastle.products.hit2ass.renderer.domain.DocumentVariable;
import org.poormanscastle.products.hit2ass.renderer.domain.DynamicContentReference;
import org.poormanscastle.products.hit2ass.renderer.domain.FontWeight;
import org.poormanscastle.products.hit2ass.renderer.domain.ForLoop;
import org.poormanscastle.products.hit2ass.renderer.domain.IfElseParagraph;
import org.poormanscastle.products.hit2ass.renderer.domain.IfThenElseParagraph;
import org.poormanscastle.products.hit2ass.renderer.domain.IfThenParagraph;
import org.poormanscastle.products.hit2ass.renderer.domain.ListAddItem;
import org.poormanscastle.products.hit2ass.renderer.domain.ListDeclaration;
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

    private FontWeight fontWeight = FontWeight.INHERIT;

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

    /**
     * At different places, the transformer will spin off child transformers
     * that will transform certain subtrees like the bodies of THEN statements
     * etc. To keep configurations like fontweight, the spin-off transformer will
     * inherit its parents configuration.
     *
     * @return
     */
    private IRTransformer spinOff() {
        IRTransformer transformer = new IRTransformer();
        transformer.fontWeight = fontWeight;
        return transformer;
    }

    @Override
    public boolean proceedWithForStatement(ForStatement forStatement) {
        ForLoop forLoop = new ForLoop(StringUtils.join("FOR-", StringEscapeUtils.escapeXml10(forStatement.getRepetitionCount().toXPathString())),
                forStatement.getRepetitionCount());
        containerStack.peek().addContent(forLoop);

        IRTransformer transformer = spinOff();
        transformer.containerStack.push(forLoop);
        forStatement.getForBody().accept(transformer);
        // the visit logic gets handled in the proceedWith method, so it returns "false" so that
        // the visit method won't get called.
        return false;
    }

    @Override
    public boolean proceedWithConditionalStatement(ConditionalStatement conditionalStatement) {
        IfThenElseParagraph ifParagraph = new IfThenElseParagraph(StringUtils.join(
                "IF-", StringEscapeUtils.escapeXml10(conditionalStatement.getCondition().toXPathString())), conditionalStatement.getCondition());
        containerStack.peek().addContent(ifParagraph);

        if (conditionalStatement.getThenElement() != null) {
            IRTransformer transformer = spinOff();
            transformer.containerStack.push(new IfThenParagraph("THEN"));
            conditionalStatement.getThenElement().accept(transformer);
            ifParagraph.addContent(transformer.containerStack.pop());
        } else {
            ifParagraph.addContent(new IfThenParagraph("THEN EMPTY"));
        }
        if (conditionalStatement.getElseElement() != null) {
            IRTransformer transformer = spinOff();
            transformer.containerStack.push(new IfElseParagraph("ELSE"));
            conditionalStatement.getElseElement().accept(transformer);
            ifParagraph.addContent(transformer.containerStack.pop());
        }
        // the visit logic gets handled in the proceedWith method, so it returns "false" so that
        // the visit method won't get called.
        return false;
    }

    @Override
    public void visitMacroCallStatement(MacroCallStatement macroCallStatement) {
        // TODO short-cut: implement a set of specific MACRO definitions.
        // In this implementation you're restricted to usage of those macros.
        // This also impacts how you can use functions textattribut, absatzattribut
        // and sonderzeichenzeile, since they are only indirectly implemented by
        // the algorithms implemented or quoted in this method.
        if (macroCallStatement.getMacroId().equals("FEEIN")) {
            fontWeight = FontWeight.BOLD;
        } else if (macroCallStatement.getMacroId().equals("FEAUS")) {
            fontWeight = FontWeight.INHERIT;
        }
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
        containerStack.peek().addContent(new DocumentVariable(StringUtils.join("Global Variable: ", globalDeclarationStatement.getId()),
                StringUtils.join("'", globalDeclarationStatement.getId(), "'"), globalDeclarationStatement.getExpression().toXPathString()));
    }

    @Override
    public void visitGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement) {
        // add list declaration statement
        containerStack.peek().addContent(new ListDeclaration(StringUtils.join("Listdeclaration - ", globalListDeclarationStatement.getListId()),
                globalListDeclarationStatement.getListId()));
        // then add list initialization
        if (globalListDeclarationStatement.getListExpression() != null && globalListDeclarationStatement.getListExpression() instanceof ExpressionList) {
            ExpressionList expressionList = (ExpressionList) globalListDeclarationStatement.getListExpression();
            while (expressionList != null) {
                if (expressionList instanceof PairExpressionList) {
                    PairExpressionList pairExpressionList = (PairExpressionList) expressionList;
                    containerStack.peek().addContent(
                            new ListAddItem("AddItem", globalListDeclarationStatement.getListId(), pairExpressionList.getHead().toXPathString()));
                    expressionList = pairExpressionList.getTail();
                } else if (expressionList instanceof LastExpressionList) {
                    containerStack.peek().addContent(
                            new ListAddItem("AddIem", globalListDeclarationStatement.getListId(), expressionList.toXPathString()));
                    expressionList = null;
                }
            }
        }
    }

    @Override
    public void visitSectionStatement(SectionStatement sectionStatement) {
        containerStack.peek().addContent(new CarriageReturn("NL", new NumExpression(CodePosition.createZeroPosition(), 1)));
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
                        "', /UserData/payload/line[@lineNr = var:read('hit2ass_xml_sequence')]) | var:write('hit2ass_xml_sequence', var:read('hit2ass_xml_sequence') + 1)"),
                fontWeight)
        );
    }

    @Override
    public void visitAssignmentStatement(AssignmentStatement assignmentStatement) {
        containerStack.peek().addContent(new DynamicContentReference(
                StringUtils.join("Assignment: ", assignmentStatement.getIdExpression().getId()),
                StringUtils.join("var:write('", assignmentStatement.getIdExpression().toXPathString(),
                        "', ", assignmentStatement.getExpression().toXPathString(), ")"),
                fontWeight
        ));
    }

    @Override
    public void visitPrintStatement(PrintStatement printStatement) {
        containerStack.peek().addContent(new DynamicContentReference(
                StringUtils.join("Print: ", printStatement.getIdExpression().getId()),
                printStatement.getIdExpression().toXPathString(), fontWeight));
    }

    @Override
    public void visitFixedText(FixedText fixedText) {
        containerStack.peek().addContent(new Text("text", fixedText.getText(), fontWeight));
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

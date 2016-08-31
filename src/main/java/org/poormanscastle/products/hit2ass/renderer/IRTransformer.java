package org.poormanscastle.products.hit2ass.renderer;

import java.util.Stack;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.poormanscastle.products.hit2ass.ast.domain.AssignmentStatement;
import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.BinaryOperator;
import org.poormanscastle.products.hit2ass.ast.domain.BinaryOperatorExpression;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBausteinImpl;
import org.poormanscastle.products.hit2ass.ast.domain.ClouFunctionCall;
import org.poormanscastle.products.hit2ass.ast.domain.CodePosition;
import org.poormanscastle.products.hit2ass.ast.domain.ConditionalStatement;
import org.poormanscastle.products.hit2ass.ast.domain.DynamicValue;
import org.poormanscastle.products.hit2ass.ast.domain.Expression;
import org.poormanscastle.products.hit2ass.ast.domain.FixedText;
import org.poormanscastle.products.hit2ass.ast.domain.ForStatement;
import org.poormanscastle.products.hit2ass.ast.domain.GlobalDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.GlobalListDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.HitCommandStatement;
import org.poormanscastle.products.hit2ass.ast.domain.IdExpression;
import org.poormanscastle.products.hit2ass.ast.domain.IncludeBausteinStatement;
import org.poormanscastle.products.hit2ass.ast.domain.ListConcatenationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.LocalDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.LocalListDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.MacroCallStatement;
import org.poormanscastle.products.hit2ass.ast.domain.NumExpression;
import org.poormanscastle.products.hit2ass.ast.domain.PrintStatement;
import org.poormanscastle.products.hit2ass.ast.domain.SectionStatement;
import org.poormanscastle.products.hit2ass.ast.domain.TextExpression;
import org.poormanscastle.products.hit2ass.ast.domain.WhileStatement;
import org.poormanscastle.products.hit2ass.renderer.domain.CarriageReturn;
import org.poormanscastle.products.hit2ass.renderer.domain.Container;
import org.poormanscastle.products.hit2ass.renderer.domain.Content;
import org.poormanscastle.products.hit2ass.renderer.domain.DynamicContentReference;
import org.poormanscastle.products.hit2ass.renderer.domain.FontWeight;
import org.poormanscastle.products.hit2ass.renderer.domain.ForLoop;
import org.poormanscastle.products.hit2ass.renderer.domain.IfElseParagraph;
import org.poormanscastle.products.hit2ass.renderer.domain.IfThenElseParagraph;
import org.poormanscastle.products.hit2ass.renderer.domain.IfThenParagraph;
import org.poormanscastle.products.hit2ass.renderer.domain.ListDeclaration;
import org.poormanscastle.products.hit2ass.renderer.domain.Paragraph;
import org.poormanscastle.products.hit2ass.renderer.domain.Text;
import org.poormanscastle.products.hit2ass.renderer.domain.TextAlignment;
import org.poormanscastle.products.hit2ass.renderer.domain.WhileLoopFlagValueFlavor;
import org.poormanscastle.products.hit2ass.renderer.domain.Workspace;
import org.poormanscastle.products.hit2ass.renderer.domain.WorkspaceContainer;

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

    private boolean insideWhileLoop = false;

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
        transformer.insideWhileLoop = insideWhileLoop;
        return transformer;
    }

    @Override
    public void visitClouBaustein(ClouBausteinImpl clouBaustein) {
        workspace = new Workspace();
        containerStack.push(new Paragraph("CLOU Component Paragraph"));
    }

    @Override
    public void leaveClouBaustein(ClouBausteinImpl clouBaustein) {
        WorkspaceContainer workspaceContainer = new WorkspaceContainer();
        for (Container container : containerStack) {
            workspaceContainer.addContent(container);
        }
        workspace.setContentContainer(workspaceContainer);
    }

    @Override
    public boolean proceedWithForStatement(ForStatement forStatement) {
        ForLoop forLoop = new ForLoop(StringUtils.join("FOR:", StringEscapeUtils.escapeXml10(forStatement.getRepetitionCount().toXPathString())),
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
    public boolean proceedWithWhileStatement(WhileStatement whileStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found WhileStatement ", whileStatement.toString(),
                    " at ", whileStatement.getCodePosition()));
        }

        // This is one of the more challenging topics when transforming HIT/CLOU to XSLT/DocFamily flavor.
        // This algorithms covers the following WHILE loop flavors:
        // while (someStringVar <> TextExpression)
        // while (someNumVar <= NumExpression or ClouFunctionCall)
        Expression condition = whileStatement.getCondition();
        if (!(condition instanceof BinaryOperatorExpression)) {
            throw new IllegalStateException(StringUtils.join("Only BinaryOperatorExpressions are supported here, not this such: ", condition.toString()));
        }
        BinaryOperatorExpression loopCondition = (BinaryOperatorExpression) condition;
        // the following are not sure fire checks, but they depict the situations found in the legacy code base.
        if ((loopCondition.getLhs() instanceof IdExpression && loopCondition.getRhs() instanceof TextExpression) ||
                (loopCondition.getLhs() instanceof TextExpression && loopCondition.getRhs() instanceof IdExpression)) {
            // handle this as WhileFlagValueFlavor
            insideWhileLoop = true;
            WhileLoopFlagValueFlavor whileLoop = new WhileLoopFlagValueFlavor("WHILE loop flag value flavor",
                    whileStatement.getCondition());
            containerStack.peek().addContent(whileLoop);

            IRTransformer transformer = spinOff();
            transformer.containerStack.push(whileLoop);
            whileStatement.getWhileBody().accept(transformer);
            // the visitor logic gets handled in the proceedWith method.
            // Thus it returns "false" here so that the visit methods won't get called.
            insideWhileLoop = false;
        } else if ((loopCondition.getLhs() instanceof IdExpression && (loopCondition.getRhs() instanceof ClouFunctionCall || loopCondition.getRhs() instanceof NumExpression))
                || (loopCondition.getLhs() instanceof ClouFunctionCall || loopCondition.getLhs() instanceof NumExpression) && loopCondition.getRhs() instanceof IdExpression) {
            // handle this as for loop.
            // e.g. var = 3; while var < 12 ...; then the applicable number expression is 12 - var + 1
            // (assuming that var will be incremented by one on each iteration)
            BinaryOperatorExpression forLoopCondition = null;
            if (loopCondition.getOperator() == BinaryOperator.LT || loopCondition.getOperator() == BinaryOperator.LTE) {
                forLoopCondition = new BinaryOperatorExpression(CodePosition.createZeroPosition(), loopCondition.getRhs(), BinaryOperator.MINUS, loopCondition.getLhs());
                if (loopCondition.getOperator() == BinaryOperator.LTE) {
                    forLoopCondition = new BinaryOperatorExpression(CodePosition.createZeroPosition(), new NumExpression(CodePosition.createZeroPosition(), 1),
                            BinaryOperator.PLUS, forLoopCondition);
                }
            }
            ForLoop forLoop = new ForLoop(StringUtils.join("WHILE(FOR_flavor):", StringEscapeUtils.escapeXml10(whileStatement.getCondition().toXPathString())),
                    forLoopCondition);

            containerStack.peek().addContent(forLoop);
            IRTransformer transformer = spinOff();
            transformer.containerStack.push(forLoop);
            whileStatement.getWhileBody().accept(transformer);
        }

        return false;
    }

    @Override
    public boolean proceedWithConditionalStatement(ConditionalStatement conditionalStatement) {
        IfThenElseParagraph ifParagraph = new IfThenElseParagraph("IF", conditionalStatement.getCondition());
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
        } else if (macroCallStatement.getMacroId().equals("TABU")) {
            containerStack.peek().addContent(new Text("TABU", "       ", fontWeight));
        } else if (macroCallStatement.getMacroId().equals("ZLTZ12")) {
            containerStack.push(new Paragraph("TextAlignment Center", TextAlignment.CENTER));
        } else if (macroCallStatement.getMacroId().equals("ZLTB12")) {
            containerStack.push(new Paragraph("TextAlignment Justified", TextAlignment.JUSTIFIED));
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

    @Override
    public void visitListConcatenationStatement(ListConcatenationStatement listConcatenationStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found ListConcatenationStatement ", listConcatenationStatement.toString(),
                    " at ", listConcatenationStatement.getCodePosition()));
        }

        ListExpressionTransformer transformer = new ListExpressionTransformer();
        transformer.transformExpression(listConcatenationStatement.getListExpression(), listConcatenationStatement.getListId(), fontWeight);
        for (Content content : transformer.getContentList()) {
            containerStack.peek().addContent(content);
        }
    }

    @Override
    public void visitGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement) {
        // add list declaration statement
        containerStack.peek().addContent(new ListDeclaration(StringUtils.join("Listdeclaration: ", globalListDeclarationStatement.getListId()),
                globalListDeclarationStatement.getListId()));

        ListExpressionTransformer transformer = new ListExpressionTransformer();
        transformer.transformExpression(globalListDeclarationStatement.getListExpression(),
                globalListDeclarationStatement.getListId(), fontWeight);
        for (Content content : transformer.getContentList()) {
            containerStack.peek().addContent(content);
        }
    }

    @Override
    public void visitLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement) {
        // add list declaration statement
        containerStack.peek().addContent(new ListDeclaration(StringUtils.join("Listdeclaration: ", localListDeclarationStatement.getListId()),
                localListDeclarationStatement.getListId()));

        ListExpressionTransformer transformer = new ListExpressionTransformer();
        transformer.transformExpression(localListDeclarationStatement.getListExpression(),
                localListDeclarationStatement.getListId(), fontWeight);
        for (Content content : transformer.getContentList()) {
            containerStack.peek().addContent(content);
        }
    }

    @Override
    public void visitSectionStatement(SectionStatement sectionStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found SectionStatement ", sectionStatement.toString(),
                    " at ", sectionStatement.getCodePosition()));
        }
        containerStack.peek().addContent(new CarriageReturn("NL", new NumExpression(CodePosition.createZeroPosition(), 1)));
    }

    /**
     * read a value from the user data XML and write it to a hit2assext scalar variable.
     * e.g. #X< firstName
     *
     * @param dynamicValue
     */
    @Override
    public void visitDynamicValue(DynamicValue dynamicValue) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found DynamicValue ", dynamicValue.toString(),
                    " at ", dynamicValue.getCodePosition()));
        }
        if (insideWhileLoop) {
            containerStack.peek().addContent(new DynamicContentReference(
                    StringUtils.join("Assign from Userdata XML (iWL): ", dynamicValue.getName()),
                    StringUtils.join(" hit2assext:setScalarVariableValue(var:read('renderSessionUuid'), '", dynamicValue.getName(),
                            "', ./. ) | hit2assext:incrementXmlSequence(var:read('renderSessionUuid')) "),
                    fontWeight));
        } else {
            containerStack.peek().addContent(new DynamicContentReference(
                    StringUtils.join("Assign from Userdata XML (oWL): ", dynamicValue.getName()),
                    StringUtils.join(" hit2assext:setScalarVariableValue(var:read('renderSessionUuid'), '", dynamicValue.getName(),
                            "', /UserData/payload/line[@lineNr = hit2assext:getXmlSequence(var:read('renderSessionUuid'))]) ",
                            "| hit2assext:incrementXmlSequence(var:read('renderSessionUuid')) "),
                    fontWeight));
        }
    }

    /**
     * e.g. #D firstName ""
     *
     * @param globalDeclarationStatement
     */
    @Override
    public void visitGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found GlobalDeclarationStatement ", globalDeclarationStatement.toString(),
                    " at ", globalDeclarationStatement.getCodePosition()));
        }
        containerStack.peek().addContent(new DynamicContentReference(StringUtils.join("Global Variable: ", globalDeclarationStatement.getId()),
                StringUtils.join(" hit2assext:createScalarVariable(var:read('renderSessionUuid'), '", globalDeclarationStatement.getId(), "', ",
                        globalDeclarationStatement.getExpression().toXPathString(), ") "
                ), fontWeight));
    }

    /**
     * e.g. #d firstName ""
     *
     * @param localDeclarationStatement
     */
    @Override
    public void visitLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found LocalDeclarationStatement ", localDeclarationStatement.toString(),
                    " at ", localDeclarationStatement.getCodePosition()));
        }
        containerStack.peek().addContent(new DynamicContentReference(StringUtils.join("Local Variable: ", localDeclarationStatement.getId()),
                StringUtils.join(" hit2assext:createScalarVariable(var:read('renderSessionUuid'), '", localDeclarationStatement.getId(), "', ",
                        localDeclarationStatement.getExpression().toXPathString(), ") "), fontWeight));
    }

    @Override
    public void visitAssignmentStatement(AssignmentStatement assignmentStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found AssignmentStatement ", assignmentStatement.toString(),
                    " at ", assignmentStatement.getCodePosition()));
        }
        // there are several possibilities here:
        // assign a value to a scalar variable
        // assign a value to a slot of a list variable
        if (assignmentStatement.getIdExpression().getIdxExp1() == null) {
            // assign a value to a scalar variable
            // uses the DocDesign DocumentVariable mechanism
            // var:write('varName', XPath Expression)
            containerStack.peek().addContent(new DynamicContentReference(
                    StringUtils.join("Scalar Assignment: ", assignmentStatement.getIdExpression().getId()),
                    StringUtils.join("hit2assext:setScalarVariableValue(var:read('renderSessionUuid'), '", assignmentStatement.getIdExpression().getId(),
                            "', ", assignmentStatement.getExpression().toXPathString(), " )"),
                    fontWeight
            ));
        } else {
            // currently, the only other possibility is: assign a vlaue to a slot of a list variable
            // uses the hit2assext list mechanisms
            // hit2assext:setListValueAt(var:read('renderSessionUuid'), assignmentStatement.getExpression().getId(), value XPath)
            containerStack.peek().addContent(new DynamicContentReference(
                    StringUtils.join("List Assignment: ", assignmentStatement.getIdExpression().getId()),
                    StringUtils.join("hit2assext:setListValueAt(var:read('renderSessionUuid'), '",
                            assignmentStatement.getIdExpression().getId(), "', ",
                            assignmentStatement.getIdExpression().getIdxExp1().toXPathString(),
                            ", ", assignmentStatement.getExpression().toXPathString(), ")"), fontWeight
            ));
        }

    }

    @Override
    public void visitPrintStatement(PrintStatement printStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found PrintStatement ", printStatement.toString(),
                    " at ", printStatement.getCodePosition()));
        }
        containerStack.peek().addContent(new DynamicContentReference(
                StringUtils.join("Print: ",
                        printStatement.getExpression() instanceof IdExpression ?
                                ((IdExpression) printStatement.getExpression()).getId() : "()"),
                printStatement.getExpression().toXPathString(), fontWeight));
    }

    @Override
    public void visitFixedText(FixedText fixedText) {
        if (!StringUtils.isBlank(fixedText.getText()) || " ".equals(fixedText.getText())) {
            containerStack.peek().addContent(new Text("text", fixedText.getText(), fontWeight));
        }
    }

    @Override
    public void visitIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found IncludeBausteinStatement ", includeBausteinStatement.getPathToBaustein()));
        }
    }

}

package org.poormanscastle.products.hit2ass.renderer;

import java.util.Stack;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.poormanscastle.products.hit2ass.ast.domain.*;
import org.poormanscastle.products.hit2ass.renderer.domain.CarriageReturn;
import org.poormanscastle.products.hit2ass.renderer.domain.Container;
import org.poormanscastle.products.hit2ass.renderer.domain.Content;
import org.poormanscastle.products.hit2ass.renderer.domain.DeployedModuleDock;
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
import org.poormanscastle.products.hit2ass.renderer.domain.TextDecoration;
import org.poormanscastle.products.hit2ass.renderer.domain.WhileLoopFlagValueFlavor;
import org.poormanscastle.products.hit2ass.renderer.domain.Workspace;
import org.poormanscastle.products.hit2ass.renderer.domain.WorkspaceContainer;
import org.poormanscastle.products.hit2ass.renderer.domain.table.Table;

import static com.google.common.base.Preconditions.checkState;

/**
 * Iterates an AST representing a HIT/CLOU text component and converts it into an
 * Intermediate Representation tree, one more step into the direction of a
 * DocDesign workspace. The IR-tree is a hierarchy built from renderer.domain objects like
 * workspace, paragraph, IfElseParagraph, etc. In the end, the workspace object can be
 * serialized to string using its getContent() method. This string contains the acr XML
 * that can be imported into DocDesign as a DocDesign Workspace.
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class IRTransformer extends AstItemVisitorAdapter {

    private final static Logger logger = Logger.getLogger(IRTransformer.class);

    // track state of font weight. shall next text container be bold or normal.
    private FontWeight fontWeight = FontWeight.INHERIT;
    // track state of text alignment. shall next paragraph be left or right aligned, or centered, or justified.
    private TextAlignment textAlignment = TextAlignment.JUSTIFIED;
    // track state of text decoration. shall next text container be underlined or not.
    // <Attribute name="FontDecoration"><![CDATA[inherit]]></Attribute>
    private TextDecoration textDecoration = TextDecoration.INHERIT;

    private boolean insideWhileLoop = false;

    /**
     * the IRTransformer relies on the behavior of the AST Parser that during parsing a deployed module
     * library workspace is created for commonly used modules and that this module library is available
     * at a defined location, e.g. in the file system. The IRTransformer will try to resolve dependencies
     * there and will throw an exception if a module name cannot be resolved in the module library.
     */
    private DeployedModuleLibrary deployedModuleLibrary;

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
     * @return a new IRTransformer
     */
    private IRTransformer spinOff() {
        IRTransformer transformer = new IRTransformer();
        transformer.fontWeight = fontWeight;
        transformer.insideWhileLoop = insideWhileLoop;
        transformer.workspace = workspace;
        return transformer;
    }

    @Override
    public void visitClouBaustein(ClouBausteinImpl clouBaustein) {
        workspace = new Workspace(clouBaustein.getClouBausteinName());
        // 2017-11-17 14:39 end users wish that components don't get organized in paragraphs
        // but directly on the workspace level. Which arguably brings advantages in further
        // maintenance of the bausteins.
        // 2017-11-19 13:00 This will not work because diverse constructs like loops
        // require Aggregation and Repetition elements which can only be applied within paragraphs.
        containerStack.push(new Paragraph("CLOU Component Paragraph"));
        // containerStack.push(new WorkspaceContainer("CLOU Component Paragraph"));
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
        addContentToIrTree(forLoop);
        // containerStack.peek().addContent(forLoop);

        IRTransformer spinOffTransformer = spinOff();
        spinOffTransformer.containerStack.push(forLoop);
        forStatement.getForBody().accept(spinOffTransformer);
        // the visit logic gets handled in the proceedWith method, so it returns "false" so that
        // the visit method won't get called.
        return false;
    }

    @Override
    public boolean proceedWithSwitchStatement(SwitchStatement switchStatement) {
        // this will transform the switch statement into a hierarchy of nested if/then/else statements where each
        // CASE is nested into its predecessors ELSE block.
        // First, get a hold of the switch expressions which will be compared to each CASE string match.
        IdExpression switchExpression = (IdExpression) switchStatement.getExpression();
        // iterate over the SWITCH statement's CASE branches
        CaseStatementList caseStatementList = (CaseStatementList) switchStatement.getCaseStatement();
        addContentToIrTree(convertAndAppendCaseStatementToIfThenElseParagraph(caseStatementList, switchExpression));
        // containerStack.peek().addContent(convertAndAppendCaseStatementToIfThenElseParagraph(caseStatementList, switchExpression));
        return false;
    }

    IfThenElseParagraph convertAndAppendCaseStatementToIfThenElseParagraph(CaseStatementList patient, Expression switchExpression) {
        // this method shall create a hierarchy of nested if/then/else structures to represent the sequence
        // of CASE branches within the SWITCH statement.
        // for each CASE branch, the if text is created from the original SWITCH expression, combined with the 
        // respective string MATCH.
        CaseStatement caseStatement = patient.getHead();
        Expression expression = "".equals(patient.getHead().getMatch()) ?
                new BinaryOperatorExpression(new NumExpression(CodePosition.createZeroPosition(), 0), BinaryOperator.EQ,
                        new NumExpression(CodePosition.createZeroPosition(), 0))
                :
                new BinaryOperatorExpression(CodePosition.createZeroPosition(), switchExpression, BinaryOperator.EQ,
                        new TextExpression(CodePosition.createZeroPosition(), patient.getHead().getMatch()));

        String ifParagraphTitle = StringEscapeUtils.escapeXml10(expression.toDebugString()).replaceAll("\\.", "_");
        logger.info(StringUtils.join("Creating CASE statement for workspace ", getWorkspace().getWorkspaceName(),
                " with condition ", ifParagraphTitle));

        IfThenElseParagraph ifParagraph = new IfThenElseParagraph(StringUtils.join("IF_CASE ", ifParagraphTitle), expression);
        // now create the THEN branch and fill it with the content contained in the respective CASE branch
        IRTransformer spinOffTransformer = spinOff();
        spinOffTransformer.containerStack.push(new IfThenParagraph("THEN"));
        caseStatement.accept(spinOffTransformer);
        ifParagraph.addContent(spinOffTransformer.containerStack.pop());

        if (patient instanceof PairCaseStatementList) {
            IfElseParagraph ifElseParagraph = new IfElseParagraph("ELSE");
            ifElseParagraph.addContent(convertAndAppendCaseStatementToIfThenElseParagraph(patient.getTail(), switchExpression));
            ifParagraph.addContent(ifElseParagraph);
        }

        return ifParagraph;
    }


    @Override
    public boolean proceedWithCaseStatementImpl(CaseStatementImpl caseStatement) {
        String match = caseStatement.getMatch();

        return super.proceedWithCaseStatementImpl(caseStatement);
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
            addContentToIrTree(whileLoop);
            // containerStack.peek().addContent(whileLoop);

            IRTransformer spinOffTransformer = spinOff();
            spinOffTransformer.containerStack.push(whileLoop);
            whileStatement.getWhileBody().accept(spinOffTransformer);
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

            addContentToIrTree(forLoop);
            // containerStack.peek().addContent(forLoop);
            IRTransformer spinOffTransformer = spinOff();
            spinOffTransformer.containerStack.push(forLoop);
            whileStatement.getWhileBody().accept(spinOffTransformer);
        }

        return false;
    }

    @Override
    public boolean proceedWithConditionalStatement(ConditionalStatement conditionalStatement) {
        // 2017-12-03 20:44 Fix issue, that DocFamily element titles must not contain dot characters (".")
        String ifParagraphTitle = StringEscapeUtils.escapeXml10(conditionalStatement.getCondition().toDebugString()).replaceAll("\\.", "_");
        IfThenElseParagraph ifParagraph = new IfThenElseParagraph(
                StringUtils.join("IF ", ifParagraphTitle),
                conditionalStatement.getCondition());
        logger.info(StringUtils.join("Creating IF statement for workspace ", getWorkspace().getWorkspaceName(), " with condition ",
                ifParagraphTitle));
        addContentToIrTree(ifParagraph);
        // containerStack.peek().addContent(ifParagraph);

        if (conditionalStatement.getThenElement() != null) {
            IRTransformer spinOffTransformer = spinOff();
            spinOffTransformer.containerStack.push(new IfThenParagraph("THEN"));
            conditionalStatement.getThenElement().accept(spinOffTransformer);
            ifParagraph.addContent(spinOffTransformer.containerStack.pop());
        } else {
            ifParagraph.addContent(new IfThenParagraph("THEN EMPTY"));
        }
        if (conditionalStatement.getElseElement() != null) {
            IRTransformer spinOffTransformer = spinOff();
            spinOffTransformer.containerStack.push(new IfElseParagraph("ELSE"));
            conditionalStatement.getElseElement().accept(spinOffTransformer);
            ifParagraph.addContent(spinOffTransformer.containerStack.pop());
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
        // TODO invert the IF tests. E.g, "FEEIN".equals(macroCallStatement.getMacroId()) instead of the *@ given below
        if ("FEEIN".equals(macroCallStatement.getMacroId())) {
            fontWeight = FontWeight.BOLD;
        } else if ("FEAUS".equals(macroCallStatement.getMacroId())) {
            fontWeight = FontWeight.INHERIT;
        } else if ("USEIN".equals(macroCallStatement.getMacroId())) {
            textDecoration = TextDecoration.UNDERLINED;
        } else if ("USAUS".equals(macroCallStatement.getMacroId())) {
            textDecoration = TextDecoration.INHERIT;
        } else if ("TABU".equals(macroCallStatement.getMacroId())) {
            if (tableSpace == null) {
                containerStack.peek().addContent(new Text("TABU", "       ", fontWeight, textDecoration));
            } else {
                tableSpace.table.startNewCell();
            }
        } else if ("ZLTZ12".equals(macroCallStatement.getMacroId())) {
            // this is the MACRO to switch to justified text alignment.
            // Check if the current parent element on the container stack is a paragraph.
            // if so: we can't shift the text alignment within one paragraph. Create a new paragraph,
            // set its alignment to justify and push it onto the container stack.
            // if the current parent is something else - like a IfThenParagraph -  again, create a new
            // paragraph with the sensible text alignment settings, but now add it as a child to the current
            // parent element.
            logger.info("ZLTZ12 was called. Create new centered container for following elements until ZLTB12 will be called.");
            // this macro call activates vertical alignment CENTER. This alignment is kept until ZLTB12 gets
            // called and the alignment is reset to BLOCK.
            textAlignment = TextAlignment.CENTER;
            Paragraph centeredParagraph = new Paragraph("TextAlignment Center", textAlignment);
            // containerStack.peek().addContent(centeredParagraph);
            if (!(containerStack.peek() instanceof Paragraph)) {
                containerStack.peek().addContent(centeredParagraph);
            } else {
                containerStack.push(centeredParagraph);
            }
        } else if ("ZLTB12".equals(macroCallStatement.getMacroId())) {
            // It is expected that ZLTZ12 was called before ZLTB12 is called.
            // see comment for MACRO ZLTZ12
            logger.info("ZLTB12 was called. Creating new justified container.");
            if (textAlignment != TextAlignment.CENTER) {
                logger.info(StringUtils.join("ZLTB12 was called while IRTransformer is in state ", textAlignment.getValue()));
            }
            textAlignment = TextAlignment.JUSTIFIED;
            Paragraph justifiedParagraph = new Paragraph("TextAlignment Justified", textAlignment);
            if (!(containerStack.peek() instanceof Paragraph)) {
                containerStack.peek().addContent(justifiedParagraph);
            } else {
                containerStack.push(justifiedParagraph);
            }
        } else if ("element".equals(macroCallStatement.getMacroId())) {
            containerStack.peek().addContent(new DynamicContentReference("MACRO call listelembel",
                    " hit2assext:convertListElementsToVars(var:read('renderSessionUuid')) ",
                    fontWeight, textDecoration));
        } else {
            logger.warn(StringUtils.join("Found unknown macro id ", macroCallStatement.getMacroId()));
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
            case ZL_NEU:
                if (tableSpace == null) {
                    /**
                     * this could be a HIT/CLOU table preamble.
                     */
                    tableSpace = new TableSpace();
                    tableSpace.startAnchor = hitCommandStatement;
                    tableSpace.currentStatementAnchor = hitCommandStatement;
                    tableSpace.table = Table.createTable();
                    // since a tableSpace was created the IRTransformer will henceforth be in table builder mode.
                } else {
                    // this should be the end of a HIT/CLOU table.
                    containerStack.peek().addContent(tableSpace.table);
                    // create a new table space. if this statement is followed by just #G 1 and #G 9 then
                    // there is now table directly following and the tableSpace can be demolished again
                    tableSpace = new TableSpace();
                    tableSpace.startAnchor = hitCommandStatement;
                    tableSpace.currentStatementAnchor = hitCommandStatement;
                    tableSpace.table = Table.createTable();
                    // since tableSpace was reset the IRTransformer will hencefoth be in normal mode.
                }
                break;
        }
    }

    @Override
    public void visitGStatement(GStatement gStatement) {
        int xpos = (Integer) gStatement.getXpos().getValue();
        int code = (Integer) gStatement.getValue().getValue();
        switch (code) {
            case 1:
                // code 1 stands for the left page margin
                checkState(tableSpace.lastTabStopPosition == -1, "before #G code 1 the last tab position must be -1");
                checkState(!tableSpace.gStatement1Found, "#G code 1 appears to be listed more than once");
                checkState(!tableSpace.gStatement8Found, "#G code 8 appears to have been listed before code 1");
                tableSpace.gStatement1Found = true;
                tableSpace.lastTabStopPosition = xpos;
                break;
            case 2:
                // code 2 stands probably for alignment left
            case 4:
                // code 4 stans probably for alignment right
                checkState(tableSpace.gStatement1Found, StringUtils.join("#G col def code ", code, " appears to come before code 1"));
                tableSpace.table.addTableColumn(xpos - tableSpace.lastTabStopPosition);
                tableSpace.lastTabStopPosition = xpos;
                break;
            case 8:
                // code 8 stands probably for right page margin
                checkState(tableSpace.gStatement1Found, StringUtils.join("#G col def code ", code, " appears to come before code 1"));
                tableSpace.gStatement8Found = true; // from now on it's ok to add content to this table
                if (tableSpace.table.getColumnCount() > 0) {
                    tableSpace.table.addTableColumn(xpos - tableSpace.lastTabStopPosition);
                    tableSpace.lastTabStopPosition = xpos;
                } else {
                    // this was not a preamble to a new table but just a setting things straight for
                    // normal page layout again.
                    tableSpace = null;
                }
                break;
        }
        super.visitGStatement(gStatement);
    }

    @Override
    public void visitListConcatenationStatement(ListConcatenationStatement listConcatenationStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found ListConcatenationStatement ", listConcatenationStatement.toString(),
                    " at ", listConcatenationStatement.getCodePosition()));
        }

        ListExpressionTransformer transformer = new ListExpressionTransformer();
        transformer.transformExpression(listConcatenationStatement.getListExpression(), listConcatenationStatement.getListId(),
                fontWeight, textDecoration);
        for (Content content : transformer.getContentList()) {
            addContentToIrTree(content);
            // containerStack.peek().addContent(content);
        }
    }

    @Override
    public void visitGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement) {
        // add list declaration statement
        addContentToIrTree(new ListDeclaration(StringUtils.join("Listdeclaration: ", globalListDeclarationStatement.getListId()),
                globalListDeclarationStatement.getListId()));
        // containerStack.peek().addContent(new ListDeclaration(StringUtils.join("Listdeclaration: ", globalListDeclarationStatement.getListId()),
        //        globalListDeclarationStatement.getListId()));

        ListExpressionTransformer transformer = new ListExpressionTransformer();
        transformer.transformExpression(globalListDeclarationStatement.getListExpression(),
                globalListDeclarationStatement.getListId(), fontWeight, textDecoration);
        for (Content content : transformer.getContentList()) {
            addContentToIrTree(content);
            // containerStack.peek().addContent(content);
        }
    }

    @Override
    public void visitLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement) {
        // add list declaration statement
        addContentToIrTree(new ListDeclaration(StringUtils.join("Listdeclaration: ", localListDeclarationStatement.getListId()),
                localListDeclarationStatement.getListId()));
        // containerStack.peek().addContent(new ListDeclaration(StringUtils.join("Listdeclaration: ", localListDeclarationStatement.getListId()),
        //        localListDeclarationStatement.getListId()));

        ListExpressionTransformer transformer = new ListExpressionTransformer();
        transformer.transformExpression(localListDeclarationStatement.getListExpression(),
                localListDeclarationStatement.getListId(), fontWeight, textDecoration);
        for (Content content : transformer.getContentList()) {
            addContentToIrTree(content);
            // containerStack.peek().addContent(content);
        }
    }

    @Override
    public void visitSectionStatement(SectionStatement sectionStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found SectionStatement ", sectionStatement.toString(),
                    " at ", sectionStatement.getCodePosition()));
        }
        addContentToIrTree(new CarriageReturn("NL", new NumExpression(CodePosition.createZeroPosition(), 1)));
        // containerStack.peek().addContent(new CarriageReturn("NL", new NumExpression(CodePosition.createZeroPosition(), 1)));
    }

    /**
     * read a value from the user data XML and write it to a hit2assext scalar variable.
     * e.g. #X&lt; firstName
     *
     * @param dynamicValue stands for an element that is retrieved from the user data XML document
     */
    @Override
    public void visitDynamicValue(DynamicValue dynamicValue) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found DynamicValue ", dynamicValue.toString(),
                    " at ", dynamicValue.getCodePosition()));
        }
        if (insideWhileLoop) {
            // containerStack.peek().addContent(new DynamicContentReference(
            addContentToIrTree(new DynamicContentReference(
                    StringUtils.join("Assign from Userdata XML (iWL): ", dynamicValue.getName()),
                    StringUtils.join(" hit2assext:setScalarVariableValue(var:read('renderSessionUuid'), '", dynamicValue.getName(),
                            "', ./. ) | hit2assext:incrementXmlSequence(var:read('renderSessionUuid')) "),
                    fontWeight, textDecoration));
        } else {
            // containerStack.peek().addContent(new DynamicContentReference(
            addContentToIrTree(new DynamicContentReference(
                    StringUtils.join("Assign from Userdata XML (oWL): ", dynamicValue.getName()),
                    StringUtils.join(" hit2assext:setScalarVariableValue(var:read('renderSessionUuid'), '", dynamicValue.getName(),
                            "', /Briefdaten/payload/line[@lineNr = hit2assext:getXmlSequence(var:read('renderSessionUuid'))]) ",
                            "| hit2assext:incrementXmlSequence(var:read('renderSessionUuid')) "),
                    fontWeight, textDecoration));
        }
    }

    @Override
    public void visitInsertDay(InsertDay insertDay) {
        addContentToIrTree(new DynamicContentReference("Insert Day", " fn:day-from-date(fn:current-date()) ", fontWeight, textDecoration));
        // containerStack.peek().addContent(new DynamicContentReference("Insert Day", " fn:day-from-date(fn:current-date()) ", fontWeight, textDecoration));
    }

    @Override
    public void visitInsertMonth(InsertMonth insertMonth) {
        addContentToIrTree(new DynamicContentReference("Insert Month", " fn:month-from-date(fn:current-date()) ", fontWeight, textDecoration));
        // containerStack.peek().addContent(new DynamicContentReference("Insert Month", " fn:month-from-date(fn:current-date()) ", fontWeight, textDecoration));
    }

    @Override
    public void visitInsertYear(InsertYear insertYear) {
        addContentToIrTree(new DynamicContentReference("Insert Year", " fn:year-from-date(fn:current-date()) ", fontWeight, textDecoration));
        // containerStack.peek().addContent(new DynamicContentReference("Insert Year", " fn:year-from-date(fn:current-date()) ", fontWeight, textDecoration));
    }

    /**
     * e.g. #D firstName ""
     *
     * @param globalDeclarationStatement declaration of a global variable
     */
    @Override
    public void visitGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found GlobalDeclarationStatement ", globalDeclarationStatement.toString(),
                    " at ", globalDeclarationStatement.getCodePosition()));
        }
        // containerStack.peek().addContent(new DynamicContentReference(StringUtils.join("Global Variable: ", globalDeclarationStatement.getId()),
        addContentToIrTree(new DynamicContentReference(StringUtils.join("Global Variable: ", globalDeclarationStatement.getId()),
                StringUtils.join(" hit2assext:createScalarVariable(var:read('renderSessionUuid'), '", globalDeclarationStatement.getId(), "', ",
                        globalDeclarationStatement.getExpression().toXPathString(), ") "
                ), fontWeight, textDecoration));
    }

    /**
     * e.g. #d firstName ""
     *
     * @param localDeclarationStatement declares a locally visible variable.
     */
    @Override
    public void visitLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found LocalDeclarationStatement ", localDeclarationStatement.toString(),
                    " at ", localDeclarationStatement.getCodePosition()));
        }
        // containerStack.peek().addContent(new DynamicContentReference(StringUtils.join("Local Variable: ", localDeclarationStatement.getId()),
        addContentToIrTree(new DynamicContentReference(StringUtils.join("Local Variable: ", localDeclarationStatement.getId()),
                StringUtils.join(" hit2assext:createScalarVariable(var:read('renderSessionUuid'), '", localDeclarationStatement.getId(), "', ",
                        localDeclarationStatement.getExpression().toXPathString(), ") "), fontWeight, textDecoration));
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
            // containerStack.peek().addContent(new DynamicContentReference(
            addContentToIrTree(new DynamicContentReference(
                    StringUtils.join("Scalar Assignment: ", assignmentStatement.getIdExpression().getId()),
                    StringUtils.join("hit2assext:setScalarVariableValue(var:read('renderSessionUuid'), '", assignmentStatement.getIdExpression().getId(),
                            "', ", assignmentStatement.getExpression().toXPathString(), " )"),
                    fontWeight, textDecoration
            ));
        } else {
            // currently, the only other possibility is: assign a vlaue to a slot of a list variable
            // uses the hit2assext list mechanisms
            // hit2assext:setListValueAt(var:read('renderSessionUuid'), assignmentStatement.getExpression().getId(), value XPath)
            //containerStack.peek().addContent(new DynamicContentReference(
            addContentToIrTree(new DynamicContentReference(
                    StringUtils.join("List Assignment: ", assignmentStatement.getIdExpression().getId()),
                    StringUtils.join("hit2assext:setListValueAt(var:read('renderSessionUuid'), '",
                            assignmentStatement.getIdExpression().getId(), "', ",
                            assignmentStatement.getIdExpression().getIdxExp1().toXPathString(),
                            ", ", assignmentStatement.getExpression().toXPathString(), ")"), fontWeight, textDecoration
            ));
        }

    }

    @Override
    public void visitPrintStatement(PrintStatement printStatement) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found PrintStatement ", printStatement.toString(),
                    " at ", printStatement.getCodePosition()));
        }
        // containerStack.peek().addContent(new DynamicContentReference(
        addContentToIrTree(new DynamicContentReference(
                StringUtils.join("Print: ",
                        printStatement.getExpression() instanceof IdExpression ?
                                ((IdExpression) printStatement.getExpression()).getId() : "()"),
                printStatement.getExpression().toXPathString(), fontWeight, textDecoration));
    }

    @Override
    public void visitFixedText(FixedText fixedText) {
        if (!StringUtils.isBlank(fixedText.getText()) || " ".equals(fixedText.getText())) {
            // containerStack.peek().addContent(new Text(fixedText.getText(), fixedText.getText(), fontWeight, textDecoration));
            addContentToIrTree(new Text(fixedText.getText(), fixedText.getText(), fontWeight, textDecoration));
        }
    }

    @Override
    public boolean proceedWithIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement) {
        // the renderer will create the DeployedModule reference from data contained within
        // the IncludeBausteinStatement and does not want to continue with the content of
        // the IncludeBausteinStatement thingie.
        if (logger.isInfoEnabled()) {
            logger.info(StringUtils.join("Found IncludeBausteinStatement ", includeBausteinStatement.getPathToBaustein()));
        }

        // analogous to ClouBausteinDependencyResolverVisitor:
        // ignore Baustein a.ende here
        // TODO implement some interceptor or other hook point, maybe make names of ignorable bausteins configurable
        if (includeBausteinStatement.getPathToBaustein().contains("a.ende")) {
            return false;
        }

        // Fetch the deployed module library
        // then look up the deployed module corresponding to the given path to baustein
        // create a DeployedModuleReference and insert it into the container stack
        // containerStack.peek().addContent(DeployedModuleDock.createDeployedModuleDock(
        addContentToIrTree(DeployedModuleDock.createDeployedModuleDock(
                StringUtils.join("Call ", includeBausteinStatement.getCalledModuleName()),
                includeBausteinStatement.getCalledModuleName(),
                includeBausteinStatement.getCalledModuleElementId(), includeBausteinStatement.isLocalModuleReferences()));

        return false;
    }

    /**
     * this variable holds the table information when the IRTransformer has to transform
     * parts of a HIT/CLOU Baustein into a DocFamily table.
     * If this variable is null than no table processing is going on right now.
     * When table processing starts, the table builder will create a new object and assign it to the variable thus
     * documenting the new state of the IRTransformer while processing the table.
     * When the table processing comes to an end, either because table rendering has finished or because an
     * exception occurred, this variable will be set to null again.
     */
    private TableSpace tableSpace = null;

    /**
     * this type holds all information needed when the IRTransformer is creating a new table structure.
     */
    private class TableSpace {
        // flag that indicates that the table is being initialized properly
        private boolean gStatement1Found = false;
        // flag that indicates that the table column definition is finished
        private boolean gStatement8Found = false;
        // this value is used to calculate the width of the current column
        private int lastTabStopPosition = -1;


        /**
         * this is the HitCommandStatement #^"ZL NEU" where everything took its start.
         * If the table builder routine finds that this is not a HIT/CLOU table preamble,
         * the IRTransformer can go back to that statement via this anchor and proceed from there.
         */
        private HitCommandStatement startAnchor;

        /**
         * this variable holds the current element being processed by the table builder.
         * When the table builder finishes successfully, this will be the element at which the IRTransformer
         * can start continuing transformation.
         */
        private Statement currentStatementAnchor;

        /**
         * this is the table being built
         */
        private Table table;

    }

    /**
     * if IRTransformer is in normal mode, add content to the containerStack.
     * if IRTransformer is in table mode, add content to the current table.
     *
     * @param content
     */
    private void addContentToIrTree(Content content) {
        if (tableSpace == null) {
            containerStack.peek().addContent(content);
        } else {
            tableSpace.table.addContent(content);
        }
    }

}

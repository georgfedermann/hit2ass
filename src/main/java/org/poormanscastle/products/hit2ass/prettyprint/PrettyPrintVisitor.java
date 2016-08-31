package org.poormanscastle.products.hit2ass.prettyprint;

import static com.google.common.base.Preconditions.checkState;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.poormanscastle.products.hit2ass.ast.domain.AssignmentStatement;
import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.BinaryOperatorExpression;
import org.poormanscastle.products.hit2ass.ast.domain.BooleanExpression;
import org.poormanscastle.products.hit2ass.ast.domain.CaseStatementImpl;
import org.poormanscastle.products.hit2ass.ast.domain.CloseFileCommand;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBausteinImpl;
import org.poormanscastle.products.hit2ass.ast.domain.ClouFunctionCall;
import org.poormanscastle.products.hit2ass.ast.domain.ConditionalStatement;
import org.poormanscastle.products.hit2ass.ast.domain.DecimalExpression;
import org.poormanscastle.products.hit2ass.ast.domain.DynamicValue;
import org.poormanscastle.products.hit2ass.ast.domain.FixedText;
import org.poormanscastle.products.hit2ass.ast.domain.ForStatement;
import org.poormanscastle.products.hit2ass.ast.domain.GStatement;
import org.poormanscastle.products.hit2ass.ast.domain.GlobalDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.GlobalListDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.HitCommandStatement;
import org.poormanscastle.products.hit2ass.ast.domain.IdExpression;
import org.poormanscastle.products.hit2ass.ast.domain.IncludeBausteinStatement;
import org.poormanscastle.products.hit2ass.ast.domain.JStatement;
import org.poormanscastle.products.hit2ass.ast.domain.LastCaseStatementList;
import org.poormanscastle.products.hit2ass.ast.domain.LastClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.LastExpressionList;
import org.poormanscastle.products.hit2ass.ast.domain.ListConcatenationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.ListEnumerationExpression;
import org.poormanscastle.products.hit2ass.ast.domain.LocalDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.LocalListDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.MacroCallStatement;
import org.poormanscastle.products.hit2ass.ast.domain.MacroDefinitionStatement;
import org.poormanscastle.products.hit2ass.ast.domain.NewLine;
import org.poormanscastle.products.hit2ass.ast.domain.NumExpression;
import org.poormanscastle.products.hit2ass.ast.domain.OpenFileCommand;
import org.poormanscastle.products.hit2ass.ast.domain.PairCaseStatementList;
import org.poormanscastle.products.hit2ass.ast.domain.PairClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.PairExpressionList;
import org.poormanscastle.products.hit2ass.ast.domain.PrintStatement;
import org.poormanscastle.products.hit2ass.ast.domain.SectionStatement;
import org.poormanscastle.products.hit2ass.ast.domain.ShellCommand;
import org.poormanscastle.products.hit2ass.ast.domain.ShellVariableExpression;
import org.poormanscastle.products.hit2ass.ast.domain.SubListExpression;
import org.poormanscastle.products.hit2ass.ast.domain.SwitchStatement;
import org.poormanscastle.products.hit2ass.ast.domain.TextExpression;
import org.poormanscastle.products.hit2ass.ast.domain.UnaryOperatorExpression;
import org.poormanscastle.products.hit2ass.ast.domain.WhileStatement;
import org.poormanscastle.products.hit2ass.ast.domain.WriteStatement;
import org.poormanscastle.products.hit2ass.ast.domain.ZStatement;

/**
 * creates a graphical view of the AST of an v0.1 grammar program using DOT syntax.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 18.02.2016.
 */
public class PrettyPrintVisitor extends AstItemVisitorAdapter {

    private static long sequence = 0;

    /**
     * using this stack the pretty printer keeps track of the current parent elements.
     */
    private Stack<StackItem> itemStack = new Stack<>();
    private List<StackItem> stackItems = new LinkedList<>();

    private StringBuffer buffer = new StringBuffer();

    public PrettyPrintVisitor() {
        initialize();
    }

    public void initialize() {
        itemStack = new Stack<>();
        buffer = new StringBuffer("digraph ast {\n%labels%\nnode [shape = circle];\n");
    }

    public void addBufferLine() {
        checkState(itemStack.size() > 0);
        if (itemStack.size() > 1) {
            StackItem child = itemStack.pop();
            StackItem parent = itemStack.pop();
            buffer.append(StringUtils.join(parent.getId(), " -> ", child.getId(), ";\n"));
            itemStack.push(parent);
            itemStack.push(child);
        }
    }

    public String serialize() {
        buffer.append("}");
        String result = buffer.toString();
        StringBuilder labelDefinitions = new StringBuilder();
        for (StackItem stackItem : stackItems) {
            labelDefinitions.append(StringUtils.join(stackItem.getId(), " [label=\"", stackItem.getLabel(), "\"];\n"));
        }
        result = result.replace("%labels%", labelDefinitions.toString());
        return result;
    }

    public void addItem(String name, String value) {
        // TODO the string abbreviation can cause problems when an escape sequence gets separated, as in \"
        // the following statement is not stable enough. If the pain is big enough with long
        // texts in the diagrams, one might consider to implement a \" proof version. Until then,
        // I'll leave it like 2 lines below.
        // StackItem stackItem = new StackItem(name, StringUtils.abbreviateMiddle(value.replaceAll("\"", "\\\\\""), "...", 30));
        StackItem stackItem = new StackItem(name, value.replaceAll("\"", "\\\\\""));
        itemStack.push(stackItem);
        stackItems.add(stackItem);
    }

    @Override
    public void visitSectionStatement(SectionStatement sectionStatement) {
        addItem("Break", "");
        addBufferLine();
    }

    @Override
    public void leaveSectionStatement(SectionStatement sectionStatement) {
        itemStack.pop();
    }

    @Override
    public void visitClouBaustein(ClouBausteinImpl clouBaustein) {
        addItem("CB", "");
        addBufferLine();
    }

    @Override
    public void leaveClouBaustein(ClouBausteinImpl clouBaustein) {
        itemStack.pop();
    }


    @Override
    public void visitFixedText(FixedText fixedText) {
        addItem("FX", fixedText.getText());
        addBufferLine();
    }

    @Override
    public void leaveFixedText(FixedText fixedText) {
        itemStack.pop();
    }

    @Override
    public void visitNewLine(NewLine newLine) {
        addItem("NL", "<-|");
        addBufferLine();
    }

    @Override
    public void leaveNewLine(NewLine newLine) {
        itemStack.pop();
    }

    @Override
    public void visitPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {
        addItem("CBPairList", "[]");
        addBufferLine();
    }

    @Override
    public void leavePairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {
        itemStack.pop();
    }

    @Override
    public void visitLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList) {
        addItem("CBLastList", "[]");
        addBufferLine();
    }

    @Override
    public void leaveLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList) {
        itemStack.pop();
    }

    @Override
    public void visitConditionalStatementStatement(ConditionalStatement conditionalStatement) {
        addItem("IF", "");
        addBufferLine();
    }

    @Override
    public void leaveConditionalStatementStatement(ConditionalStatement conditionalStatement) {
        itemStack.pop();
    }

    @Override
    public void visitWhileStatement(WhileStatement whileStatement) {
        addItem("While", "[]");
        addBufferLine();
    }

    @Override
    public void leaveWhileStatement(WhileStatement whileStatement) {
        itemStack.pop();
    }

    @Override
    public void visitForStatement(ForStatement forStatement) {
        addItem("For", forStatement.getRepetitionCount().toXPathString());
        addBufferLine();
    }

    @Override
    public void leaveForStatement(ForStatement forStatement) {
        itemStack.pop();
    }

    @Override
    public void visitSwitchStatement(SwitchStatement switchStatement) {
        addItem("Switch", "[]");
        addBufferLine();
    }

    @Override
    public void leaveSwitchStatement(SwitchStatement switchStatement) {
        itemStack.pop();
    }

    @Override
    public void visitCaseStatementImpl(CaseStatementImpl caseStatement) {
        addItem("Case", caseStatement.getMatch());
        addBufferLine();
    }

    @Override
    public void leaveCaseStatementImpl(CaseStatementImpl caseStatement) {
        itemStack.pop();
    }

    @Override
    public void visitGStatement(GStatement gStatement) {
        addItem("GS", String.valueOf(gStatement.getValue().getValue()));
        addBufferLine();
    }

    @Override
    public void leaveGStatement(GStatement gStatement) {
        itemStack.pop();
    }

    @Override
    public void visitJStatement(JStatement jStatement) {
        addItem("JS", StringUtils.join(jStatement.getRowPos().getValue(), "/", jStatement.getColPos().getValue()));
        addBufferLine();
    }

    @Override
    public void leaveJStatement(JStatement jStatement) {
        itemStack.pop();
    }

    @Override
    public void visitZStatement(ZStatement zStatement) {
        addItem("ZS", "[]");
        addBufferLine();
    }

    @Override
    public void leaveZStatement(ZStatement zStatement) {
        itemStack.pop();
    }

    @Override
    public void visitPairCaseStatementList(PairCaseStatementList pairCaseStatementList) {
        addItem("CasePairList", "[]");
        addBufferLine();
    }

    @Override
    public void leavePairCaseStatementList(PairCaseStatementList pairCaseStatementList) {
        itemStack.pop();
    }

    @Override
    public void visitLastCaseStatementList(LastCaseStatementList lastCaseStatementList) {
        addItem("CaseLastList", "[]");
        addBufferLine();
    }

    @Override
    public void leaveLastCaseStatementList(LastCaseStatementList lastCaseStatementList) {
        itemStack.pop();
    }

    @Override
    public void visitMacroCallStatement(MacroCallStatement macroCallStatement) {
        addItem("MacroCall", macroCallStatement.getMacroId());
        addBufferLine();
    }

    @Override
    public void leaveMacroCallStatement(MacroCallStatement macroCallStatement) {
        itemStack.pop();
    }

    @Override
    public void visitMacroDefinitionStatement(MacroDefinitionStatement macroDefinitionStatement) {
        addItem("MacroDef", macroDefinitionStatement.getMacroDefinition());
        addBufferLine();
    }

    @Override
    public void leaveMacroDefinitionStatement(MacroDefinitionStatement macroDefinitionStatement) {
        itemStack.pop();
    }

    @Override
    public void visitPairExpressionList(PairExpressionList pairExpressionList) {
        addItem("PairExprList", "[]");
        addBufferLine();
    }

    @Override
    public void leavePairExpressionList(PairExpressionList pairExpressionList) {
        itemStack.pop();
    }

    @Override
    public void visitLastExpressionList(LastExpressionList lastExpressionList) {
        addItem("LastExprList", "[]");
        addBufferLine();
    }

    @Override
    public void leaveLastExpressionList(LastExpressionList lastExpressionList) {
        itemStack.pop();
    }

    @Override
    public void visitIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement) {
        addItem("B", includeBausteinStatement.getPathToBaustein());
        addBufferLine();
    }

    @Override
    public void leaveIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement) {
        itemStack.pop();
    }

    @Override
    public void visitOpenFileCommand(OpenFileCommand openFileCommand) {
        addItem("Open", openFileCommand.getFileName());
        addBufferLine();
    }

    @Override
    public void leaveOpenFileCommand(OpenFileCommand openFileCommand) {
        itemStack.pop();
    }

    @Override
    public void visitCloseFileCommand(CloseFileCommand closeFileCommand) {
        addItem("Close", closeFileCommand.getFileName());
        addBufferLine();
    }

    @Override
    public void leaveCloseFileCommand(CloseFileCommand closeFileCommand) {
        itemStack.pop();
    }

    @Override
    public void visitUnaryOperatorExpression(UnaryOperatorExpression unaryOperatorExpression) {
        addItem("UnOpExpr", unaryOperatorExpression.getOperator().getLabel());
        addBufferLine();
    }

    @Override
    public void leaveUnaryOperatorExpression(UnaryOperatorExpression unaryOperatorExpression) {
        itemStack.pop();
    }

    @Override
    public void visitListEnumerationExpression(ListEnumerationExpression listEnumerationExpression) {
        addItem("ListEnum", "[]");
        addBufferLine();
    }

    @Override
    public void leaveListEnumerationExpression(ListEnumerationExpression listEnumerationExpression) {
        itemStack.pop();
    }

    @Override
    public void visitSubListExpression(SubListExpression subListExpression) {
        addItem("SubListExpr", "[]");
        addBufferLine();
    }

    @Override
    public void leaveSubListExpression(SubListExpression subListExpression) {
        itemStack.pop();
    }

    @Override
    public void visitClouFunctionCall(ClouFunctionCall clouFunctionCall) {
        addItem("ClouFuncCall", clouFunctionCall.toXPathString());
        addBufferLine();
    }

    @Override
    public void leaveClouFunctionCall(ClouFunctionCall clouFunctionCall) {
        itemStack.pop();
    }

    @Override
    public void visitListConcatenationStatement(ListConcatenationStatement listConcatenationStatement) {
        addItem("ListConcat", "[]");
        addBufferLine();
    }

    @Override
    public void leaveListConcatenationStatement(ListConcatenationStatement listConcatenationStatement) {
        itemStack.pop();
    }

    @Override
    public void visitGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement) {
        addItem("GDS", globalDeclarationStatement.getId());
        addBufferLine();
    }

    @Override
    public void leaveGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement) {
        itemStack.pop();
    }

    @Override
    public void visitLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement) {
        addItem("LDS", localDeclarationStatement.getId());
        addBufferLine();
    }

    @Override
    public void leaveLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement) {
        itemStack.pop();
    }

    @Override
    public void visitGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement) {
        addItem("GLDS", globalListDeclarationStatement.getListId());
        addBufferLine();
    }

    @Override
    public void leaveGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement) {
        itemStack.pop();
    }

    @Override
    public void visitLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement) {
        addItem("LLDS", localListDeclarationStatement.getListId());
        addBufferLine();
    }

    @Override
    public void leaveLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement) {
        itemStack.pop();
    }

    @Override
    public void visitHitCommandStatement(HitCommandStatement hitCommandStatement) {
        addItem("HitCmd", StringUtils.join(String.valueOf(hitCommandStatement.getRepetitor()), "/",
                hitCommandStatement.getHitCommand().getLabel(), hitCommandStatement.isVerstarkt() ? "+" : ""));
        addBufferLine();
    }

    @Override
    public void leaveHitCommandStatement(HitCommandStatement hitCommandStatement) {
        itemStack.pop();
    }

    @Override
    public void visitWriteStatement(WriteStatement writeStatement) {
        addItem("WriteCmd", "[]");
        addBufferLine();
    }

    @Override
    public void leaveWriteStatement(WriteStatement writeStatement) {
        itemStack.pop();
    }

    @Override
    public void visitDynamicValue(DynamicValue dynamicValue) {
        addItem("DV", dynamicValue.getName());
        addBufferLine();
    }

    @Override
    public void leaveDynamicValue(DynamicValue dynamicValue) {
        itemStack.pop();
    }

    @Override
    public void visitPrintStatement(PrintStatement printStatement) {
        addItem("Print", printStatement.getExpression().toXPathString());
        addBufferLine();
    }

    @Override
    public void leavePrintStatement(PrintStatement printStatement) {
        itemStack.pop();
    }

    @Override
    public void visitShellCommand(ShellCommand shellCommand) {
        addItem("ShellCommand", shellCommand.getCommando());
        addBufferLine();
    }

    @Override
    public void leaveShellCommand(ShellCommand shellCommand) {
        itemStack.pop();
    }

    @Override
    public void visitAssignmentStatement(AssignmentStatement assignmentStatement) {
        addItem("AS", assignmentStatement.getIdExpression().getId());
        addBufferLine();
    }

    @Override
    public void leaveAssignmentStatement(AssignmentStatement assignmentStatement) {
        itemStack.pop();
    }

    @Override
    public void visitBinaryOperatorExpression(BinaryOperatorExpression binaryOperatorExpression) {
        addItem("BinOP", binaryOperatorExpression.getOperator().getLabel());
        addBufferLine();
    }

    @Override
    public void leaveBinaryOperatorExpression(BinaryOperatorExpression binaryOperatorExpression) {
        itemStack.pop();
    }

    @Override
    public void visitIdExpression(IdExpression idExpression) {
        addItem("IdExp", idExpression.getId());
        addBufferLine();
    }

    @Override
    public void leaveIdExpression(IdExpression idExpression) {
        itemStack.pop();
    }

    @Override
    public void visitNumExpression(NumExpression numExpression) {
        addItem("NumExp", String.valueOf(numExpression.getValue()));
        addBufferLine();
    }

    @Override
    public void leaveNumExpression(NumExpression numExpression) {
        itemStack.pop();
    }

    @Override
    public void visitBooleanExpression(BooleanExpression booleanExpression) {
        addItem("BoolExp", String.valueOf(booleanExpression.getValue()));
        addBufferLine();
    }

    @Override
    public void leaveBooleanExpression(BooleanExpression booleanExpression) {
        itemStack.pop();
    }

    @Override
    public void visitDecimalExpression(DecimalExpression decimalExpression) {
        addItem("DecExpr", String.valueOf(decimalExpression.getValue()));
        addBufferLine();
    }

    @Override
    public void leaveDecimalExpression(DecimalExpression decimalExpression) {
        itemStack.pop();
    }

    @Override
    public void visitTextExpression(TextExpression textExpression) {
        addItem("TxtExpr", textExpression.getValue());
        addBufferLine();
    }

    @Override
    public void leaveTextExpression(TextExpression textExpression) {
        itemStack.pop();
    }

    @Override
    public void visitShellVariableExpression(ShellVariableExpression shellVariableExpression) {
        addItem("ShellVarExpr", shellVariableExpression.getShellVariableId());
        addBufferLine();
    }

    @Override
    public void leaveShellVariableExpression(ShellVariableExpression shellVariableExpression) {
        itemStack.pop();
    }

    /**
     * the pretty printer uses a stack to keep track of the current nodes.
     */
    private class StackItem {
        private String id = StringUtils.join("i", PrettyPrintVisitor.sequence++);

        /**
         * name will be used as label for the DOT diagram.
         */
        private final String name;

        /**
         * if an item carries a semantic value (like the id of an idToken, etc.) the value
         * will be stored here.
         */
        private final String value;

        private StackItem(String name, String value) {
            this.name = name;
            this.value = value;
        }

        private String getLabel() {
            return StringUtils.isBlank(value) ? name : StringUtils.join(name, ":", value);
        }

        public String getId() {
            return id;
        }
    }

}

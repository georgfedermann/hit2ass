package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg.federmann@poormanscastle.com on 06.04.2016.
 */
public abstract class AstItemVisitorAdapter implements AstItemVisitor {

    private boolean isAstValid = true;

    private boolean defaultVisitNodes = true;

    public AstItemVisitorAdapter(boolean defaultVisitNodes) {
        this.defaultVisitNodes = defaultVisitNodes;
    }

    public AstItemVisitorAdapter() {
        this(true);
    }

    @Override
    public boolean isAstValid() {
        return isAstValid;
    }

    protected void invalidateAst() {
        isAstValid = false;
    }

    @Override
    public boolean proceedWithNewLine(NewLine newLine) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitNewLine(NewLine newLine) {

    }

    @Override
    public void leaveNewLine(NewLine newLine) {

    }

    @Override
    public boolean proceedWithBooleanExpression(BooleanExpression booleanExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitBooleanExpression(BooleanExpression booleanExpression) {

    }

    @Override
    public void leaveBooleanExpression(BooleanExpression booleanExpression) {

    }

    @Override
    public void leaveBinaryOperatorExpression(BinaryOperatorExpression binaryOperatorExpression) {

    }

    @Override
    public void leaveClouBaustein(ClouBausteinImpl clouBaustein) {

    }

    @Override
    public void leaveConditionalStatementStatement(ConditionalStatement conditionalStatement) {

    }

    @Override
    public void leaveDecimalExpression(DecimalExpression decimalExpression) {

    }

    @Override
    public void leaveFixedText(FixedText fixedText) {

    }

    @Override
    public void leaveIdExpression(IdExpression idExpression) {

    }

    @Override
    public void leaveLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList) {

    }

    @Override
    public void leaveNumExpression(NumExpression numExpression) {

    }

    @Override
    public void leavePairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {

    }

    @Override
    public boolean proceedWithBinaryOperatorExpression(BinaryOperatorExpression binaryOperatorExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithClouBaustein(ClouBausteinImpl clouBaustein) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithConditionalStatement(ConditionalStatement conditionalStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithDecimalExpression(DecimalExpression decimalExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithFixedText(FixedText fixedText) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithIdExpression(IdExpression idExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithNumExpression(NumExpression numExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitBinaryOperatorExpression(BinaryOperatorExpression binaryOperatorExpression) {

    }

    @Override
    public void visitClouBaustein(ClouBausteinImpl clouBaustein) {

    }

    @Override
    public void visitConditionalStatementStatement(ConditionalStatement conditionalStatement) {

    }

    @Override
    public void visitDecimalExpression(DecimalExpression decimalExpression) {

    }

    @Override
    public void visitFixedText(FixedText fixedText) {

    }

    @Override
    public void visitIdExpression(IdExpression idExpression) {

    }

    @Override
    public void visitLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList) {

    }

    @Override
    public void visitNumExpression(NumExpression numExpression) {

    }

    @Override
    public void visitPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {

    }

    @Override
    public void leaveTextExpression(TextExpression textExpression) {

    }

    @Override
    public boolean proceedWithTextExpression(TextExpression textExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitTextExpression(TextExpression textExpression) {

    }

    @Override
    public void visitHitCommandStatement(HitCommandStatement hitCommandStatement) {

    }

    @Override
    public boolean proceedWithHitCommandStatement(HitCommandStatement hitCommandStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void leaveHitCommandStatement(HitCommandStatement hitCommandStatement) {

    }

    @Override
    public void leaveGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement) {

    }

    @Override
    public void leaveLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement) {

    }

    @Override
    public boolean proceedWithGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement) {

    }

    @Override
    public void visitLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement) {

    }

    @Override
    public void visitUnaryOperatorExpression(UnaryOperatorExpression unaryOperatorExpression) {

    }

    @Override
    public boolean proceedWithUnaryOperatorExpression(UnaryOperatorExpression unaryOperatorExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public void leaveUnaryOperatorExpression(UnaryOperatorExpression unaryOperatorExpression) {

    }

    @Override
    public void visitIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement) {

    }

    @Override
    public boolean proceedWithIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void leaveIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement) {

    }

    @Override
    public boolean proceedWithOpenFileCommand(OpenFileCommand openFileCommand) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitOpenFileCommand(OpenFileCommand openFileCommand) {

    }

    @Override
    public void leaveOpenFileCommand(OpenFileCommand openFileCommand) {

    }

    @Override
    public boolean proceedWithCloseFileCommand(CloseFileCommand closeFileCommand) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitCloseFileCommand(CloseFileCommand closeFileCommand) {

    }

    @Override
    public void leaveCloseFileCommand(CloseFileCommand closeFileCommand) {

    }

    @Override
    public boolean proceedWithDynamicValue(DynamicValue dynamicValue) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitDynamicValue(DynamicValue dynamicValue) {

    }

    @Override
    public void leaveDynamicValue(DynamicValue dynamicValue) {

    }

    @Override
    public boolean proceedWithAssignmentStatement(AssignmentStatement assignmentStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitAssignmentStatement(AssignmentStatement assignmentStatement) {

    }

    @Override
    public void leaveAssignmentStatement(AssignmentStatement assignmentStatement) {

    }

    @Override
    public void leaveWhileStatement(WhileStatement whileStatement) {

    }

    @Override
    public void visitWhileStatement(WhileStatement whileStatement) {

    }

    @Override
    public boolean proceedWithWhileStatement(WhileStatement whileStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithSwitchStatement(SwitchStatement switchStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitSwitchStatement(SwitchStatement switchStatement) {

    }

    @Override
    public void leaveSwitchStatement(SwitchStatement switchStatement) {

    }

    @Override
    public boolean proceedWithPairExpressionList(PairExpressionList pairExpressionList) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitPairExpressionList(PairExpressionList pairExpressionList) {

    }

    @Override
    public void leavePairExpressionList(PairExpressionList pairExpressionList) {

    }

    @Override
    public boolean proceedWithLastExpressionList(LastExpressionList lastExpressionList) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitLastExpressionList(LastExpressionList lastExpressionList) {

    }

    @Override
    public void leaveLastExpressionList(LastExpressionList lastExpressionList) {

    }

    @Override
    public boolean proceedWithMacroCallStatement(MacroCallStatement macroCallStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitMacroCallStatement(MacroCallStatement macroCallStatement) {

    }

    @Override
    public void leaveMacroCallStatement(MacroCallStatement macroCallStatement) {

    }

    @Override
    public boolean proceedWithMacroDefinitionStatement(MacroDefinitionStatement macroDefinitionStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitMacroDefinitionStatement(MacroDefinitionStatement macroDefinitionStatement) {

    }

    @Override
    public void leaveMacroDefinitionStatement(MacroDefinitionStatement macroDefinitionStatement) {

    }

    @Override
    public boolean proceedWithCaseStatementImpl(CaseStatementImpl caseStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitCaseStatementImpl(CaseStatementImpl caseStatement) {

    }

    @Override
    public void leaveCaseStatementImpl(CaseStatementImpl caseStatement) {

    }

    @Override
    public boolean proceedWithPairCaseStatement(PairCaseStatementList pairCaseStatementList) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitPairCaseStatementList(PairCaseStatementList pairCaseStatementList) {

    }

    @Override
    public void leavePairCaseStatementList(PairCaseStatementList pairCaseStatementList) {

    }

    @Override
    public boolean proceedWithLastCaseStatementList(LastCaseStatementList lastCaseStatementList) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitLastCaseStatementList(LastCaseStatementList lastCaseStatementList) {

    }

    @Override
    public void leaveLastCaseStatementList(LastCaseStatementList lastCaseStatementList) {

    }

    @Override
    public boolean proceedWithListEnumerationExpression(ListEnumerationExpression listEnumerationExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitListEnumerationExpression(ListEnumerationExpression listEnumerationExpression) {

    }

    @Override
    public void leaveListEnumerationExpression(ListEnumerationExpression listEnumerationExpression) {

    }

    @Override
    public boolean proceedWithGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement) {

    }

    @Override
    public void leaveGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement) {

    }

    @Override
    public boolean proceedWithLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement) {

    }

    @Override
    public void leaveLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement) {

    }

    @Override
    public boolean proceedWithListConcatenationStatement(ListConcatenationStatement listConcatenationStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitListConcatenationStatement(ListConcatenationStatement listConcatenationStatement) {

    }

    @Override
    public void leaveListConcatenationStatement(ListConcatenationStatement listConcatenationStatement) {

    }

    @Override
    public boolean proceedWithGStatement(GStatement gStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitGStatement(GStatement gStatement) {

    }

    @Override
    public void leaveGStatement(GStatement gStatement) {

    }

    @Override
    public boolean proceedWithJStatement(JStatement jStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitJStatement(JStatement jStatement) {

    }

    @Override
    public void leaveJStatement(JStatement jStatement) {

    }

    @Override
    public boolean proceedWithZStatement(ZStatement zStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitZStatement(ZStatement zStatement) {

    }

    @Override
    public void leaveZStatement(ZStatement zStatement) {

    }

    @Override
    public void leavePrintStatement(PrintStatement printStatement) {

    }

    @Override
    public void visitPrintStatement(PrintStatement printStatement) {

    }

    @Override
    public boolean proceedWithPrintStatement(PrintStatement printStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void leaveShellVariableExpression(ShellVariableExpression shellVariableExpression) {

    }

    @Override
    public void visitShellVariableExpression(ShellVariableExpression shellVariableExpression) {

    }

    @Override
    public boolean proceedWithShellVariableExpression(ShellVariableExpression shellVariableExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public void leaveShellCommand(ShellCommand shellCommand) {

    }

    @Override
    public void visitShellCommand(ShellCommand shellCommand) {

    }

    @Override
    public boolean proceedWithShellCommand(ShellCommand shellCommand) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithWriteStatement(WriteStatement writeStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitWriteStatement(WriteStatement writeStatement) {

    }

    @Override
    public void leaveWriteStatement(WriteStatement writeStatement) {

    }

    @Override
    public boolean proceedWithSubListExpression(SubListExpression subListExpression) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitSubListExpression(SubListExpression subListExpression) {

    }

    @Override
    public void leaveSubListExpression(SubListExpression subListExpression) {

    }

    @Override
    public boolean proceedWithClouFunctionCall(ClouFunctionCall clouFunctionCall) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitClouFunctionCall(ClouFunctionCall clouFunctionCall) {

    }

    @Override
    public void leaveClouFunctionCall(ClouFunctionCall clouFunctionCall) {

    }

    @Override
    public boolean proceedWithForStatement(ForStatement forStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitForStatement(ForStatement forStatement) {

    }

    @Override
    public void leaveForStatement(ForStatement forStatement) {

    }

    @Override
    public boolean proceedWithSectionStatement(SectionStatement sectionStatement) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitSectionStatement(SectionStatement sectionStatement) {

    }

    @Override
    public void leaveSectionStatement(SectionStatement sectionStatement) {

    }

    @Override
    public boolean proceedWithInsertDay(InsertDay insertDay) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitInsertDay(InsertDay insertDay) {

    }

    @Override
    public void leaveInsertDay(InsertDay insertDay) {

    }

    @Override
    public void leaveInsertMonth(InsertMonth insertMonth) {

    }

    @Override
    public void visitInsertMonth(InsertMonth insertMonth) {

    }

    @Override
    public boolean proceedWithInsertMonth(InsertMonth insertMonth) {
        return this.defaultVisitNodes;
    }

    @Override
    public boolean proceedWithInsertYear(InsertYear insertYear) {
        return this.defaultVisitNodes;
    }

    @Override
    public void visitInsertYear(InsertYear insertYear) {

    }

    @Override
    public void leaveInsertYear(InsertYear insertYear) {

    }
}

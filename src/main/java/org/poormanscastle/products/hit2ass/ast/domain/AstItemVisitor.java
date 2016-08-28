package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Implements aspects of the Hit/Clou AstTree, like semantic analysis, expression evaluation
 * and translation to DocDesign Workspaces.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public interface AstItemVisitor {

    /**
     * This method states whether the given Hit/Clou text component was found to be valid or not.
     *
     * @return
     */
    boolean isAstValid();

    boolean proceedWithClouBaustein(ClouBausteinImpl clouBaustein);

    void visitClouBaustein(ClouBausteinImpl clouBaustein);

    void leaveClouBaustein(ClouBausteinImpl clouBaustein);

    boolean proceedWithPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList);

    void visitPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList);

    void leavePairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList);

    boolean proceedWithLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList);

    void visitLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList);

    void leaveLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList);

    boolean proceedWithFixedText(FixedText fixedText);

    void visitFixedText(FixedText fixedText);

    void leaveFixedText(FixedText fixedText);

    boolean proceedWithConditionalStatement(ConditionalStatement conditionalStatement);

    void visitConditionalStatementStatement(ConditionalStatement conditionalStatement);

    void leaveConditionalStatementStatement(ConditionalStatement conditionalStatement);

    boolean proceedWithBinaryOperatorExpression(BinaryOperatorExpression binaryOperatorExpression);

    void visitBinaryOperatorExpression(BinaryOperatorExpression binaryOperatorExpression);

    void leaveBinaryOperatorExpression(BinaryOperatorExpression binaryOperatorExpression);

    boolean proceedWithDecimalExpression(DecimalExpression decimalExpression);

    void visitDecimalExpression(DecimalExpression decimalExpression);

    void leaveDecimalExpression(DecimalExpression decimalExpression);

    boolean proceedWithIdExpression(IdExpression idExpression);

    void visitIdExpression(IdExpression idExpression);

    void leaveIdExpression(IdExpression idExpression);

    boolean proceedWithNumExpression(NumExpression numExpression);

    void visitNumExpression(NumExpression numExpression);

    void leaveNumExpression(NumExpression numExpression);

    boolean proceedWithTextExpression(TextExpression textExpression);

    void visitTextExpression(TextExpression textExpression);

    void leaveTextExpression(TextExpression textExpression);

    boolean proceedWithHitCommandStatement(HitCommandStatement hitCommandStatement);

    void visitHitCommandStatement(HitCommandStatement hitCommandStatement);

    void leaveHitCommandStatement(HitCommandStatement hitCommandStatement);

    boolean proceedWithGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement);

    void visitGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement);

    void leaveGlobalDeclarationStatement(GlobalDeclarationStatement globalDeclarationStatement);

    boolean proceedWithLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement);

    void visitLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement);

    void leaveLocalDeclarationStatement(LocalDeclarationStatement localDeclarationStatement);

    boolean proceedWithUnaryOperatorExpression(UnaryOperatorExpression unaryOperatorExpression);

    void visitUnaryOperatorExpression(UnaryOperatorExpression unaryOperatorExpression);

    void leaveUnaryOperatorExpression(UnaryOperatorExpression unaryOperatorExpression);

    boolean proceedWithIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement);

    void visitIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement);

    void leaveIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement);

    boolean proceedWithOpenFileCommand(OpenFileCommand openFileCommand);

    void visitOpenFileCommand(OpenFileCommand openFileCommand);

    void leaveOpenFileCommand(OpenFileCommand openFileCommand);

    boolean proceedWithCloseFileCommand(CloseFileCommand closeFileCommand);

    void visitCloseFileCommand(CloseFileCommand closeFileCommand);

    void leaveCloseFileCommand(CloseFileCommand closeFileCommand);

    boolean proceedWithDynamicValue(DynamicValue dynamicValue);

    void visitDynamicValue(DynamicValue dynamicValue);

    void leaveDynamicValue(DynamicValue dynamicValue);

    boolean proceedWithAssignmentStatement(AssignmentStatement assignmentStatement);

    void visitAssignmentStatement(AssignmentStatement assignmentStatement);

    void leaveAssignmentStatement(AssignmentStatement assignmentStatement);

    boolean proceedWithWhileStatement(WhileStatement whileStatement);

    void visitWhileStatement(WhileStatement whileStatement);

    void leaveWhileStatement(WhileStatement whileStatement);

    boolean proceedWithCaseStatementImpl(CaseStatementImpl caseStatement);

    void visitCaseStatementImpl(CaseStatementImpl caseStatement);

    void leaveCaseStatementImpl(CaseStatementImpl caseStatement);

    boolean proceedWithSwitchStatement(SwitchStatement switchStatement);

    void visitSwitchStatement(SwitchStatement switchStatement);

    void leaveSwitchStatement(SwitchStatement switchStatement);

    boolean proceedWithPairCaseStatement(PairCaseStatementList pairCaseStatementList);

    void visitPairCaseStatementList(PairCaseStatementList pairCaseStatementList);

    void leavePairCaseStatementList(PairCaseStatementList pairCaseStatementList);

    boolean proceedWithLastCaseStatementList(LastCaseStatementList lastCaseStatementList);

    void visitLastCaseStatementList(LastCaseStatementList lastCaseStatementList);

    void leaveLastCaseStatementList(LastCaseStatementList lastCaseStatementList);

    boolean proceedWithPairExpressionList(PairExpressionList pairExpressionList);

    void visitPairExpressionList(PairExpressionList pairExpressionList);

    void leavePairExpressionList(PairExpressionList pairExpressionList);

    boolean proceedWithLastExpressionList(LastExpressionList lastExpressionList);

    void visitLastExpressionList(LastExpressionList lastExpressionList);

    void leaveLastExpressionList(LastExpressionList lastExpressionList);

    boolean proceedWithMacroCallStatement(MacroCallStatement macroCallStatement);

    void visitMacroCallStatement(MacroCallStatement macroCallStatement);

    void leaveMacroCallStatement(MacroCallStatement macroCallStatement);

    boolean proceedWithMacroDefinitionStatement(MacroDefinitionStatement macroDefinitionStatement);

    void visitMacroDefinitionStatement(MacroDefinitionStatement macroDefinitionStatement);

    void leaveMacroDefinitionStatement(MacroDefinitionStatement macroDefinitionStatement);

    boolean proceedWithListEnumerationExpression(ListEnumerationExpression listEnumerationExpression);

    void visitListEnumerationExpression(ListEnumerationExpression listEnumerationExpression);

    void leaveListEnumerationExpression(ListEnumerationExpression listEnumerationExpression);

    boolean proceedWithGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement);

    void visitGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement);

    void leaveGlobalListDeclarationStatement(GlobalListDeclarationStatement globalListDeclarationStatement);

    boolean proceedWithLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement);

    void visitLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement);

    void leaveLocalListDeclarationStatement(LocalListDeclarationStatement localListDeclarationStatement);

    boolean proceedWithListConcatenationStatement(ListConcatenationStatement listConcatenationStatement);

    void visitListConcatenationStatement(ListConcatenationStatement listConcatenationStatement);

    void leaveListConcatenationStatement(ListConcatenationStatement listConcatenationStatement);

    boolean proceedWithGStatement(GStatement gStatement);

    void visitGStatement(GStatement gStatement);

    void leaveGStatement(GStatement gStatement);

    boolean proceedWithJStatement(JStatement jStatement);

    void visitJStatement(JStatement jStatement);

    void leaveJStatement(JStatement jStatement);

    boolean proceedWithZStatement(ZStatement zStatement);

    void visitZStatement(ZStatement zStatement);

    void leaveZStatement(ZStatement zStatement);

    boolean proceedWithPrintStatement(PrintStatement printStatement);

    void visitPrintStatement(PrintStatement printStatement);

    void leavePrintStatement(PrintStatement printStatement);

    boolean proceedWithShellVariableExpression(ShellVariableExpression shellVariableExpression);

    void visitShellVariableExpression(ShellVariableExpression shellVariableExpression);

    void leaveShellVariableExpression(ShellVariableExpression shellVariableExpression);

    boolean proceedWithShellCommand(ShellCommand shellCommand);

    void visitShellCommand(ShellCommand shellCommand);

    void leaveShellCommand(ShellCommand shellCommand);

    boolean proceedWithWriteStatement(WriteStatement writeStatement);

    void visitWriteStatement(WriteStatement writeStatement);

    void leaveWriteStatement(WriteStatement writeStatement);

    boolean proceedWithSubListExpression(SubListExpression subListExpression);

    void visitSubListExpression(SubListExpression subListExpression);

    void leaveSubListExpression(SubListExpression subListExpression);

    boolean proceedWithClouFunctionCall(ClouFunctionCall clouFunctionCall);

    void visitClouFunctionCall(ClouFunctionCall clouFunctionCall);

    void leaveClouFunctionCall(ClouFunctionCall clouFunctionCall);

    boolean proceedWithForStatement(ForStatement forStatement);

    void visitForStatement(ForStatement forStatement);

    void leaveForStatement(ForStatement forStatement);

    boolean proceedWithSectionStatement(SectionStatement sectionStatement);

    void visitSectionStatement(SectionStatement sectionStatement);

    void leaveSectionStatement(SectionStatement sectionStatement);

    boolean proceedWithBooleanExpression(BooleanExpression booleanExpression);

    void visitBooleanExpression(BooleanExpression booleanExpression);

    void leaveBooleanExpression(BooleanExpression booleanExpression);

    boolean proceedWithNewLine(NewLine newLine);

    void visitNewLine(NewLine newLine);

    void leaveNewLine(NewLine newLine);

}

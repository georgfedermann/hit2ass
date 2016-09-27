package org.poormanscastle.products.hit2ass.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.poormanscastle.products.hit2ass.TestUtils;
import org.poormanscastle.products.hit2ass.ast.domain.BinaryOperator;
import org.poormanscastle.products.hit2ass.ast.domain.BinaryOperatorExpression;
import org.poormanscastle.products.hit2ass.ast.domain.CaseStatement;
import org.poormanscastle.products.hit2ass.ast.domain.CaseStatementList;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.ClouFunctionCall;
import org.poormanscastle.products.hit2ass.ast.domain.ConditionalStatement;
import org.poormanscastle.products.hit2ass.ast.domain.ExpressionList;
import org.poormanscastle.products.hit2ass.ast.domain.FixedText;
import org.poormanscastle.products.hit2ass.ast.domain.GlobalDeclarationStatement;
import org.poormanscastle.products.hit2ass.ast.domain.IdExpression;
import org.poormanscastle.products.hit2ass.ast.domain.InsertDay;
import org.poormanscastle.products.hit2ass.ast.domain.InsertMonth;
import org.poormanscastle.products.hit2ass.ast.domain.InsertYear;
import org.poormanscastle.products.hit2ass.ast.domain.LastExpressionList;
import org.poormanscastle.products.hit2ass.ast.domain.MacroCallStatement;
import org.poormanscastle.products.hit2ass.ast.domain.NewLine;
import org.poormanscastle.products.hit2ass.ast.domain.NumExpression;
import org.poormanscastle.products.hit2ass.ast.domain.PairClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.PrintStatement;
import org.poormanscastle.products.hit2ass.ast.domain.SectionStatement;
import org.poormanscastle.products.hit2ass.ast.domain.SwitchStatement;
import org.poormanscastle.products.hit2ass.ast.domain.TextExpression;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.transformer.EraseBlanksVisitor;

/**
 * Created by georg on 28.08.16.
 */
public class ClouChunkTest {

    @Test
    public void ifWithLogicalAndTest() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("IfWithLogicalAnd"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((ClouBausteinElementList) baustein.getClouBausteinElement());assertTrue(true);assertEquals(BinaryOperator.AND, ((BinaryOperatorExpression) ((ConditionalStatement) elementList.getHead()).getCondition()).getOperator());
    }

    @Test
    public void emptyCaseTest() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("EmptyCase"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((ClouBausteinElementList) baustein.getClouBausteinElement());
        SwitchStatement switchStatement = (SwitchStatement) elementList.getHead();
        assertEquals("someVar", ((IdExpression) switchStatement.getExpression()).getId());
        CaseStatement caseStatement = ((CaseStatementList) switchStatement.getCaseStatement()).getHead();
        assertEquals("label1", caseStatement.getMatch());
        assertNull(caseStatement.getClouBausteinElement());
        caseStatement = ((CaseStatementList) switchStatement.getCaseStatement()).getTail().getHead();
        assertEquals("label2", caseStatement.getMatch());
        assertEquals(" Live and let live.", ((FixedText) ((PairClouBausteinElementList) caseStatement.getClouBausteinElement()).getTail().getHead()).getText());
    }

    @Test
    public void commentWithSingleBlankTest() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("CommentWithSingleBlank"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((ClouBausteinElementList) baustein.getClouBausteinElement());
        PrintStatement printStatement = (PrintStatement) elementList.getHead();
        assertEquals("someVar", ((IdExpression) printStatement.getExpression()).getId());
        elementList = elementList.getTail();
        assertEquals(", some text", ((FixedText) elementList.getHead()).getText());
    }

    @Test
    public void dotsInCaseStatementLabelTest() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("DotsInCaseStatementLabel"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((ClouBausteinElementList) baustein.getClouBausteinElement());
        SwitchStatement switchStatement = (SwitchStatement) elementList.getHead();
        assertEquals("someName", ((IdExpression) switchStatement.getExpression()).getId());
        assertEquals("Some.Label", ((CaseStatementList) switchStatement.getCaseStatement()).getHead().getMatch());
    }

    @Test
    public void blanksInIndexExpressionTest() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("BlanksInIndexExpression"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((ClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("In den Kalenderjahren ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        PrintStatement printStatement = (PrintStatement) elementList.getHead();
        IdExpression idExpression = (IdExpression) printStatement.getExpression();
        assertEquals("listendlos2", idExpression.getId());
        elementList = elementList.getTail();
        assertEquals(" bis ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        printStatement = (PrintStatement) elementList.getHead();
        idExpression = (IdExpression) printStatement.getExpression();
        assertEquals("listendlos2", idExpression.getId());
        assertEquals(BinaryOperator.MINUS, ((BinaryOperatorExpression) ((LastExpressionList) idExpression.getIdxExp1()).getHead()).getOperator());
    }

    @Test
    public void insertDateTest() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("InsertDate"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("Wien, am ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals(InsertDay.class, elementList.getHead().getClass());
        elementList = elementList.getTail();
        assertEquals(".", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals(InsertMonth.class, elementList.getHead().getClass());
        elementList = elementList.getTail();
        assertEquals(".", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals(InsertYear.class, elementList.getHead().getClass());
        elementList = elementList.getTail();
        assertEquals(SectionStatement.class, elementList.getHead().getClass());
    }

    @Test
    public void localVariableTest() throws Exception {
        //  #
        //  #? flag <> 4
        //      /J
        //          #d varName "value"
        //  #
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("LocalVariableDeclaration"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
    }

    @Test
    public void functionVarNamesWithUmlauts() throws Exception {
        //  #
        //  #D äüöÄÖÜß 14
        //  #D üäöÄÖÜß 16
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("VarNamesWithUmlauts"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        GlobalDeclarationStatement probe = (GlobalDeclarationStatement) elementList.getHead();
        assertEquals("äüöÄÖÜß", probe.getId());
        assertEquals(Integer.valueOf(14), ((NumExpression) probe.getExpression()).getValue());
        probe = (GlobalDeclarationStatement) elementList.getTail().getHead();
        assertEquals("üäöÄÖÜß", probe.getId());
        assertEquals(Integer.valueOf(16), ((NumExpression) probe.getExpression()).getValue());
    }

    @Test
    public void functionCallTest() throws Exception {
        //  #
        //  #$ steuerzeile("einfügen", "Schriftart", "Arial:20")
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("FunctionCall"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        MacroCallStatement macroCall = (MacroCallStatement) elementList.getHead();
        assertEquals("steuerzeile", macroCall.getMacroId());
        ExpressionList argList = macroCall.getArgumentList();
        assertEquals("einfügen", ((TextExpression) argList.getHead()).getValue());
        argList = argList.getTail();
        assertEquals("Schriftart", ((TextExpression) argList.getHead()).getValue());
        argList = argList.getTail();
        assertEquals("Arial:20", ((TextExpression) argList.getHead()).getValue());
    }

    @Test
    public void IndexedVariableSubstring() throws Exception {
        //  #
        //  #? stringArray[1][1,2] = "EN":
        //      /J
        //  This means war.
        //      /N
        //  Das bedeutet Krieg.
        //  #
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("IndexedVariableSubstring"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertTrue(elementList.getHead() instanceof ConditionalStatement);

        assertEquals(" This means war.", ((FixedText) ((ConditionalStatement) elementList.getHead()).getThenElement().getTail().getHead()).getText());
        assertEquals(" Das bedeutet Krieg.", ((FixedText) ((ConditionalStatement) elementList.getHead()).getElseElement().getTail().getHead()).getText());

        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);

    }

    @Test
    public void strangeVariableIfSequenceTest() throws Exception {
        // #
        // Some fixed text.@
        // ^1( var ^ ^?
        // #? a > 0 :
        //    /J
        //        yes@
        //    /N
        //        no@
        // #
        // More text.@
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("StrangeVariableIfSequence"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("Some fixed text.", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof SectionStatement);
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
    }

    @Test
    public void strangeVariableTest() throws Exception {
        // #
        // Some fixed text
        // ^1( var ^ ^?
        // More text
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("StrangeVariable"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("Some fixed text", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertEquals("More text", ((FixedText) elementList.getHead()).getText());
    }

    @Test
    public void testMacroStatementFixedTextSequence() throws Exception {
        // #
        // #$ BOLD_ON Please note $# BOLD_OFF
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("MacroStatementFixedTextSequence"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("BOLD_ON", ((MacroCallStatement) elementList.getHead()).getMacroId());
        elementList = elementList.getTail();
        assertEquals(" Please note ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals("BOLD_OFF", ((MacroCallStatement) elementList.getHead()).getMacroId());
    }

    @Test
    public void testMacroCallMacroCallSequence() throws Exception {
        // #
        // #$TABU#$BOLDON#$BOLDOFF
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("MacroCallMacroCallSequence"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("TABU", ((MacroCallStatement) elementList.getHead()).getMacroId());
        elementList = elementList.getTail();
        assertEquals("BOLDON", ((MacroCallStatement) elementList.getHead()).getMacroId());
        elementList = elementList.getTail();
        assertEquals("BOLDOFF", ((MacroCallStatement) elementList.getHead()).getMacroId());
    }

    @Test
    public void testSwitchWithClouFunctionCall() throws Exception {
        // #
        // #C strlen(zahlpunktdazu)
        //  /4:
        //      Some fixed text.
        //  /"text":
        //        Some other fixed text.
        //  /:
        //        Some default text.
        // #
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("SwitchWithClouFunctionCall"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        SwitchStatement switchStatement = (SwitchStatement) elementList.getHead();
        assertEquals("strlen", ((ClouFunctionCall) switchStatement.getExpression()).getFunctionName());
        CaseStatementList caseStatementList = (CaseStatementList) switchStatement.getCaseStatement();
        assertEquals("4", caseStatementList.getHead().getMatch());
        caseStatementList = caseStatementList.getTail();
        assertEquals("text", caseStatementList.getHead().getMatch());
        caseStatementList = caseStatementList.getTail();
        assertEquals("", caseStatementList.getHead().getMatch());
        assertNull(caseStatementList.getTail());

        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();

    }

    @Test
    public void testMacroStatementPrintStatementSequence() throws Exception {
        // #
        //#$TABU #> currency #> amount.
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("MacroStatementPrintStatementSequence"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("TABU", ((MacroCallStatement) elementList.getHead()).getMacroId());
        elementList = elementList.getTail();
        assertEquals("currency", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        assertEquals(" ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals("amount", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        assertEquals(".", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertEquals("TABU", ((MacroCallStatement) elementList.getHead()).getMacroId());
        elementList = elementList.getTail();
        assertEquals("currency", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        assertEquals(" ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals("amount", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
    }

    @Test
    public void testPrintStatementMacroCallSequence() throws Exception {
        // #
        // #> currency#$TABU#*##> amount@
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("PrintStatementMacroCallSequence"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("currency", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        assertEquals("TABU", ((MacroCallStatement) elementList.getHead()).getMacroId());
        elementList = elementList.getTail();
        assertEquals("amount", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof SectionStatement);
    }

    @Test
    public void testFixedTextConditionWithPrintStatement() throws Exception {
        // #
        // And now
        // #? power > 5 :
        //  /J
        //      #> firstName
        //  /N
        //      #> lastName
        // #
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("FixedTextConditionWithPrintStatement"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("And now ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof ConditionalStatement);
    }

    @Test
    public void testFixedTextNewLinePrintStatementSequence() throws Exception {
        // #
        // and
        // #> lastMember
        //     and
        //     #> lastMember
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("FixedTextNewLinePrintStatementSequence"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("and ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertEquals("lastMember", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertEquals(" and ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertEquals("", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals("lastMember", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
    }

    @Test
    public void testFixedTextIfFixedTextSequence() throws Exception {
        // #
        // Please note that we
        // #? someVar = 1 :
        //    /J
        //      will
        //    /N
        //      won't
        // #
        // process your order.
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("FixedTextIfFixedTextSequence"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("Please note that we", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();

        ConditionalStatement ifStatement = (ConditionalStatement) elementList.getHead();
        ifStatement.getThenElement().getHead();
        assertEquals(" will", ((FixedText) ifStatement.getThenElement().getTail().getHead()).getText());
        assertEquals(" won't", ((FixedText) ifStatement.getElseElement().getTail().getHead()).getText());

        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertEquals(" process your order.", ((FixedText) elementList.getHead()).getText());
    }

    @Test
    public void testPrintStatementBlankFixedTextSequence() throws Exception {
        // #
        // Since your name is #> firstName you will get a present.
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("PrintStatementBlankFixedTextSequence"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("Since your name is ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals("firstName", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        assertEquals(" you will get a present.", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
    }

    @Test
    public void testFixedTextWithSpecialCharacters() throws Exception {
        // #
        // äöüÄÖÜß()""_-.,ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("FixedTextWithSpecialCharacters"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("äöüÄÖÜß()\"\"_-.,ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", ((FixedText) elementList.getHead()).getText());
    }

    @Test
    public void testPrintStatementNewLineFixedTextSequence() throws Exception {
        // #
        // Your name is #> firstname
        // and that is great!@
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("PrintStatementNewLineFixedTextSequence"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        assertEquals("Your name is ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals("firstname", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertEquals(" and that is great!", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof SectionStatement);
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
    }

    @Test
    public void testSectionStatement() throws Exception {
        // #
        // SectionStatement after FixedText.@
        // SectionStatement after a PrintStatement #> firstName@
        // SectionStatement alone in a line@
        // @
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("SectionStatement"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        // check that FixedText and SectionStatement are correctly separated
        assertEquals("SectionStatement after FixedText.", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        // check that SectionStatement is correctly scanned
        assertTrue(elementList.getHead() instanceof SectionStatement);
        elementList = elementList.getTail();
        // check that NewLine is preserved
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertEquals("SectionStatement after a PrintStatement ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertEquals("firstName", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        // check that SectionStatement is recognized directly after a PrintStatement
        assertTrue(elementList.getHead() instanceof SectionStatement);
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertEquals("SectionStatement alone in a line", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof SectionStatement);
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof SectionStatement);
        elementList = elementList.getTail();
        assertTrue(elementList.getHead() instanceof NewLine);
    }

    @Test
    public void testPrintStatementWithQuotes() throws Exception {
        // #
        // You are "#> firstName".
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("PrintStatementWithQuotes"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        // Check that FixedText is parsed correctly including quotes
        assertEquals("You are \"", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        // Check that PrintStatement directly follows FixedText without a blank in between.
        assertEquals("firstName", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        // Check that the quote directly follows the PrintStatement, without a blank in between.
        assertEquals("\".", ((FixedText) elementList.getHead()).getText());
    }


    @Test
    public void testMultipleBlanksBetweenFixedTextAndComment() throws Exception {
        // #
        // You are #> firstName #> lastName.     #* one blank should remain after the dot. #
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("MultipleBlanksBetweenFixedTextAndComment"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        // Check that multiple sequential words are read into one FixedText element, and that single blanks
        // are preserved between FixedText and PrintStatements
        assertEquals("You are ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        // check that the PrintStatement is recognized, and that the ID is correctly scanned, without leading or trailing blanks.
        assertEquals("firstName", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        // check that the blank between PrintStatements is preserved
        assertEquals(" ", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        // check that the PrintStatement is recognized
        assertEquals("lastName", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        // check that the trailing text is correctly scanned and that trailing blanks between FixedText and comment get stripped.
        assertEquals(". ", ((FixedText) elementList.getHead()).getText());
    }

    @Test
    public void testTabuMakro() throws Exception {
        // #
        // Your name is#$TABU#*##> firstName, right?@
        // And so it ends.
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("TabuMakroTest"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((PairClouBausteinElementList) baustein.getClouBausteinElement());
        // check that no blanks are inserted between FixedText and directly following macro call
        assertEquals("Your name is", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        // check that the macro call is recognized and the command is correctly scanned.
        MacroCallStatement macroCall = (MacroCallStatement) elementList.getHead();
        assertEquals("TABU", macroCall.getMacroId());
        assertNull(macroCall.getArgumentList());
        elementList = elementList.getTail();
        // check that the pseudo comment #*# suppresses insertion of blanks and that so the next element is the PrintStatement
        assertEquals("firstName", ((IdExpression) ((PrintStatement) elementList.getHead()).getExpression()).getId());
        elementList = elementList.getTail();
        // check that the complete string ", right?" is parsed as one fixed text token, and that the @ goes into the next token.
        assertEquals(", right?", ((FixedText) elementList.getHead()).getText());
        elementList = elementList.getTail();
        // check that the @ is recognized as a SectionStatement
        assertTrue(elementList.getHead() instanceof SectionStatement);
        elementList = elementList.getTail();
        // assert that the NewLine after the SectionStatement is preserved
        assertTrue(elementList.getHead() instanceof NewLine);
        elementList = elementList.getTail();
        // assert that the next line is recognized as a FixedText, and that no leading blank is added, since this line
        // is not part of a longer flow text.
        assertEquals("And so it ends.", ((FixedText) elementList.getHead()).getText());
    }

}

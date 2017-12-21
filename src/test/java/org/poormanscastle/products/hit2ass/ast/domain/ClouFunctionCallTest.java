package org.poormanscastle.products.hit2ass.ast.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.poormanscastle.products.hit2ass.TestUtils;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;

/**
 * Created by georg on 21/12/2017.
 */
public class ClouFunctionCallTest {
    @Test
    public void evaluateDateExpressionInFdateTest() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("fdateExpressionTest"), "ISO8859-1");
        ClouBaustein baustein = parser.CB();

        BinaryOperatorExpression plus365DaysExpression = (BinaryOperatorExpression)
                ((PairExpressionList)
                        ((ClouFunctionCall)
                                ((GlobalDeclarationStatement)
                                        ((PairClouBausteinElementList)
                                                ((PairClouBausteinElementList)
                                                        ((PairClouBausteinElementList) baustein.getClouBausteinElement()).getTail()).getTail()).getHead()).getExpression()).getArgs()).getHead();

        BinaryOperatorExpression minus365DaysExpression = (BinaryOperatorExpression)
                ((PairExpressionList)
                        ((ClouFunctionCall)
                                ((GlobalDeclarationStatement)
                                        ((PairClouBausteinElementList)
                                                ((PairClouBausteinElementList)
                                                        ((PairClouBausteinElementList)
                                                                ((PairClouBausteinElementList) baustein.getClouBausteinElement()).getTail()).getTail()).getTail()).getHead()).getExpression()).getArgs()).getHead();

        BinaryOperatorExpression plus100DaysExpression = (BinaryOperatorExpression)
                ((PairExpressionList)
                        ((ClouFunctionCall)
                                ((GlobalDeclarationStatement)
                                        ((LastClouBausteinElementList)
                                                ((PairClouBausteinElementList)
                                                        ((PairClouBausteinElementList)
                                                                ((PairClouBausteinElementList)
                                                                        ((PairClouBausteinElementList) baustein.getClouBausteinElement()).getTail()).getTail()).getTail()).getTail()).getHead()).getExpression()).getArgs()).getHead();

        assertEquals(" fn:current-date() + xs:dayTimeDuration('PT31536000S') ", ClouFunctionCall.evaluateDateExpressionInFdate(plus365DaysExpression));
        assertEquals(" fn:current-date() + xs:dayTimeDuration('-PT31536000S') ", ClouFunctionCall.evaluateDateExpressionInFdate(minus365DaysExpression));
        assertEquals(" fn:current-date() + xs:dayTimeDuration('PT8640000S') ", ClouFunctionCall.evaluateDateExpressionInFdate(plus100DaysExpression));
    }

    @Test
    public void applyPatternInFdateTest() throws Exception {
        assertEquals(" fn:substring(string(100 + fn:month-from-date(fn:current-date())), 2) ", ClouFunctionCall.applyPatternInFdate("fn:current-date()", "0M"));
        assertEquals(" fn:concat(fn:substring(string(100 + fn:month-from-date(fn:current-date())), 2),fn:substring(string(100 + fn:day-from-date(fn:current-date())), 2)) ", ClouFunctionCall.applyPatternInFdate("fn:current-date()", "0M0T"));
        assertEquals(" fn:concat( fn:year-from-date(fn:current-date()), fn:concat(fn:substring(string(100 + fn:month-from-date(fn:current-date())), 2),fn:substring(string(100 + fn:day-from-date(fn:current-date())), 2))) ", ClouFunctionCall.applyPatternInFdate("fn:current-date()", "JJ0M0T"));
        assertEquals(" fn:year-from-date(fn:current-date()) ", ClouFunctionCall.applyPatternInFdate("fn:current-date()", "JJ"));
        assertEquals(" fn:concat( fn:substring(string(100 + fn:day-from-date(fn:current-date())), 2), '.', fn:concat(fn:substring(string(100 + fn:month-from-date(fn:current-date())), 2), '.', fn:year-from-date(fn:current-date()))) ", ClouFunctionCall.applyPatternInFdate("fn:current-date()", "0T.0M.JJ"));
    }

}
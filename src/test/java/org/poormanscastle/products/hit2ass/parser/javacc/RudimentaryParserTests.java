package org.poormanscastle.products.hit2ass.parser.javacc;

import org.junit.Test;
import org.poormanscastle.products.hit2ass.TestUtils;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public class RudimentaryParserTests {

    HitAssAstParser parser;

    @Test
    public void testEmptyClouBaustein() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("emptyClou"), "UTF-8");
        parser.CB();
//        ClouBaustein baustein = parser.CB();
        //    assertNotNull(baustein);
    }

    @Test
    public void testComments() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("Comments"), "UTF-8");
        parser.CB();
//        ClouBaustein baustein = parser.CB();
        //  assertNotNull(baustein);
    }

    @Test
    public void testCommentsAndFixedText() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("CommentsAndFixedText"), "UTF-8");
        ClouBaustein baustein = parser.CB();
        assertNotNull(baustein);
    }

    @Test
    public void testIfThen() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("IfThen"), "UTF-8");
        ClouBaustein baustein = parser.CB();
        assertNotNull(baustein);
        assertEquals("ClouBausteinImpl{codePosition=begin line/column 4/3; end line/column 4/14, clouBausteinElement=PairClouBausteinElementList{codePosition=begin line/column 4/3; end line/column 4/14, head=FixedTextImpl{codePosition=begin line/column 4/3; end line/column 4/14, textBuffer=Gleichzeitig}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/16; end line/column 4/23, head=FixedTextImpl{codePosition=begin line/column 4/16; end line/column 4/23, textBuffer=ersuchen}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/25; end line/column 4/27, head=FixedTextImpl{codePosition=begin line/column 4/25; end line/column 4/27, textBuffer=wir}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/29; end line/column 4/31, head=FixedTextImpl{codePosition=begin line/column 4/29; end line/column 4/31, textBuffer=den}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/33; end line/column 4/37, head=FixedTextImpl{codePosition=begin line/column 4/33; end line/column 4/37, textBuffer=(das)}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/39; end line/column 4/52, head=FixedTextImpl{codePosition=begin line/column 4/39; end line/column 4/52, textBuffer=beiliegende(n)}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/54; end line/column 4/61, head=FixedTextImpl{codePosition=begin line/column 4/54; end line/column 4/61, textBuffer=Bescheid}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/63; end line/column 4/68, head=FixedTextImpl{codePosition=begin line/column 4/63; end line/column 4/68, textBuffer=(INFO)}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/3; end line/column 5/12, head=FixedTextImpl{codePosition=begin line/column 5/3; end line/column 5/12, textBuffer=anlässlich}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/14; end line/column 5/16, head=FixedTextImpl{codePosition=begin line/column 5/14; end line/column 5/16, textBuffer=der}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/18; end line/column 5/28, head=FixedTextImpl{codePosition=begin line/column 5/18; end line/column 5/28, textBuffer=Information}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/30; end line/column 5/31, head=FixedTextImpl{codePosition=begin line/column 5/30; end line/column 5/31, textBuffer=zu}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/33; end line/column 5/42, head=FixedTextImpl{codePosition=begin line/column 5/33; end line/column 5/42, textBuffer=expedieren}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/44; end line/column 5/47, head=FixedTextImpl{codePosition=begin line/column 5/44; end line/column 5/47, textBuffer=bzw.}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/49; end line/column 5/61, head=FixedTextImpl{codePosition=begin line/column 5/49; end line/column 5/61, textBuffer=auszuhändigen}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/63; end line/column 5/65, head=FixedTextImpl{codePosition=begin line/column 5/63; end line/column 5/65, textBuffer=und}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/3; end line/column 6/7, head=FixedTextImpl{codePosition=begin line/column 6/3; end line/column 6/7, textBuffer=einen}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/9; end line/column 6/22, head=FixedTextImpl{codePosition=begin line/column 6/9; end line/column 6/22, textBuffer=entsprechenden}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/24; end line/column 6/42, head=FixedTextImpl{codePosition=begin line/column 6/24; end line/column 6/42, textBuffer=Expedierungsvermerk}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/44; end line/column 6/55, head=FixedTextImpl{codePosition=begin line/column 6/44; end line/column 6/55, textBuffer=anzubringen.}, tail=LastClouBausteinElementList{head=ConditionalStatement{codePosition=begin line/column 8/6; end line/column 8/20, condition=BinaryOperatorExpression{codePosition=begin line/column 8/6; end line/column 8/20, lhs=IdExpression{id='bauname_äöüßÄÖÜ', valueType=null, value=null}, operator=EQ, rhs=TextExpression{codePosition=begin line/column 8/24; end line/column 8/30, value='AL001'}, value=null}, thenElement=PairClouBausteinElementList{codePosition=begin line/column 11/7; end line/column 11/9, head=FixedTextImpl{codePosition=begin line/column 11/7; end line/column 11/9, textBuffer=den}, tail=LastClouBausteinElementList{head=FixedTextImpl{codePosition=begin line/column 11/11; end line/column 11/20, textBuffer=Treppenakt}}}, elseElement=null}}}}}}}}}}}}}}}}}}}}}}}", baustein.toString());
    }

    @Test
    public void testIfThenElse() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("IfThenElse"), "UTF-8");
        ClouBaustein baustein = parser.CB();
        assertNotNull(baustein);
    }

    @Test
    public void testFunctionCall() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("FunctionCall"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        assertNotNull(baustein);
        assertEquals("ClouBausteinImpl{codePosition=begin line/column 4/5; end line/column 4/16, clouBausteinElement=PairClouBausteinElementList{codePosition=begin line/column 4/5; end line/column 4/16, head=FixedTextImpl{codePosition=begin line/column 4/5; end line/column 4/16, textBuffer=Gleichzeitig}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/18; end line/column 4/25, head=FixedTextImpl{codePosition=begin line/column 4/18; end line/column 4/25, textBuffer=ersuchen}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/27; end line/column 4/29, head=FixedTextImpl{codePosition=begin line/column 4/27; end line/column 4/29, textBuffer=wir}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/31; end line/column 4/34, head=FixedTextImpl{codePosition=begin line/column 4/31; end line/column 4/34, textBuffer=Sie,}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/36; end line/column 4/42, head=FixedTextImpl{codePosition=begin line/column 4/36; end line/column 4/42, textBuffer=zeitnah}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/44; end line/column 4/48, head=FixedTextImpl{codePosition=begin line/column 4/44; end line/column 4/48, textBuffer=tätig}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/50; end line/column 4/51, head=FixedTextImpl{codePosition=begin line/column 4/50; end line/column 4/51, textBuffer=zu}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/53; end line/column 4/59, head=FixedTextImpl{codePosition=begin line/column 4/53; end line/column 4/59, textBuffer=werden.}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/14; end line/column 6/20, head=GlobalDeclarationStatement{codePosition=begin line/column 6/14; end line/column 6/20, expression=TextExpression{codePosition=begin line/column 6/14; end line/column 6/20, value='James'}, id='firstName', formatDefinition=''}, tail=PairClouBausteinElementList{codePosition=begin line/column 7/4; end line/column 7/11, head=GlobalListDeclarationStatement{codePosition=begin line/column 7/4; end line/column 7/11, listId='quartal1', listExpression=AbstractAstItem{codePosition=begin line/column 7/17; end line/column 7/24}}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/14; end line/column 9/19, head=AssignmentStatement{codePosition=begin line/column 9/14; end line/column 9/19, id='firstName', expression=TextExpression{codePosition=begin line/column 9/14; end line/column 9/19, value='John'}}, tail=PairClouBausteinElementList{codePosition=begin line/column 10/16; end line/column 10/16, head=GlobalDeclarationStatement{codePosition=begin line/column 10/16; end line/column 10/16, expression=NumExpression{codePosition=begin line/column 10/16; end line/column 10/16, value=0}, id='listschleif', formatDefinition=''}, tail=PairClouBausteinElementList{codePosition=begin line/column 11/20; end line/column 11/20, head=GlobalDeclarationStatement{codePosition=begin line/column 11/20; end line/column 11/20, expression=NumExpression{codePosition=begin line/column 11/20; end line/column 11/20, value=0}, id='durchlaufzähler', formatDefinition=''}, tail=PairClouBausteinElementList{codePosition=begin line/column 12/16; end line/column 12/16, head=GlobalDeclarationStatement{codePosition=begin line/column 12/16; end line/column 12/16, expression=NumExpression{codePosition=begin line/column 12/16; end line/column 12/16, value=5}, id='list df anz', formatDefinition=''}, tail=PairClouBausteinElementList{codePosition=begin line/column 14/16; end line/column 14/16, head=AssignmentStatement{codePosition=begin line/column 14/16; end line/column 14/16, id='listschleif', expression=BinaryOperatorExpression{codePosition=begin line/column 14/16; end line/column 14/16, lhs=NumExpression{codePosition=begin line/column 14/16; end line/column 14/16, value=3}, operator=PLUS, rhs=BinaryOperatorExpression{codePosition=begin line/column 14/20; end line/column 14/21, lhs=BinaryOperatorExpression{codePosition=begin line/column 14/20; end line/column 14/21, lhs=NumExpression{codePosition=begin line/column 14/20; end line/column 14/21, value=20}, operator=DIV, rhs=NumExpression{codePosition=begin line/column 14/25; end line/column 14/25, value=5}, value=null}, operator=MINUS, rhs=NumExpression{codePosition=begin line/column 14/29; end line/column 14/29, value=2}, value=null}, value=null}}, tail=PairClouBausteinElementList{codePosition=begin line/column 16/4; end line/column 16/18, head=ConditionalStatement{codePosition=begin line/column 16/4; end line/column 16/18, condition=BinaryOperatorExpression{codePosition=begin line/column 16/4; end line/column 16/18, lhs=IdExpression{id='durchlaufzähler', valueType=null, value=null}, operator=LTE, rhs=BinaryOperatorExpression{codePosition=begin line/column 16/23; end line/column 16/33, lhs=IdExpression{id='listschleif', valueType=null, value=null}, operator=MINUS, rhs=NumExpression{codePosition=begin line/column 16/37; end line/column 16/37, value=2}, value=null}, value=null}, thenElement=LastClouBausteinElementList{head=HitCommandStatement{codePosition=begin line/column 18/5; end line/column 18/6, hitCommand=RETURN, repetitor=null, verstarkt=false}}, elseElement=null}, tail=PairClouBausteinElementList{codePosition=begin line/column 21/4; end line/column 21/10, head=ConditionalStatement{codePosition=begin line/column 21/4; end line/column 21/10, condition=BinaryOperatorExpression{codePosition=begin line/column 21/4; end line/column 21/10, lhs=ClouFunctionCall{codePosition=begin line/column 21/4; end line/column 21/10, functionName='listlen', args=GlobalListDeclarationStatement{codePosition=begin line/column 21/12; end line/column 21/21head='TextExpression{codePosition=begin line/column 21/12; end line/column 21/21, value='quartal1'}}}, operator=GT, rhs=NumExpression{codePosition=begin line/column 21/26; end line/column 21/26, value=3}, value=null}, thenElement=PairClouBausteinElementList{codePosition=begin line/column 23/4; end line/column 23/7, head=FixedTextImpl{codePosition=begin line/column 23/4; end line/column 23/7, textBuffer=Hier}, tail=PairClouBausteinElementList{codePosition=begin line/column 23/9; end line/column 23/11, head=FixedTextImpl{codePosition=begin line/column 23/9; end line/column 23/11, textBuffer=ist}, tail=PairClouBausteinElementList{codePosition=begin line/column 23/13; end line/column 23/17, head=FixedTextImpl{codePosition=begin line/column 23/13; end line/column 23/17, textBuffer=etwas}, tail=PairClouBausteinElementList{codePosition=begin line/column 23/19; end line/column 23/26, head=FixedTextImpl{codePosition=begin line/column 23/19; end line/column 23/26, textBuffer=verkehrt}, tail=LastClouBausteinElementList{head=FixedTextImpl{codePosition=begin line/column 23/28; end line/column 23/40, textBuffer=konfiguriert.}}}}}}, elseElement=null}, tail=PairClouBausteinElementList{codePosition=begin line/column 28/4; end line/column 28/11, head=GlobalListDeclarationStatement{codePosition=begin line/column 28/4; end line/column 28/11, listId='l vombis', listExpression=AbstractAstItem{codePosition=begin line/column 28/17; end line/column 28/23}}, tail=PairClouBausteinElementList{codePosition=begin line/column 29/4; end line/column 29/10, head=ConditionalStatement{codePosition=begin line/column 29/4; end line/column 29/10, condition=BinaryOperatorExpression{codePosition=begin line/column 29/4; end line/column 29/10, lhs=ClouFunctionCall{codePosition=begin line/column 29/4; end line/column 29/10, functionName='listlen', args=GlobalListDeclarationStatement{codePosition=begin line/column 29/12; end line/column 29/21head='TextExpression{codePosition=begin line/column 29/12; end line/column 29/21, value='l vombis'}}}, operator=NEQ, rhs=NumExpression{codePosition=begin line/column 29/27; end line/column 29/27, value=2}, value=null}, thenElement=PairClouBausteinElementList{codePosition=begin line/column 31/19; end line/column 31/25, head=AssignmentStatement{codePosition=begin line/column 31/19; end line/column 31/25, id='listschleif', expression=BinaryOperatorExpression{codePosition=begin line/column 31/19; end line/column 31/25, lhs=ClouFunctionCall{codePosition=begin line/column 31/19; end line/column 31/25, functionName='listlen', args=GlobalListDeclarationStatement{codePosition=begin line/column 31/27; end line/column 31/36head='TextExpression{codePosition=begin line/column 31/27; end line/column 31/36, value='l vombis'}}}, operator=DIV, rhs=IdExpression{id='list df anz', valueType=null, value=null}, value=null}}, tail=PairClouBausteinElementList{codePosition=begin line/column 32/4; end line/column 32/7, head=FixedTextImpl{codePosition=begin line/column 32/4; end line/column 32/7, textBuffer=Hier}, tail=PairClouBausteinElementList{codePosition=begin line/column 32/9; end line/column 32/11, head=FixedTextImpl{codePosition=begin line/column 32/9; end line/column 32/11, textBuffer=ist}, tail=PairClouBausteinElementList{codePosition=begin line/column 32/13; end line/column 32/17, head=FixedTextImpl{codePosition=begin line/column 32/13; end line/column 32/17, textBuffer=etwas}, tail=PairClouBausteinElementList{codePosition=begin line/column 32/19; end line/column 32/26, head=FixedTextImpl{codePosition=begin line/column 32/19; end line/column 32/26, textBuffer=verkehrt}, tail=LastClouBausteinElementList{head=FixedTextImpl{codePosition=begin line/column 32/28; end line/column 32/40, textBuffer=konfiguriert.}}}}}}}, elseElement=null}, tail=PairClouBausteinElementList{codePosition=begin line/column 35/1; end line/column 35/3, head=FixedTextImpl{codePosition=begin line/column 35/1; end line/column 35/3, textBuffer=And}, tail=PairClouBausteinElementList{codePosition=begin line/column 35/5; end line/column 35/6, head=FixedTextImpl{codePosition=begin line/column 35/5; end line/column 35/6, textBuffer=so}, tail=PairClouBausteinElementList{codePosition=begin line/column 35/8; end line/column 35/9, head=FixedTextImpl{codePosition=begin line/column 35/8; end line/column 35/9, textBuffer=it}, tail=LastClouBausteinElementList{head=FixedTextImpl{codePosition=begin line/column 35/11; end line/column 35/15, textBuffer=ends.}}}}}}}}}}}}}}}}}}}}}}}}}", baustein.toString());
    }

    @Test
    public void testHitCommandReturn() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("HitCommandReturn"), "UTF-8");
        ClouBaustein baustein = parser.CB();
        assertNotNull(baustein);
        String probe = baustein.toString();
        assertEquals("ClouBausteinImpl{codePosition=begin line/column 4/5; end line/column 4/16, clouBausteinElement=PairClouBausteinElementList{codePosition=begin line/column 4/5; end line/column 4/16, head=FixedTextImpl{codePosition=begin line/column 4/5; end line/column 4/16, textBuffer=Gleichzeitig}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/18; end line/column 4/25, head=FixedTextImpl{codePosition=begin line/column 4/18; end line/column 4/25, textBuffer=ersuchen}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/27; end line/column 4/29, head=FixedTextImpl{codePosition=begin line/column 4/27; end line/column 4/29, textBuffer=wir}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/31; end line/column 4/33, head=FixedTextImpl{codePosition=begin line/column 4/31; end line/column 4/33, textBuffer=den}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/35; end line/column 4/39, head=FixedTextImpl{codePosition=begin line/column 4/35; end line/column 4/39, textBuffer=(das)}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/41; end line/column 4/54, head=FixedTextImpl{codePosition=begin line/column 4/41; end line/column 4/54, textBuffer=beiliegende(n)}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/56; end line/column 4/63, head=FixedTextImpl{codePosition=begin line/column 4/56; end line/column 4/63, textBuffer=Bescheid}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/65; end line/column 4/70, head=FixedTextImpl{codePosition=begin line/column 4/65; end line/column 4/70, textBuffer=(INFO)}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/5; end line/column 5/6, head=HitCommandStatement{codePosition=begin line/column 5/5; end line/column 5/6, hitCommand=RETURN, repetitor=NumExpression{codePosition=begin line/column 5/7; end line/column 5/7, value=2}, verstarkt=false}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/5; end line/column 6/14, head=FixedTextImpl{codePosition=begin line/column 6/5; end line/column 6/14, textBuffer=anlässlich}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/16; end line/column 6/18, head=FixedTextImpl{codePosition=begin line/column 6/16; end line/column 6/18, textBuffer=der}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/20; end line/column 6/30, head=FixedTextImpl{codePosition=begin line/column 6/20; end line/column 6/30, textBuffer=Information}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/32; end line/column 6/33, head=FixedTextImpl{codePosition=begin line/column 6/32; end line/column 6/33, textBuffer=zu}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/35; end line/column 6/44, head=FixedTextImpl{codePosition=begin line/column 6/35; end line/column 6/44, textBuffer=expedieren}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/46; end line/column 6/49, head=FixedTextImpl{codePosition=begin line/column 6/46; end line/column 6/49, textBuffer=bzw.}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/51; end line/column 6/63, head=FixedTextImpl{codePosition=begin line/column 6/51; end line/column 6/63, textBuffer=auszuhändigen}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/65; end line/column 6/67, head=FixedTextImpl{codePosition=begin line/column 6/65; end line/column 6/67, textBuffer=und}, tail=PairClouBausteinElementList{codePosition=begin line/column 7/5; end line/column 7/6, head=HitCommandStatement{codePosition=begin line/column 7/5; end line/column 7/6, hitCommand=RETURN, repetitor=null, verstarkt=false}, tail=PairClouBausteinElementList{codePosition=begin line/column 8/5; end line/column 8/7, head=FixedTextImpl{codePosition=begin line/column 8/5; end line/column 8/7, textBuffer=wie}, tail=PairClouBausteinElementList{codePosition=begin line/column 8/9; end line/column 8/13, head=FixedTextImpl{codePosition=begin line/column 8/9; end line/column 8/13, textBuffer=folgt}, tail=PairClouBausteinElementList{codePosition=begin line/column 8/15; end line/column 8/23, head=FixedTextImpl{codePosition=begin line/column 8/15; end line/column 8/23, textBuffer=storniert}, tail=PairClouBausteinElementList{codePosition=begin line/column 8/25; end line/column 8/30, head=FixedTextImpl{codePosition=begin line/column 8/25; end line/column 8/30, textBuffer=haben:}, tail=LastClouBausteinElementList{head=HitCommandStatement{codePosition=begin line/column 9/5; end line/column 9/6, hitCommand=RETURN, repetitor=NumExpression{codePosition=begin line/column 9/8; end line/column 9/8, value=5}, verstarkt=false}}}}}}}}}}}}}}}}}}}}}}}}}", probe);
    }


    @Test
    public void testForLoop() throws Exception {
        // ISO8859_1 is how javacc wants to be told the encoding ... if I'm not mistaken.
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("For"), "ISO8859_1");
        ClouBaustein clouBaustein = parser.CB();
        assertNotNull(clouBaustein);
        String probe = clouBaustein.toString();
        assertEquals("ClouBausteinImpl{codePosition=begin line/column 4/1; end line/column 4/6, clouBausteinElement=PairClouBausteinElementList{codePosition=begin line/column 4/1; end line/column 4/6, head=FixedTextImpl{codePosition=begin line/column 4/1; end line/column 4/6, textBuffer=Please}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/8; end line/column 4/11, head=FixedTextImpl{codePosition=begin line/column 4/8; end line/column 4/11, textBuffer=know}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/13; end line/column 4/16, head=FixedTextImpl{codePosition=begin line/column 4/13; end line/column 4/16, textBuffer=that}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/18; end line/column 4/19, head=FixedTextImpl{codePosition=begin line/column 4/18; end line/column 4/19, textBuffer=we}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/21; end line/column 4/25, head=FixedTextImpl{codePosition=begin line/column 4/21; end line/column 4/25, textBuffer=won't}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/27; end line/column 4/33, head=FixedTextImpl{codePosition=begin line/column 4/27; end line/column 4/33, textBuffer=process}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/35; end line/column 4/38, head=FixedTextImpl{codePosition=begin line/column 4/35; end line/column 4/38, textBuffer=your}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/40; end line/column 4/45, head=FixedTextImpl{codePosition=begin line/column 4/40; end line/column 4/45, textBuffer=order.}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/1; end line/column 5/6, head=FixedTextImpl{codePosition=begin line/column 5/1; end line/column 5/6, textBuffer=Thanks}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/8; end line/column 5/10, head=FixedTextImpl{codePosition=begin line/column 5/8; end line/column 5/10, textBuffer=for}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/12; end line/column 5/15, head=FixedTextImpl{codePosition=begin line/column 5/12; end line/column 5/15, textBuffer=your}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/17; end line/column 5/24, head=FixedTextImpl{codePosition=begin line/column 5/17; end line/column 5/24, textBuffer=business}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/26; end line/column 5/28, head=FixedTextImpl{codePosition=begin line/column 5/26; end line/column 5/28, textBuffer=and}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/30; end line/column 5/33, head=FixedTextImpl{codePosition=begin line/column 5/30; end line/column 5/33, textBuffer=have}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/35; end line/column 5/35, head=FixedTextImpl{codePosition=begin line/column 5/35; end line/column 5/35, textBuffer=a}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/37; end line/column 5/40, head=FixedTextImpl{codePosition=begin line/column 5/37; end line/column 5/40, textBuffer=nice}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/42; end line/column 5/45, head=FixedTextImpl{codePosition=begin line/column 5/42; end line/column 5/45, textBuffer=day.}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/1; end line/column 6/7, head=FixedTextImpl{codePosition=begin line/column 6/1; end line/column 6/7, textBuffer=äöüÄÖÜß}, tail=PairClouBausteinElementList{codePosition=begin line/column 8/16; end line/column 8/16, head=GlobalDeclarationStatement{codePosition=begin line/column 8/16; end line/column 8/16, expression=NumExpression{codePosition=begin line/column 8/16; end line/column 8/16, value=2}, id='loopCounter', formatDefinition=''}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/17; end line/column 9/17, head=GlobalDeclarationStatement{codePosition=begin line/column 9/17; end line/column 9/17, expression=NumExpression{codePosition=begin line/column 9/17; end line/column 9/17, value=3}, id='löäüpCounter', formatDefinition=''}, tail=PairClouBausteinElementList{codePosition=begin line/column 11/4; end line/column 11/14, head=ForStatement{repetitionCount=IdExpression{id='loopCounter', valueType=null, value=null}, forBody=LastClouBausteinElementList{head=FixedTextImpl{codePosition=begin line/column 12/5; end line/column 12/11, textBuffer=Guards!}}}, tail=PairClouBausteinElementList{codePosition=begin line/column 15/4; end line/column 15/15, head=ForStatement{repetitionCount=IdExpression{id='löäüpCounter', valueType=null, value=null}, forBody=LastClouBausteinElementList{head=FixedTextImpl{codePosition=begin line/column 16/5; end line/column 16/9, textBuffer=Help!}}}, tail=PairClouBausteinElementList{codePosition=begin line/column 19/1; end line/column 19/3, head=FixedTextImpl{codePosition=begin line/column 19/1; end line/column 19/3, textBuffer=And}, tail=PairClouBausteinElementList{codePosition=begin line/column 19/5; end line/column 19/6, head=FixedTextImpl{codePosition=begin line/column 19/5; end line/column 19/6, textBuffer=so}, tail=PairClouBausteinElementList{codePosition=begin line/column 19/8; end line/column 19/9, head=FixedTextImpl{codePosition=begin line/column 19/8; end line/column 19/9, textBuffer=it}, tail=LastClouBausteinElementList{head=FixedTextImpl{codePosition=begin line/column 19/11; end line/column 19/15, textBuffer=ends.}}}}}}}}}}}}}}}}}}}}}}}}}}}}", probe);
    }

    @Test
    public void testHitCommandVarious() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("HitCommandVarious"), "UTF-8");
        ClouBaustein baustein = parser.CB();
        assertNotNull(baustein);
        String probe = baustein.toString();
        assertEquals("ClouBausteinImpl{codePosition=begin line/column 4/5; end line/column 4/16, clouBausteinElement=PairClouBausteinElementList{codePosition=begin line/column 4/5; end line/column 4/16, head=FixedTextImpl{codePosition=begin line/column 4/5; end line/column 4/16, textBuffer=Gleichzeitig}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/18; end line/column 4/25, head=FixedTextImpl{codePosition=begin line/column 4/18; end line/column 4/25, textBuffer=ersuchen}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/27; end line/column 4/29, head=FixedTextImpl{codePosition=begin line/column 4/27; end line/column 4/29, textBuffer=wir}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/31; end line/column 4/33, head=FixedTextImpl{codePosition=begin line/column 4/31; end line/column 4/33, textBuffer=den}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/35; end line/column 4/39, head=FixedTextImpl{codePosition=begin line/column 4/35; end line/column 4/39, textBuffer=(das)}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/41; end line/column 4/42, head=HitCommandStatement{codePosition=begin line/column 4/41; end line/column 4/42, hitCommand=RETURN, repetitor=NumExpression{codePosition=begin line/column 4/43; end line/column 4/43, value=2}, verstarkt=false}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/53; end line/column 4/66, head=FixedTextImpl{codePosition=begin line/column 4/53; end line/column 4/66, textBuffer=beiliegende(n)}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/68; end line/column 4/75, head=FixedTextImpl{codePosition=begin line/column 4/68; end line/column 4/75, textBuffer=Bescheid}, tail=PairClouBausteinElementList{codePosition=begin line/column 4/77; end line/column 4/82, head=FixedTextImpl{codePosition=begin line/column 4/77; end line/column 4/82, textBuffer=(INFO)}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/5; end line/column 5/14, head=FixedTextImpl{codePosition=begin line/column 5/5; end line/column 5/14, textBuffer=anlässlich}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/16; end line/column 5/18, head=FixedTextImpl{codePosition=begin line/column 5/16; end line/column 5/18, textBuffer=der}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/20; end line/column 5/30, head=FixedTextImpl{codePosition=begin line/column 5/20; end line/column 5/30, textBuffer=Information}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/32; end line/column 5/33, head=FixedTextImpl{codePosition=begin line/column 5/32; end line/column 5/33, textBuffer=zu}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/35; end line/column 5/44, head=FixedTextImpl{codePosition=begin line/column 5/35; end line/column 5/44, textBuffer=expedieren}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/46; end line/column 5/49, head=FixedTextImpl{codePosition=begin line/column 5/46; end line/column 5/49, textBuffer=bzw.}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/51; end line/column 5/63, head=FixedTextImpl{codePosition=begin line/column 5/51; end line/column 5/63, textBuffer=auszuhändigen}, tail=PairClouBausteinElementList{codePosition=begin line/column 5/65; end line/column 5/67, head=FixedTextImpl{codePosition=begin line/column 5/65; end line/column 5/67, textBuffer=und}, tail=PairClouBausteinElementList{codePosition=begin line/column 6/5; end line/column 6/6, head=HitCommandStatement{codePosition=begin line/column 6/5; end line/column 6/6, hitCommand=C_LINKS, repetitor=null, verstarkt=false}, tail=PairClouBausteinElementList{codePosition=begin line/column 7/5; end line/column 7/7, head=FixedTextImpl{codePosition=begin line/column 7/5; end line/column 7/7, textBuffer=wie}, tail=PairClouBausteinElementList{codePosition=begin line/column 7/9; end line/column 7/13, head=FixedTextImpl{codePosition=begin line/column 7/9; end line/column 7/13, textBuffer=folgt}, tail=PairClouBausteinElementList{codePosition=begin line/column 7/15; end line/column 7/23, head=FixedTextImpl{codePosition=begin line/column 7/15; end line/column 7/23, textBuffer=storniert}, tail=PairClouBausteinElementList{codePosition=begin line/column 7/25; end line/column 7/30, head=FixedTextImpl{codePosition=begin line/column 7/25; end line/column 7/30, textBuffer=haben:}, tail=PairClouBausteinElementList{codePosition=begin line/column 8/5; end line/column 8/6, head=HitCommandStatement{codePosition=begin line/column 8/5; end line/column 8/6, hitCommand=ABS_FORMATIEREN, repetitor=null, verstarkt=false}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/5; end line/column 9/8, head=FixedTextImpl{codePosition=begin line/column 9/5; end line/column 9/8, textBuffer=What}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/10; end line/column 9/11, head=FixedTextImpl{codePosition=begin line/column 9/10; end line/column 9/11, textBuffer=is}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/13; end line/column 9/14, head=FixedTextImpl{codePosition=begin line/column 9/13; end line/column 9/14, textBuffer=it}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/16; end line/column 9/21, head=FixedTextImpl{codePosition=begin line/column 9/16; end line/column 9/21, textBuffer=that's}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/23; end line/column 9/26, head=FixedTextImpl{codePosition=begin line/column 9/23; end line/column 9/26, textBuffer=ever}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/28; end line/column 9/31, head=FixedTextImpl{codePosition=begin line/column 9/28; end line/column 9/31, textBuffer=been}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/33; end line/column 9/39, head=FixedTextImpl{codePosition=begin line/column 9/33; end line/column 9/39, textBuffer=hunting}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/41; end line/column 9/44, head=FixedTextImpl{codePosition=begin line/column 9/41; end line/column 9/44, textBuffer=them}, tail=PairClouBausteinElementList{codePosition=begin line/column 9/46; end line/column 9/52, head=FixedTextImpl{codePosition=begin line/column 9/46; end line/column 9/52, textBuffer=around?}, tail=PairClouBausteinElementList{codePosition=begin line/column 10/5; end line/column 10/6, head=HitCommandStatement{codePosition=begin line/column 10/5; end line/column 10/6, hitCommand=VERSTAERKER, repetitor=null, verstarkt=false}, tail=PairClouBausteinElementList{codePosition=begin line/column 10/20; end line/column 10/21, head=HitCommandStatement{codePosition=begin line/column 10/20; end line/column 10/21, hitCommand=RETURN, repetitor=null, verstarkt=false}, tail=PairClouBausteinElementList{codePosition=begin line/column 11/5; end line/column 11/8, head=FixedTextImpl{codePosition=begin line/column 11/5; end line/column 11/8, textBuffer=blah}, tail=PairClouBausteinElementList{codePosition=begin line/column 11/10; end line/column 11/14, head=FixedTextImpl{codePosition=begin line/column 11/10; end line/column 11/14, textBuffer=Udine}, tail=PairClouBausteinElementList{codePosition=begin line/column 12/5; end line/column 12/6, head=HitCommandStatement{codePosition=begin line/column 12/5; end line/column 12/6, hitCommand=RETURN, repetitor=null, verstarkt=true}, tail=PairClouBausteinElementList{codePosition=begin line/column 13/5; end line/column 13/16, head=FixedTextImpl{codePosition=begin line/column 13/5; end line/column 13/16, textBuffer=Gleichzeitig}, tail=PairClouBausteinElementList{codePosition=begin line/column 13/18; end line/column 13/25, head=FixedTextImpl{codePosition=begin line/column 13/18; end line/column 13/25, textBuffer=ersuchen}, tail=PairClouBausteinElementList{codePosition=begin line/column 13/27; end line/column 13/29, head=FixedTextImpl{codePosition=begin line/column 13/27; end line/column 13/29, textBuffer=wir}, tail=PairClouBausteinElementList{codePosition=begin line/column 13/31; end line/column 13/33, head=FixedTextImpl{codePosition=begin line/column 13/31; end line/column 13/33, textBuffer=den}, tail=PairClouBausteinElementList{codePosition=begin line/column 13/35; end line/column 13/39, head=FixedTextImpl{codePosition=begin line/column 13/35; end line/column 13/39, textBuffer=(das)}, tail=PairClouBausteinElementList{codePosition=begin line/column 13/41; end line/column 13/42, head=HitCommandStatement{codePosition=begin line/column 13/41; end line/column 13/42, hitCommand=RETURN, repetitor=NumExpression{codePosition=begin line/column 13/43; end line/column 13/43, value=2}, verstarkt=false}, tail=PairClouBausteinElementList{codePosition=begin line/column 13/53; end line/column 13/66, head=FixedTextImpl{codePosition=begin line/column 13/53; end line/column 13/66, textBuffer=beiliegende(n)}, tail=PairClouBausteinElementList{codePosition=begin line/column 13/68; end line/column 13/75, head=FixedTextImpl{codePosition=begin line/column 13/68; end line/column 13/75, textBuffer=Bescheid}, tail=PairClouBausteinElementList{codePosition=begin line/column 13/77; end line/column 13/82, head=FixedTextImpl{codePosition=begin line/column 13/77; end line/column 13/82, textBuffer=(INFO)}, tail=PairClouBausteinElementList{codePosition=begin line/column 14/5; end line/column 14/6, head=HitCommandStatement{codePosition=begin line/column 14/5; end line/column 14/6, hitCommand=RETURN, repetitor=NumExpression{codePosition=begin line/column 14/7; end line/column 14/7, value=2}, verstarkt=true}, tail=LastClouBausteinElementList{head=FixedTextImpl{codePosition=begin line/column 15/5; end line/column 15/8, textBuffer=jaha}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}", probe);
    }

    @Test
    public void schweineTest() throws Exception {
        String foo = "asdf-\"Hallo\"-fdsa";
        String bar = foo.replaceAll("\"", "\\\\\"");
        assertNotNull(foo);
        assertTrue("hello:bello".split(":").length == 2);
        assertTrue("bello".split(":").length == 1);
    }

}

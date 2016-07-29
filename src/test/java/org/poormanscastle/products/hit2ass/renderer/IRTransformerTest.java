package org.poormanscastle.products.hit2ass.renderer;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.poormanscastle.products.hit2ass.TestUtils;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.renderer.domain.Workspace;
import org.poormanscastle.products.hit2ass.transformer.FixedTextMerger;
import org.poormanscastle.products.hit2ass.transformer.InsertBlanksVisitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public class IRTransformerTest {

    private ClouBaustein baustein;
    private HitAssAstParser parser;
    private FixedTextMerger merger = new FixedTextMerger();
    private InsertBlanksVisitor blanksVisitor = new InsertBlanksVisitor();
    private IRTransformer irTransformer = new IRTransformer();

    @Test
    public void testHitCommandReturn() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("HitCommandReturn"), "UTF-8");
        baustein = parser.CB();
        baustein.accept(merger);
        baustein.accept(blanksVisitor);
        baustein.accept(irTransformer);
        Workspace workspace = irTransformer.getWorkspace();
        assertNotNull(workspace);
        assertEquals("Workspace{workspaceName='HitAssWorkspace', projectsName='HitAssProjects', projectName='HitAssProject', documentName='HitAssDocument', repeatingPageName='HitAssRepeatingPage', pageContentName='HitAssPageContent', contentContainer=Paragraph{contentElements=[Text{name='text', text='Gleichzeitig ersuchen wir den (das) beiliegende(n) Bescheid (INFO)'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='2'}, Text{name='text', text='anlässlich der Information zu expedieren bzw. auszuhändigen und'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='wie folgt storniert haben:'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='5'}]}}", workspace.toString());
        String acr = workspace.getContent();
        assertFalse(StringUtils.isBlank(acr));
    }

    @Test
    public void testIfElse() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("SimpleIfLetter"), "UTF-8");
        baustein = parser.CB();
        baustein.accept(merger);
        baustein.accept(blanksVisitor);
        baustein.accept(irTransformer);
        Workspace workspace = irTransformer.getWorkspace();
        assertNotNull(workspace);
        String acrString = workspace.getContent();
        assertFalse(StringUtils.isBlank(acrString));
    }

    @Test
    public void testSection() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("SectionBreak"), "ISO8859_1");
        baustein = parser.CB();
        baustein.accept(merger);
        baustein.accept(blanksVisitor);
        baustein.accept(irTransformer);
        Workspace workspace = irTransformer.getWorkspace();
        assertNotNull(workspace);
        String acrString = workspace.getContent();
        assertFalse(StringUtils.isBlank(acrString));
        assertEquals("Workspace{workspaceName='HitAssWorkspace', projectsName='HitAssProjects', projectName='HitAssProject', documentName='HitAssDocument', repeatingPageName='HitAssRepeatingPage', pageContentName='HitAssPageContent', contentContainer=Paragraph{contentElements=[DocumentVariable{variableName=''firstName'', variableValue=' 'Jim' '}, DocumentVariable{variableName=''lastName'', variableValue=' 'Raynor' '}, DocumentVariable{variableName=''gender'', variableValue=' 'm' '}, DocumentVariable{variableName=''shoppingItem'', variableValue=' 'Adler' '}, DocumentVariable{variableName=''numberOfNewLines'', variableValue='5'}, Text{name='text', text='Sehr geehrter Herr '}, DynamicContentReference{name='Print: firstName', xpath=' var:read('firstName') '}, Text{name='text', text=' '}, DynamicContentReference{name='Print: lastName', xpath=' var:read('lastName') '}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='Wir danken Ihnen für Ihre Bestellung und bitten Sie um Ihre Zurkenntnisnahme:'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='Ihre Bestellung wird nicht ausgeführt.'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='3'}, Text{name='text', text='Aufwiederhören.'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='And so it ends.'}, Text{name='text', text=''}]}}", workspace.toString());
    }

    @Test
    public void testAssignments() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("Assignments"), "ISO8859_1");
        baustein = parser.CB();
        baustein.accept(merger);
        baustein.accept(blanksVisitor);
        baustein.accept(irTransformer);
        Workspace workspace = irTransformer.getWorkspace();
        assertNotNull(workspace);
        String probe = workspace.toString();
        assertEquals("Workspace{workspaceName='HitAssWorkspace', projectsName='HitAssProjects', projectName='HitAssProject', documentName='HitAssDocument', repeatingPageName='HitAssRepeatingPage', pageContentName='HitAssPageContent', contentContainer=Paragraph{contentElements=[Text{name='text', text='Here is some fixed text for you.'}, DocumentVariable{variableName=''myVar'', variableValue=' 'John2' '}, DynamicContentReference{name='Assign from Userdata XML: quargl', xpath='var:write('quargl', /UserData/payload/line[@lineNr = hit2assext:getXmlSequence(var:read('renderSessionUuid'))]) | hit2assext:incrementXmlSequence(var:read('renderSessionUuid'))'}, DynamicContentReference{name='Scalar Assignment: quargl', xpath='var:write('quargl',  'Speed' )'}, DynamicContentReference{name='Scalar Assignment: varName', xpath='var:write('varName',  'For' )'}, DynamicContentReference{name='Scalar Assignment: anotherVisitor', xpath='var:write('anotherVisitor',  'Need' )'}, Text{name='text', text='This is the value of anotherVisitor: '}, DynamicContentReference{name='Print: anotherVisitor', xpath=' var:read('anotherVisitor') '}, Text{name='text', text=','}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='this is the value of varName: '}, DynamicContentReference{name='Print: varName', xpath=' var:read('varName') '}, Text{name='text', text=','}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='and this is the vlaue of quargl: '}, DynamicContentReference{name='Print: quargl', xpath=' var:read('quargl') '}, Text{name='text', text='.'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='Now, intitializing a list, setting slot values and retrieving slot values:'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, ListDeclaration{listVariableName='someNames', components='[]'}, ListAddItem{listVariableName='someNames', newValue=' 'John' ', components='[]'}, ListAddItem{listVariableName='someNames', newValue=' 'Joanne' ', components='[]'}, ListAddItem{listVariableName='someNames', newValue=' 'Eli' ', components='[]'}, Text{name='text', text='someNames[1]= '}, DynamicContentReference{name='Print: someNames', xpath=' hit2assext:getListValueAt(var:read('renderSessionUuid'), 'someNames', 1)'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='someNames[2]= '}, DynamicContentReference{name='Print: someNames', xpath=' hit2assext:getListValueAt(var:read('renderSessionUuid'), 'someNames', 2)'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='someNames[3|= '}, DynamicContentReference{name='Print: someNames', xpath=' hit2assext:getListValueAt(var:read('renderSessionUuid'), 'someNames', 3)'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='Now setting someNames[2] to Lisa'}, DynamicContentReference{name='List Assignment: someNames', xpath='hit2assext:setListValueAt(var:read('renderSessionUuid'), 'someNames', 2,  'Lisa' )'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, Text{name='text', text='someNames[2]= '}, DynamicContentReference{name='Print: someNames', xpath=' hit2assext:getListValueAt(var:read('renderSessionUuid'), 'someNames', 2)'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}]}}", probe);
    }

    @Test
    public void testSubstring() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("Substring"), "ISO8859_1");
        baustein = parser.CB();
        baustein.accept(merger);
        baustein.accept(blanksVisitor);
        baustein.accept(irTransformer);
        Workspace workspace = irTransformer.getWorkspace();
        assertNotNull(workspace);
        String probe = workspace.toString();
        assertFalse(StringUtils.isBlank(probe));
        assertEquals("Workspace{workspaceName='HitAssWorkspace', projectsName='HitAssProjects', projectName='HitAssProject', documentName='HitAssDocument', repeatingPageName='HitAssRepeatingPage', pageContentName='HitAssPageContent', contentContainer=Paragraph{contentElements=[DocumentVariable{variableName=''myString'', variableValue=' 'Let freedom ring' '}, DocumentVariable{variableName=''mySubstring'', variableValue=' substring(var:read('myString'), 5, 11 + 1 - 5 ) '}, Text{name='text', text='The substring [5,11] of \"'}, DynamicContentReference{name='Print: myString', xpath=' var:read('myString') '}, Text{name='text', text='\" is \"'}, DynamicContentReference{name='Print: mySubstring', xpath=' var:read('mySubstring') '}, Text{name='text', text='\".'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}, IfThenElseParagraph{condition=BinaryOperatorExpression{codePosition=begin line/column 9/4; end line/column 9/14, lhs=IdExpression{id='mySubstring', idxExp1=null, idxExp2=null, valueType=null, value=null}, operator=EQ, rhs=TextExpression{codePosition=begin line/column 9/18; end line/column 9/26, value='freedom'}, value=null}, components=[IfThenParagraph{components=[Text{name='text', text='Hey, that worked!'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}]}, IfElseParagraph{components=[Text{name='text', text='OK, we will have to fix that.'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='1'}]}]}, Text{name='text', text='And so it ends.'}, Text{name='text', text=''}]}}", probe);
    }

    @Test
    public void testInsertBlanksVisitor() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("InsertBlanksVisitorTest"), "ISO8859_1");
        baustein = parser.CB();
        baustein.accept(merger);
        baustein.accept(blanksVisitor);
        baustein.accept(irTransformer);
        Workspace workspace = irTransformer.getWorkspace();
        assertNotNull(workspace);
        String probe = workspace.toString();
        assertFalse(StringUtils.isBlank(probe));
        assertEquals("Workspace{workspaceName='HitAssWorkspace', projectsName='HitAssProjects', projectName='HitAssProject', documentName='HitAssDocument', repeatingPageName='HitAssRepeatingPage', pageContentName='HitAssPageContent', contentContainer=Paragraph{contentElements=[DocumentVariable{variableName=''firstName'', variableValue=' 'John' '}, DocumentVariable{variableName=''lastName'', variableValue=' 'Connor' '}, Text{name='text', text='You are '}, DynamicContentReference{name='Print: firstName', xpath=' var:read('firstName') '}, Text{name='text', text=' '}, DynamicContentReference{name='Print: lastName', xpath=' var:read('lastName') '}, Text{name='text', text='.'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='2'}, Text{name='text', text='You are \"'}, DynamicContentReference{name='Print: firstName', xpath=' var:read('firstName') '}, Text{name='text', text='\".'}, CarriageReturn{name='NL', repetitionExpression.toXPathString()='2'}, Text{name='text', text='And so it ends.'}, Text{name='text', text=''}]}}", probe);
    }

    @Test
    public void testSimpleIfLetter() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("/sampleDocuments/SimpleIfLetter/", "SimpleIfLetter", "clou"), "ISO8859-1");
        baustein = parser.CB();
        baustein.accept(merger);
        baustein.accept(blanksVisitor);
        baustein.accept(irTransformer);
        Workspace workspace = irTransformer.getWorkspace();
        assertNotNull(workspace);
        String acr = workspace.getContent();
        assertFalse(StringUtils.isBlank(acr));
        // check that InsertBlanksVisitor does not insert blanks before punctuation characters.
        assertTrue(acr.contains("<![CDATA[.]]"));
        // check that InsertBlanksVisitor inserts blanks between FixedText and PrintStatements
        assertTrue(acr.contains("den Versand von einem ]]"));
        // check that InsertBlanksVisitor inserts blanks between PrintStatements
        assertTrue(acr.contains("<![CDATA[ ]]"));
    }

}

package org.poormanscastle.products.hit2ass.renderer;

import org.poormanscastle.products.hit2ass.TestUtils;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.renderer.domain.Workspace;
import org.poormanscastle.products.hit2ass.transformer.FixedTextMerger;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public class IRTransformerTest {

    private HitAssAstParser parser;
    private FixedTextMerger merger = new FixedTextMerger();
    private ClouBaustein baustein;
    private IRTransformer irTransformer = new IRTransformer();

    @Test
    public void testHitCommandReturn() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("HitCommandReturn"), "UTF-8");
        baustein = parser.CB();
        baustein.accept(merger);
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
        baustein.accept(irTransformer);
        Workspace workspace = irTransformer.getWorkspace();
        assertNotNull(workspace);
        String acrString = workspace.getContent();
        assertFalse(StringUtils.isBlank(acrString));
    }

}

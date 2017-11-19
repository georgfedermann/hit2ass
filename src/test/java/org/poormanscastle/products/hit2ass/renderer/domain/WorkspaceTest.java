package org.poormanscastle.products.hit2ass.renderer.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.poormanscastle.products.hit2ass.TestUtils;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.renderer.IRTransformer;
import org.poormanscastle.products.hit2ass.transformer.EraseBlanksVisitor;

/**
 * Created by georg on 09.10.16.
 */
public class WorkspaceTest {
    
    @Test
    public void getPageContentTest() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("EmptyCase"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        IRTransformer transformer = new IRTransformer();
        baustein.accept(transformer);
        Workspace workspace = transformer.getWorkspace();
        String pageContent = workspace.getPageContentForDeployedModules();
        assertFalse(StringUtils.isBlank(pageContent));
        assertTrue(pageContent.contains("Live and let live"));
    }

}
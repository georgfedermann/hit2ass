package org.poormanscastle.products.hit2ass.transformer;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.poormanscastle.products.hit2ass.TestUtils;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.renderer.DeployedModuleLibrary;
import org.poormanscastle.products.hit2ass.renderer.IRTransformer;

/**
 * Created by georg on 09.10.16.
 */
public class ClouBausteinDependencyResolverVisitorTest {

    @Test
    public void visitIncludeBausteinStatementTest() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(TestUtils.getClouChunkAsInputStream("BausteinResolverTest"), "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        baustein.accept(new ClouBausteinDependencyResolverVisitor());
        IRTransformer transformer = new IRTransformer();
        baustein.accept(transformer);

        DeployedModuleLibrary dmLib = DeployedModuleLibrary.loadHitAssDeployedModuleLibrary();
        assertNotNull(dmLib);
        DeployedModuleLibrary.storeHitAssDeployedModuleLibrary();
    }

}
package org.poormanscastle.products.hit2ass.parser;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBausteinElementList;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.renderer.IRTransformer;
import org.poormanscastle.products.hit2ass.transformer.EraseBlanksVisitor;

/**
 * Created by georg on 28.09.16.
 */
public class BausteinTest {

    // @Test
    public void bal001() throws Exception {
        HitAssAstParser parser = new HitAssAstParser(
                Files.newInputStream(Paths.get("/Users/georg/vms/UbuntuWork/shared/hitass/reverseEngineering/hit2assentis_reworked/Baustein"))
                , "ISO8859_1");
        ClouBaustein baustein = parser.CB();
        baustein.accept(new EraseBlanksVisitor());
        ClouBausteinElementList elementList = ((ClouBausteinElementList) baustein.getClouBausteinElement());
        IRTransformer transformer = new IRTransformer();
        baustein.accept(transformer);
    }

}

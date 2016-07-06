package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import org.poormanscastle.products.hit2ass.TestUtils;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.transformer.ClouBausteinMergerVisitor;
import org.poormanscastle.products.hit2ass.transformer.FixedTextMerger;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by georg.federmann@poormanscastle.com on 5/10/16.
 */
public class XmlCreatorTest {

    private HitAssAstParser parser;

    private ClouBaustein baustein;

    private FixedTextMerger fixedTextMerger = new FixedTextMerger();
    private ClouBausteinMergerVisitor bausteinMerger = new ClouBausteinMergerVisitor();
    private XmlCreator xmlCreator = new XmlCreator();

    @Before
    public void init() {
        xmlCreator = new XmlCreator();
    }

    @Test
    public void testSerializeWithSimpleBaustein() throws Exception {
        parser = new HitAssAstParser(TestUtils.getClouBausteinAsInputStream("DynamicValue"), "UTF-8");
        baustein = parser.CB();
        // the referenced #B bausteine in this simple sample baustein are not meant to be processed.
        // baustein.accept(bausteinMerger);
        baustein.accept(fixedTextMerger);
        baustein.accept(xmlCreator);
        String xmlString = xmlCreator.serialize();
        assertFalse(StringUtils.isBlank(xmlString));
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xml>\n" +
                "  <firstName>${param0}</firstName>\n" +
                "  <lastName>${param1}</lastName>\n" +
                "  <customerNr>${param2}</customerNr>\n" +
                "  <orderNr>${param3}</orderNr>\n" +
                "</xml>\n", xmlString);
    }

}
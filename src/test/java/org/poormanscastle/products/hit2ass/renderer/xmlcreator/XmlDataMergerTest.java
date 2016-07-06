package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Before;
import org.junit.Test;
import org.poormanscastle.products.hit2ass.renderer.xmlcreator.XmlDataMerger;

import java.io.InputStream;

import static org.junit.Assert.assertFalse;

/**
 * Created by georg.federmann@poormanscastle.com on 5/11/16.
 */
public class XmlDataMergerTest {

    XmlDataMerger xmlDataMerger;

    @Before
    public void init() {
        xmlDataMerger = new XmlDataMerger();
    }

    @Test
    public void merge() throws Exception {
        ClasspathResourceLoader loader = new ClasspathResourceLoader();
        InputStream xmlTemplateIs = loader.getResourceStream("/clou/MergeXmlTemplate.vlt");
        InputStream inputDataIs = loader.getResourceStream("/clou/MergeInput.txt");
        String result = xmlDataMerger.merge(xmlTemplateIs, inputDataIs);
        assertFalse(StringUtils.isBlank(result));
    }

}
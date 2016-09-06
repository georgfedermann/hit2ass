package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by georg on 7/8/16.
 */
public class UserDataServiceBeanTest {

    UserDataServiceBean userDataService = new UserDataServiceBean();

    @Test
    public void getUserdataXml() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream())));
        assertFalse(StringUtils.isBlank(result));
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Briefdaten><MD/><payload><line lineNr=\"1\">16-47951-8</line><line lineNr=\"2\">John</line><line lineNr=\"3\">Connor</line><line lineNr=\"4\">m</line><line lineNr=\"5\">premium</line><line lineNr=\"6\">Terminator</line><line lineNr=\"7\">10</line><line lineNr=\"8\">2016-05-21</line></payload></Briefdaten>", result);
    }

}
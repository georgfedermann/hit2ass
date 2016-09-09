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
    public void getUserdataXmlTelMitarb() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "UE108"));
        assertFalse(StringUtils.isBlank(result));
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Briefdaten>\n" +
                "    <MD>\n" +
                "        <Aktenzeichen>\n" +
                "            <Vstr>02</Vstr>\n" +
                "        </Aktenzeichen>\n" +
                "        <Schreiben>\n" +
                "            <Kopf Klappe=\"23823\" LdstKz=\"H\"/>\n" +
                "            <Abtkz>HVBA</Abtkz>\n" +
                "        </Schreiben>\n" +
                "    </MD>\n" +
                "    <Spruch>\n" +
                "        <Spruch413>\n" +
                "            <KenLdst>H</KenLdst>\n" +
                "        </Spruch413>\n" +
                "    </Spruch>\n" +
                "    <payload><line lineNr=\"1\">1078300358</line><line lineNr=\"2\">Frau</line><line lineNr=\"3\">--</line><line lineNr=\"4\">Hilda (B.UE108)</line><line lineNr=\"5\">Mustermann</line><line lineNr=\"6\">--</line><line lineNr=\"7\">--</line><line lineNr=\"8\">Musterweg 22/11</line><line lineNr=\"9\">--</line><line lineNr=\"10\">--</line><line lineNr=\"11\">1080</line><line lineNr=\"12\">Musterdorf</line><line lineNr=\"13\">--</line><line lineNr=\"14\">Sehr geehrter Frau Mustermann!</line><line lineNr=\"15\">A</line><line lineNr=\"16\">M</line><line lineNr=\"17\">2382327770238612387023800</line><line lineNr=\"18\">1</line><line lineNr=\"19\">11</line><line lineNr=\"20\">20160627</line><line lineNr=\"21\">17.05.2016</line><line lineNr=\"22\">411</line><line lineNr=\"23\">Hilda Mustermann</line><line lineNr=\"24\">30.03.1958</line><line lineNr=\"25\">Musterweg 22/11</line><line lineNr=\"26\">--</line><line lineNr=\"27\">1080 Musterdorf</line><line lineNr=\"28\">--</line><line lineNr=\"29\">31.07.1982</line><line lineNr=\"30\">--</line><line lineNr=\"31\">--</line><line lineNr=\"32\">1.816,76</line><line lineNr=\"33\">797,40</line><line lineNr=\"34\">--</line><line lineNr=\"35\">--</line><line lineNr=\"36\">--</line><line lineNr=\"37\">--</line><line lineNr=\"38\">--</line><line lineNr=\"39\">--</line><line lineNr=\"40\">--</line><line lineNr=\"41\">--</line><line lineNr=\"42\">--</line><line lineNr=\"43\">1.816,76</line><line lineNr=\"44\">--</line><line lineNr=\"45\">--</line><line lineNr=\"46\">--</line><line lineNr=\"47\">Z</line><line lineNr=\"48\">--</line><line lineNr=\"49\">30.09.2016</line><line lineNr=\"50\">--</line><line lineNr=\"51\">--</line><line lineNr=\"52\">31.03.1978</line><line lineNr=\"53\">1,106</line><line lineNr=\"54\">N</line><line lineNr=\"55\">--</line><line lineNr=\"56\">--</line><line lineNr=\"57\">--</line><line lineNr=\"58\">02.09.1974</line><line lineNr=\"59\">31.03.1976</line><line lineNr=\"60\">N</line><line lineNr=\"61\">*</line><line lineNr=\"62\">--</line><line lineNr=\"63\">--</line><line lineNr=\"64\">--</line><line lineNr=\"65\">--</line><line lineNr=\"66\">--</line><line lineNr=\"67\">*</line><line lineNr=\"68\">Zahlschein</line><line lineNr=\"69\">*</line><line lineNr=\"70\">--</line><line lineNr=\"71\">--</line><line lineNr=\"72\"/></payload>\n" +
                "</Briefdaten>", result);
    }

    @Test
    public void getUserdataXmlTelV1() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "SV111"));
        assertFalse(StringUtils.isBlank(result));
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Briefdaten>\n" +
                "    <MD>\n" +
                "        <Aktenzeichen>\n" +
                "            <Vstr>02</Vstr>\n" +
                "        </Aktenzeichen>\n" +
                "        <Schreiben>\n" +
                "            <Kopf Klappe=\"27770\" LdstKz=\"H\"/>\n" +
                "            <Abtkz>HVBA</Abtkz>\n" +
                "        </Schreiben>\n" +
                "    </MD>\n" +
                "    <Spruch>\n" +
                "        <Spruch413>\n" +
                "            <KenLdst>H</KenLdst>\n" +
                "        </Spruch413>\n" +
                "    </Spruch>\n" +
                "    <payload><line lineNr=\"1\">1078300358</line><line lineNr=\"2\">Frau</line><line lineNr=\"3\">--</line><line lineNr=\"4\">Hilda (B.UE108)</line><line lineNr=\"5\">Mustermann</line><line lineNr=\"6\">--</line><line lineNr=\"7\">--</line><line lineNr=\"8\">Musterweg 22/11</line><line lineNr=\"9\">--</line><line lineNr=\"10\">--</line><line lineNr=\"11\">1080</line><line lineNr=\"12\">Musterdorf</line><line lineNr=\"13\">--</line><line lineNr=\"14\">Sehr geehrter Frau Mustermann!</line><line lineNr=\"15\">A</line><line lineNr=\"16\">M</line><line lineNr=\"17\">2382327770238612387023800</line><line lineNr=\"18\">1</line><line lineNr=\"19\">11</line><line lineNr=\"20\">20160627</line><line lineNr=\"21\">17.05.2016</line><line lineNr=\"22\">411</line><line lineNr=\"23\">Hilda Mustermann</line><line lineNr=\"24\">30.03.1958</line><line lineNr=\"25\">Musterweg 22/11</line><line lineNr=\"26\">--</line><line lineNr=\"27\">1080 Musterdorf</line><line lineNr=\"28\">--</line><line lineNr=\"29\">31.07.1982</line><line lineNr=\"30\">--</line><line lineNr=\"31\">--</line><line lineNr=\"32\">1.816,76</line><line lineNr=\"33\">797,40</line><line lineNr=\"34\">--</line><line lineNr=\"35\">--</line><line lineNr=\"36\">--</line><line lineNr=\"37\">--</line><line lineNr=\"38\">--</line><line lineNr=\"39\">--</line><line lineNr=\"40\">--</line><line lineNr=\"41\">--</line><line lineNr=\"42\">--</line><line lineNr=\"43\">1.816,76</line><line lineNr=\"44\">--</line><line lineNr=\"45\">--</line><line lineNr=\"46\">--</line><line lineNr=\"47\">Z</line><line lineNr=\"48\">--</line><line lineNr=\"49\">30.09.2016</line><line lineNr=\"50\">--</line><line lineNr=\"51\">--</line><line lineNr=\"52\">31.03.1978</line><line lineNr=\"53\">1,106</line><line lineNr=\"54\">N</line><line lineNr=\"55\">--</line><line lineNr=\"56\">--</line><line lineNr=\"57\">--</line><line lineNr=\"58\">02.09.1974</line><line lineNr=\"59\">31.03.1976</line><line lineNr=\"60\">N</line><line lineNr=\"61\">*</line><line lineNr=\"62\">--</line><line lineNr=\"63\">--</line><line lineNr=\"64\">--</line><line lineNr=\"65\">--</line><line lineNr=\"66\">--</line><line lineNr=\"67\">*</line><line lineNr=\"68\">Zahlschein</line><line lineNr=\"69\">*</line><line lineNr=\"70\">--</line><line lineNr=\"71\">--</line><line lineNr=\"72\"/></payload>\n" +
                "</Briefdaten>", result);
    }

    @Test
    public void getUserdataXmlTel308() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "UE105"));
        assertFalse(StringUtils.isBlank(result));
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Briefdaten>\n" +
                "    <MD>\n" +
                "        <Aktenzeichen>\n" +
                "            <Vstr>02</Vstr>\n" +
                "        </Aktenzeichen>\n" +
                "        <Schreiben>\n" +
                "            <Kopf Klappe=\"23870\" LdstKz=\"H\"/>\n" +
                "            <Abtkz>HVBA</Abtkz>\n" +
                "        </Schreiben>\n" +
                "    </MD>\n" +
                "    <Spruch>\n" +
                "        <Spruch413>\n" +
                "            <KenLdst>H</KenLdst>\n" +
                "        </Spruch413>\n" +
                "    </Spruch>\n" +
                "    <payload><line lineNr=\"1\">1078300358</line><line lineNr=\"2\">Frau</line><line lineNr=\"3\">--</line><line lineNr=\"4\">Hilda (B.UE108)</line><line lineNr=\"5\">Mustermann</line><line lineNr=\"6\">--</line><line lineNr=\"7\">--</line><line lineNr=\"8\">Musterweg 22/11</line><line lineNr=\"9\">--</line><line lineNr=\"10\">--</line><line lineNr=\"11\">1080</line><line lineNr=\"12\">Musterdorf</line><line lineNr=\"13\">--</line><line lineNr=\"14\">Sehr geehrter Frau Mustermann!</line><line lineNr=\"15\">A</line><line lineNr=\"16\">M</line><line lineNr=\"17\">2382327770238612387023800</line><line lineNr=\"18\">1</line><line lineNr=\"19\">11</line><line lineNr=\"20\">20160627</line><line lineNr=\"21\">17.05.2016</line><line lineNr=\"22\">411</line><line lineNr=\"23\">Hilda Mustermann</line><line lineNr=\"24\">30.03.1958</line><line lineNr=\"25\">Musterweg 22/11</line><line lineNr=\"26\">--</line><line lineNr=\"27\">1080 Musterdorf</line><line lineNr=\"28\">--</line><line lineNr=\"29\">31.07.1982</line><line lineNr=\"30\">--</line><line lineNr=\"31\">--</line><line lineNr=\"32\">1.816,76</line><line lineNr=\"33\">797,40</line><line lineNr=\"34\">--</line><line lineNr=\"35\">--</line><line lineNr=\"36\">--</line><line lineNr=\"37\">--</line><line lineNr=\"38\">--</line><line lineNr=\"39\">--</line><line lineNr=\"40\">--</line><line lineNr=\"41\">--</line><line lineNr=\"42\">--</line><line lineNr=\"43\">1.816,76</line><line lineNr=\"44\">--</line><line lineNr=\"45\">--</line><line lineNr=\"46\">--</line><line lineNr=\"47\">Z</line><line lineNr=\"48\">--</line><line lineNr=\"49\">30.09.2016</line><line lineNr=\"50\">--</line><line lineNr=\"51\">--</line><line lineNr=\"52\">31.03.1978</line><line lineNr=\"53\">1,106</line><line lineNr=\"54\">N</line><line lineNr=\"55\">--</line><line lineNr=\"56\">--</line><line lineNr=\"57\">--</line><line lineNr=\"58\">02.09.1974</line><line lineNr=\"59\">31.03.1976</line><line lineNr=\"60\">N</line><line lineNr=\"61\">*</line><line lineNr=\"62\">--</line><line lineNr=\"63\">--</line><line lineNr=\"64\">--</line><line lineNr=\"65\">--</line><line lineNr=\"66\">--</line><line lineNr=\"67\">*</line><line lineNr=\"68\">Zahlschein</line><line lineNr=\"69\">*</line><line lineNr=\"70\">--</line><line lineNr=\"71\">--</line><line lineNr=\"72\"/></payload>\n" +
                "</Briefdaten>", result);
    }

    @Test
    public void getUserdataXmlTelV2() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "MA010"));
        assertFalse(StringUtils.isBlank(result));
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Briefdaten>\n" +
                "    <MD>\n" +
                "        <Aktenzeichen>\n" +
                "            <Vstr>02</Vstr>\n" +
                "        </Aktenzeichen>\n" +
                "        <Schreiben>\n" +
                "            <Kopf Klappe=\"23861\" LdstKz=\"H\"/>\n" +
                "            <Abtkz>HVBA</Abtkz>\n" +
                "        </Schreiben>\n" +
                "    </MD>\n" +
                "    <Spruch>\n" +
                "        <Spruch413>\n" +
                "            <KenLdst>H</KenLdst>\n" +
                "        </Spruch413>\n" +
                "    </Spruch>\n" +
                "    <payload><line lineNr=\"1\">1078300358</line><line lineNr=\"2\">Frau</line><line lineNr=\"3\">--</line><line lineNr=\"4\">Hilda (B.UE108)</line><line lineNr=\"5\">Mustermann</line><line lineNr=\"6\">--</line><line lineNr=\"7\">--</line><line lineNr=\"8\">Musterweg 22/11</line><line lineNr=\"9\">--</line><line lineNr=\"10\">--</line><line lineNr=\"11\">1080</line><line lineNr=\"12\">Musterdorf</line><line lineNr=\"13\">--</line><line lineNr=\"14\">Sehr geehrter Frau Mustermann!</line><line lineNr=\"15\">A</line><line lineNr=\"16\">M</line><line lineNr=\"17\">2382327770238612387023800</line><line lineNr=\"18\">1</line><line lineNr=\"19\">11</line><line lineNr=\"20\">20160627</line><line lineNr=\"21\">17.05.2016</line><line lineNr=\"22\">411</line><line lineNr=\"23\">Hilda Mustermann</line><line lineNr=\"24\">30.03.1958</line><line lineNr=\"25\">Musterweg 22/11</line><line lineNr=\"26\">--</line><line lineNr=\"27\">1080 Musterdorf</line><line lineNr=\"28\">--</line><line lineNr=\"29\">31.07.1982</line><line lineNr=\"30\">--</line><line lineNr=\"31\">--</line><line lineNr=\"32\">1.816,76</line><line lineNr=\"33\">797,40</line><line lineNr=\"34\">--</line><line lineNr=\"35\">--</line><line lineNr=\"36\">--</line><line lineNr=\"37\">--</line><line lineNr=\"38\">--</line><line lineNr=\"39\">--</line><line lineNr=\"40\">--</line><line lineNr=\"41\">--</line><line lineNr=\"42\">--</line><line lineNr=\"43\">1.816,76</line><line lineNr=\"44\">--</line><line lineNr=\"45\">--</line><line lineNr=\"46\">--</line><line lineNr=\"47\">Z</line><line lineNr=\"48\">--</line><line lineNr=\"49\">30.09.2016</line><line lineNr=\"50\">--</line><line lineNr=\"51\">--</line><line lineNr=\"52\">31.03.1978</line><line lineNr=\"53\">1,106</line><line lineNr=\"54\">N</line><line lineNr=\"55\">--</line><line lineNr=\"56\">--</line><line lineNr=\"57\">--</line><line lineNr=\"58\">02.09.1974</line><line lineNr=\"59\">31.03.1976</line><line lineNr=\"60\">N</line><line lineNr=\"61\">*</line><line lineNr=\"62\">--</line><line lineNr=\"63\">--</line><line lineNr=\"64\">--</line><line lineNr=\"65\">--</line><line lineNr=\"66\">--</line><line lineNr=\"67\">*</line><line lineNr=\"68\">Zahlschein</line><line lineNr=\"69\">*</line><line lineNr=\"70\">--</line><line lineNr=\"71\">--</line><line lineNr=\"72\"/></payload>\n" +
                "</Briefdaten>", result);
    }

}
package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by georg on 7/8/16.
 */
public class UserDataServiceBeanTest {

    UserDataServiceBean userDataService = new UserDataServiceBean();

    @Test
    public void testAenderungsFreigabeNummer() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "UE108", "130/0001"));
        assertFalse(StringUtils.isBlank(result));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new ByteArrayInputStream(result.getBytes()));
        assertEquals("130", ((Element) document.getElementsByTagName("Aktionsnummer").item(0)).getTextContent());
        assertEquals("0001", ((Element) document.getElementsByTagName("Korrekturnummer").item(0)).getTextContent());
        assertEquals("UE", ((Element) document.getElementsByTagName("Dokument").item(0)).getTextContent());
        assertEquals("108", ((Element) document.getElementsByTagName("SpruchSchlzl").item(0)).getTextContent());
    }

    @Test
    public void testUuid() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "UE108", "130/0001"));
        assertFalse(StringUtils.isBlank(result));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new ByteArrayInputStream(result.getBytes()));
        assertEquals("1078300358", ((Element) document.getElementsByTagName("line").item(0)).getTextContent());
    }

    @Test
    public void testFax() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "UE108", "130/0001"));
        assertFalse(StringUtils.isBlank(result));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new ByteArrayInputStream(result.getBytes()));
        assertEquals("23891", ((Element) document.getElementsByTagName("Kopf").item(0)).getAttribute("Faxnr"));
    }

    @Test
    public void getUserdataXmlTelMitarb() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "UE108", "130/0001"));
        assertFalse(StringUtils.isBlank(result));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new ByteArrayInputStream(result.getBytes()));
        assertEquals("23823", ((Element) document.getElementsByTagName("Kopf").item(0)).getAttribute("Klappe"));
    }

    @Test
    public void getUserdataXmlTelV1() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "SV111", "130/0001"));
        assertFalse(StringUtils.isBlank(result));

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new ByteArrayInputStream(result.getBytes()));
        assertEquals("27770", ((Element) document.getElementsByTagName("Kopf").item(0)).getAttribute("Klappe"));
    }

    @Test
    public void getUserdataXmlTel308() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "UE105", "130/0001"));
        assertFalse(StringUtils.isBlank(result));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new ByteArrayInputStream(result.getBytes()));
        assertEquals("23870", ((Element) document.getElementsByTagName("Kopf").item(0)).getAttribute("Klappe"));
    }

    @Test
    public void getUserdataXmlTelV2() throws Exception {
        String result = IOUtils.toString(userDataService.getUserdataXml(new BufferedInputStream(getClass().getResource(
                "/HitClouTextInputData/OrderData.dat").openStream()), "MA010", "130/0001"));
        assertFalse(StringUtils.isBlank(result));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new ByteArrayInputStream(result.getBytes()));
        assertEquals("23861", ((Element) document.getElementsByTagName("Kopf").item(0)).getAttribute("Klappe"));
    }

}
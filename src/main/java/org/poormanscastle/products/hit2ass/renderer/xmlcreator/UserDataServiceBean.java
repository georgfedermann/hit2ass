package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.poormanscastle.products.hit2ass.exceptions.HitAssTransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Creates a generic XML from a HIT / CLOU business data input text file, by embedding the text data line by line
 * into <zeile /> elements in a new XML document.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 7/8/16.
 */
public class UserDataServiceBean implements UserDataService {

    @Override
    public InputStream getUserdataXml(InputStream dataInputStream, String bausteinName) {
        String inputText = "If you can read this, the actual dataInputStream was corrupted and could not be read.";
        try {
            inputText = IOUtils.toString(dataInputStream);

            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getClass().getResourceAsStream("/xml/HitAssBriefdatenXmlPrototype.xml"));

            // create payload content
            Element payload = (Element) document.getElementsByTagName("payload").item(0);

            BufferedReader reader = new BufferedReader(new StringReader(inputText));
            String line = null;
            int sequence = 1;
            while ((line = reader.readLine()) != null) {
                Element lineElement = document.createElement("line");
                lineElement.setAttribute("lineNr", String.valueOf(sequence++));
                lineElement.setTextContent(line);
                payload.appendChild(lineElement);
            }

            // TODO add hook point to pre/post process XML here
            // add direct inward dial (Durchwahl)
            ((Element) document.getElementsByTagName("Kopf").item(0)).setAttribute("Klappe",
                    inferDid(bausteinName, document.getElementsByTagName("line").item(16).getTextContent()));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(outputStream));

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            String errorMessage = StringUtils.join("Could not process HIT / CLOU input text data, because: ",
                    e.getMessage(), " - Input text was ", inputText);
            throw new HitAssTransformerException(errorMessage, e);
        }
    }

    @Override
    public String inferDid(String bausteinName, String dataBlock) {
        String telMitarb = dataBlock.substring(0, 5);
        String telv1 = dataBlock.substring(5, 10);
        String telv2 = dataBlock.substring(10, 15);
        String tel308 = dataBlock.substring(15, 20);
        // String telAbtl = dataBlock.substring(20, 25);
        String result = telMitarb;
        if ("SV111".equals(bausteinName)) {
            result = telv1;
        } else if (Arrays.stream(new String[]{"UE105", "UE109", "UE110", "UE111", "UE112",
                "OR003", "OR004", "OR005",
                "ER005", "ER006",
                "UN003", "UN005", "UN006", "UN012", "UN013",
                "UW006", "UW021", "UW022", "UW023"}).anyMatch(bausteinName::equals)) {
            result = tel308;
        } else if (Arrays.stream(new String[]{"MA010",
                "AH004", "AH007", "AH008",
                "EK015",
                "WB108", "WB117",
                "HE004"}).anyMatch(bausteinName::equals)) {
            result = telv2;
        }

        Arrays.stream(new String[]{""}).anyMatch(bausteinName::equals);

        return result;
    }
}

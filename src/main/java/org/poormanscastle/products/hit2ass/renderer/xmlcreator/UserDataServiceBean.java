package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.poormanscastle.products.hit2ass.exceptions.HitAssTransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;

/**
 * Creates a generic XML from a HIT / CLOU business data input text file, by embedding the text data line by line
 * into <zeile /> elements in a new XML document.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 7/8/16.
 */
public class UserDataServiceBean implements UserDataService {

    @Override
    public InputStream getUserdataXml(InputStream dataInputStream) {
        String inputText = "If you can read this, the actual dataInputStream was corrupted and could not be read.";
        try {
            inputText = IOUtils.toString(dataInputStream);
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element documentElement = document.createElement("UserData");
            document.appendChild(documentElement);
            Element metadata = document.createElement("Metadata");
            documentElement.appendChild(metadata);
            Element payload = document.createElement("payload");
            documentElement.appendChild(payload);

            BufferedReader reader = new BufferedReader(new StringReader(inputText));
            String line = null;
            int sequence = 1;
            while ((line = reader.readLine()) != null) {
                Element lineElement = document.createElement("line");
                lineElement.setAttribute("lineNr", String.valueOf(sequence++));
                lineElement.setTextContent(line);
                payload.appendChild(lineElement);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(outputStream));

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            String errorMessage = StringUtils.join("Could not process HIT / CLOU input text data, because: ",
                    e.getMessage(), " - Input text was ", inputText);
            throw new HitAssTransformerException(errorMessage, e);
        }
    }

}

package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

/**
 * Merges the data input file with the given XML template to
 * produce the DocFamily Data XML.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/11/16.
 * <p>
 * TODO since the strategy to produce the userdata XML has changed fundamentally this type is not needed any more.
 * But since it creates a velocity template out of thin air as opposed to reading it from a file it is still
 * interesting for future reference. Push this type to a sample source code repository and then delete it from
 * this project.
 */
public class XmlDataMerger {

    {
        init();
    }

    void init() {
        Velocity.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
        Velocity.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        Velocity.init();
    }

    /**
     * This method creates a velocity template from the data contained in
     * the given input stream.
     *
     * @param xmlVelocityTemplateIs
     * @return
     */
    Template createVelocityTemplate(InputStream xmlVelocityTemplateIs) {
        try {
            RuntimeServices veloServices = RuntimeSingleton.getRuntimeServices();
            Reader reader = new InputStreamReader(xmlVelocityTemplateIs);
            SimpleNode veloNode = veloServices.parse(reader, "XmlTemplate");
            Template template = new Template();
            template.setRuntimeServices(veloServices);
            template.setData(veloNode);
            template.initDocument();
            return template;
        } catch (ParseException e) {
            throw new DataXmlMergerException(StringUtils.join("Could not process velocity template, because ", e.getMessage()), e);
        }
    }

    /**
     * This method "parses" the input stream. Each line is interpreted as one value.
     * Each value gets written to a velocity context parameter with names
     * param0, param1, param2, ...
     *
     * @param dataIs
     * @return a velocity context containing the values found in dataIs
     */
    VelocityContext createVelocityContext(InputStream dataIs) {
        try {
            VelocityContext context = new VelocityContext();
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataIs));
            String line, paramName = "param";
            int sequence = 0;
            while ((line = reader.readLine()) != null) {
                context.put(StringUtils.join(paramName, sequence++), line);
            }
            return context;
        } catch (IOException e) {
            throw new DataXmlMergerException(StringUtils.join("Could not process input data, because ", e.getMessage()), e);
        }
    }

    public String merge(InputStream xmlVelocityTemplateIs, InputStream dataIs) {
        StringWriter writer = new StringWriter();
        createVelocityTemplate(xmlVelocityTemplateIs).merge(createVelocityContext(dataIs), writer);
        return writer.toString();
    }

}

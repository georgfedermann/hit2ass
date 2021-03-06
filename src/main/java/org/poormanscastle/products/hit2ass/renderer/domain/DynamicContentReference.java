package org.poormanscastle.products.hit2ass.renderer.domain;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;

/**
 * Represents the type BoDynamicContentReference.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class DynamicContentReference implements Content {

    private final String name;
    private final String xpath;
    private final FontWeight fontWeight;
    private final TextDecoration textDecoration;

    public DynamicContentReference(String name, String xpath, FontWeight fontWeight, TextDecoration textDecoration) {
        this.name = name;
        this.xpath = xpath;
        this.fontWeight = fontWeight;
        this.textDecoration = textDecoration;
    }

    public String getName() {
        return name;
    }

    public String getXpath() {
        return xpath;
    }

    @Override
    public String toString() {
        return "DynamicContentReference{" +
                "name='" + name + '\'' +
                ", xpath='" + xpath + '\'' +
                '}';
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("dynamicContentName", name);
        context.put("xpath", xpath);
        context.put("fontWeight", fontWeight.getValue());
        context.put("textDecoration", textDecoration.getValue());
        Template template = Velocity.getTemplate("/velocity/TemplateDynamicContentReference.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }
}

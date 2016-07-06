package org.poormanscastle.products.hit2ass.renderer.domain;

import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;

/**
 * Represents the type BoDynamicContentReference.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class DynamicContentReference implements Content {

    private final String name;
    private final String xpath;

    public DynamicContentReference(String name, String xpath) {
        this.name = name;
        this.xpath = xpath;
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
        Template template = Velocity.getTemplate("/velocity/TemplateDynamicContentReference.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }
}

package org.poormanscastle.products.hit2ass.renderer.domain;

import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;

/**
 * Represents the type com.assentis.cockpit.bo.BoText.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class Text implements Content {

    private final String name;
    private final String text;

    public Text(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("textName", name);
        context.put("textContent", text);
        Template template = Velocity.getTemplate("/velocity/TemplateText.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    @Override
    public String toString() {
        return "Text{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}

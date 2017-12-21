package org.poormanscastle.products.hit2ass.renderer.domain;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;

/**
 * Represents the type com.assentis.cockpit.bo.BoText.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class Text implements Content {

    private final String name;
    private final String text;
    private final FontWeight fontWeight;
    private final TextDecoration textDecoration;

    public Text(String name, String text, FontWeight fontWeight, TextDecoration textDecoration) {
        this.name = name.replaceAll("[^\\w\\söÖäÄüÜß]", "");
        this.text = text;
        this.fontWeight = fontWeight;
        this.textDecoration = textDecoration;
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
        context.put("fontWeight", fontWeight.getValue());
        context.put("textDecoration", textDecoration.getValue());
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

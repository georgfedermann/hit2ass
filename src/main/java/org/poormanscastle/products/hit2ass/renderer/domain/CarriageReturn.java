package org.poormanscastle.products.hit2ass.renderer.domain;

import org.poormanscastle.products.hit2ass.ast.domain.Expression;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;

/**
 * Represents the type com.assentis.cockpit.bo.BoCReturn.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class CarriageReturn implements Content {

    private final String name;
    private final Expression repetitionExpression;

    public CarriageReturn(String name, Expression repetitionExpression) {
        this.name = name;
        this.repetitionExpression = repetitionExpression;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CarriageReturn{" +
                "name='" + name + '\'' +
                ", repetitionExpression.toXPathString()='" + repetitionExpression.toXPathString() + "'" +
                '}';
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("linebreakName", name);
        context.put("repetitionExpression", repetitionExpression.toXPathString());
        Template template = Velocity.getTemplate("/velocity/TemplateNewLineHitCommand.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

}

package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.poormanscastle.products.hit2ass.ast.domain.Expression;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;

import java.io.StringWriter;

public class TableRowRepetitionImpl implements TableRowRepetition {

    private final Expression expression;

    TableRowRepetitionImpl(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("repetitionExpression", expression.toXPathString());
        Template template = Velocity.getTemplate("/velocity/tables/TemplateRowRepetition.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }
}

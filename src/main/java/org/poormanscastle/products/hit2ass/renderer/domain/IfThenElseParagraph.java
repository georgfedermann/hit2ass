package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;
import org.poormanscastle.products.hit2ass.ast.domain.Expression;

/**
 * Created by georg.federmann@poormanscastle.com on 5/11/16.
 */
public final class IfThenElseParagraph extends AbstractContainer {

    private final Expression condition;

    public IfThenElseParagraph(String name, Expression condition) {
        super(name, "/velocity/TemplateIfThenElseParagraph.vlt");
        this.condition = condition;
    }

    @Override
    public void setupContext(VelocityContext context) {
        context.put("xpathExpression", condition.toXPathString());
    }

    @Override
    public String toString() {
        return "IfThenElseParagraph{" +
                "condition=" + condition +
                ", components=" + getComponents() +
                '}';
    }

}

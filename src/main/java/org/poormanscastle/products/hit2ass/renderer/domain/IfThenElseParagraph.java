package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;
import org.poormanscastle.products.hit2ass.ast.domain.Expression;

/**
 * An IfThenElseParagraph consists of an expression that should evaluate to something that resembles a boolean
 * expression. Also, the IfThenElseParagraph have zero or more of the following elements: IfThenParagraph holding
 * the content that will be rendered if the expression resolved to true, and an IfElseParagraph which holds the content
 * for the false case.
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

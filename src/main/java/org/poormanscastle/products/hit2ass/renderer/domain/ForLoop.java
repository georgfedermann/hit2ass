package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;
import org.poormanscastle.products.hit2ass.ast.domain.Expression;

/**
 * This is the FOR command.
 * <p>
 * It is implemented by combining a DocDesign Aggregation with a repetition. The
 * body of the HIT/CLOU for loop is added as content to the aggregation element.
 * <p>
 * Created by georg on 7/13/16.
 */
public class ForLoop extends AbstractContainer {

    /**
     * A HIT/CLOU for loop just has an expression that gets evaluated
     * to an integer value. The body of the for loop gets executed for
     * as many times as the numeric value of this expression states.
     */
    private final Expression expression;

    public ForLoop(String name, Expression expression) {
        super(name, "/velocity/TemplateFor.vlt");
        this.expression = expression;
    }

    @Override
    public void setupContext(VelocityContext context) {
        context.put("repetitionExpression", expression.toXPathString());
    }

}

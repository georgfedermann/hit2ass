package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.poormanscastle.products.hit2ass.ast.domain.BinaryOperatorExpression;
import org.poormanscastle.products.hit2ass.ast.domain.Expression;
import org.poormanscastle.products.hit2ass.ast.domain.TextExpression;

/**
 * This is the WHILE command.
 * <p>
 * It is implemented by combining a DocDesign Aggregation with a repetition.
 * <p>
 * Created by georg on 7/30/16.
 */
public class WhileLoop extends AbstractContainer {

    private final Expression expression;

    public WhileLoop(String name, Expression expression) {
        super(name, "/velocity/TemplateWhileLelementFlavor.vlt");
        this.expression = expression;
    }

    @Override
    public void setupContext(VelocityContext context) {
        // here we are making some assumptions:
        // the transformer works for lelement-flavor while loops, where values are read from an
        // input source (file, XML) into an lelement variable, and the value of lelement is
        // appended to some list, until a flag value is found.
        // so, in the loop condition, this algorithm searches for a TextExpression containing
        // the flag value (*)
        String flagValue = "";
        if (!(expression instanceof BinaryOperatorExpression)) {
            throw new IllegalStateException(StringUtils.join("Only BinaryOperatorExpressions are supported here, not this such: ", expression));
        }
        BinaryOperatorExpression loopCondition = (BinaryOperatorExpression) expression;
        // search for flag value
        if (loopCondition.getLhs() instanceof TextExpression) {
            flagValue = loopCondition.getLhs().toXPathString();
        } else if (loopCondition.getRhs() instanceof TextExpression) {
            flagValue = loopCondition.getRhs().toXPathString();
        } else {
            throw new IllegalStateException(StringUtils.join("No TextExpression found in loop condition: ", loopCondition));
        }
        context.put("flagValue", flagValue);
    }

    @Override
    public String toString() {
        return "WhileLoop{" +
                "expression=" + expression +
                ", components=" + getComponents() +
                '}';
    }
}

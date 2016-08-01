package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;
import org.poormanscastle.products.hit2ass.ast.domain.Expression;

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
        context.put("inputVariable", "lelement");
        context.put("listVariable", "indtext0");
        context.put("flagValue", "*");
    }

    @Override
    public String toString() {
        return "WhileLoop{" +
                "expression=" + expression +
                ", components=" + getComponents() +
                '}';
    }
}

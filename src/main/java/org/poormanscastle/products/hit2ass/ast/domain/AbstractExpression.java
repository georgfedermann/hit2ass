package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg.federmann@poormanscastle.com on 22.02.2016.
 */
public abstract class AbstractExpression<T> extends AbstractAstItem implements Expression<T> {

    private ExpressionState state = ExpressionState.NOT_DETERMINED_YET;

    public AbstractExpression(CodePosition codePosition) {
        super(codePosition);
    }

    @Override
    public ExpressionState getState() {
        return state;
    }

    public void setState(ExpressionState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AbstractExpression{" +
                "state=" + state +
                '}';
    }
}

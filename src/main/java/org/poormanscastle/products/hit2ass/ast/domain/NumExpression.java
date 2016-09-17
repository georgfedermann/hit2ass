package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * evaluates to an integer expression.
 * Created by georg.federmann@poormanscastle.com on 17.02.2016.
 */
public final class NumExpression extends AbstractExpression<Integer> {

    private final Integer value;

    public NumExpression(CodePosition codePosition, Integer value) {
        super(codePosition);
        checkArgument(value != null);
        this.value = value;
        setState(ExpressionState.VALID);
    }

    @Override
    public String toXPathString() {
        return String.valueOf(value);
    }

    @Override
    public Type getValueType() {
        return Type.INT;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithNumExpression(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitNumExpression(this);
        visitor.leaveNumExpression(this);
    }

    @Override
    public String toString() {
        return "NumExpression{" +
                "codePosition=" + getCodePosition() +
                ", value=" + value +
                '}';
    }
}

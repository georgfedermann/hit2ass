package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * evaluates to a floating point value.
 * Created by georg.federmann@poormanscastle.com on 17.02.2016.
 */
public final class DecimalExpression extends AbstractExpression<Double> {

    private final Double value;

    public DecimalExpression(CodePosition codePosition, Double value) {
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
        return Type.DOUBLE;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithDecimalExpression(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitDecimalExpression(this);
        visitor.leaveDecimalExpression(this);
    }

    @Override
    public String toString() {
        return "DecimalExpression{" +
                "codePosition=" + getCodePosition() +
                ", value=" + value +
                '}';
    }
}

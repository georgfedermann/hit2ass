package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by georg on 7/22/16.
 */
public class BooleanExpression extends AbstractExpression<Boolean> {

    private final Boolean value;

    public BooleanExpression(CodePosition codePosition, Boolean value) {
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
    public String toDebugString() {
        return String.valueOf(value);
    }

    @Override
    public Type getValueType() {
        return Type.BOOLEAN;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithBooleanExpression(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitBooleanExpression(this);
        visitor.leaveBooleanExpression(this);
    }

    @Override
    public String toString() {
        return "BooleanExpression{" +
                "value=" + value +
                '}';
    }

}

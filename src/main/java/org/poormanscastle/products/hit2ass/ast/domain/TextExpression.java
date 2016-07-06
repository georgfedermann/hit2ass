package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * evaluates to a string value.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 17.02.2016.
 */
public final class TextExpression extends AbstractExpression<String> {

    private final String value;

    public TextExpression(CodePosition codePosition, String value) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(value));
        // string literals are delimited with quotes. JavaCC hands the quotes through. I remove all quotes here.
        // TODO maybe keep escaped quotes
        this.value = value.replaceAll("^\"", "").replaceAll("\"$", "").replaceAll("\\\\\"", "\"");
        setState(ExpressionState.VALID);
    }

    @Override
    public String toXPathString() {
        return StringUtils.join(" '", value, "' ");
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type getValueType() {
        return Type.TEXT;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithTextExpression(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitTextExpression(this);
        visitor.leaveTextExpression(this);
    }

    @Override
    public String toString() {
        return "TextExpression{" +
                "codePosition=" + getCodePosition() +
                ", value='" + value + '\'' +
                '}';
    }
}

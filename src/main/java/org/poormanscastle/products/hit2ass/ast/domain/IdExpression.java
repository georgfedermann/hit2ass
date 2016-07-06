package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * resolves to the value of a variable identified by an id.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 17.02.2016.
 */
public final class IdExpression extends AbstractExpression<Object> {

    private final String id;

    /**
     * the type of this identifier needs to be set in the semantic compiler phase.
     */
    private Type valueType;

    private Object value;

    public IdExpression(CodePosition codePosition, String id) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(id));
        this.id = id;
    }

    @Override
    public String toXPathString() {
        return StringUtils.join(" var:read('", id, "') ");
    }

    public String getId() {
        return id;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Type getValueType() {
        return valueType;
    }

    public void setValueType(Type valueType) {
        this.valueType = valueType;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithIdExpression(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitIdExpression(this);
        visitor.leaveIdExpression(this);
    }

    @Override
    public String toString() {
        return "IdExpression{" +
                "id='" + id + '\'' +
                ", valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}

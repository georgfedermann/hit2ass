package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Defines a list through enumeration of its elements.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/21/16.
 */
public final class ListEnumerationExpression extends AbstractExpression<List> {

    private final ExpressionList expressionList;

    /**
     * this field can be used by an interpreter evaluating the expressionList field.
     */
    private List value;

    public ListEnumerationExpression(CodePosition codePosition, ExpressionList expressionList) {
        super(codePosition);
        this.expressionList = expressionList;
    }

    public ListEnumerationExpression(ExpressionList expressionList) {
        this(expressionList.getCodePosition(), expressionList);
    }

    @Override
    public String toXPathString() {
        throw new UnsupportedOperationException("yet to be implemented.");
    }

    @Override
    public String toDebugString() {
        throw new UnsupportedOperationException("yet to be implemented.");
    }

    @Override
    public Type getValueType() {
        return null;
    }

    @Override
    public List getValue() {
        return value;
    }

    @Override
    public void setValue(List value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithListEnumerationExpression(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitListEnumerationExpression(this);
        if (expressionList.handleProceedWith(visitor)) {
            expressionList.accept(visitor);
        }
        visitor.leaveListEnumerationExpression(this);
    }

    @Override
    public String toString() {
        return "ListEnumerationExpression{" +
                "codePosition=" + getCodePosition() +
                ", expressionList=" + expressionList +
                '}';
    }

}

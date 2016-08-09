package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 4/19/16.
 */
public class LastExpressionList extends AbstractAstItem implements ExpressionList {

    private final Expression head;

    public LastExpressionList(CodePosition codePosition, Expression head) {
        super(codePosition);
        checkNotNull(head);
        this.head = head;
    }

    @Override
    public Expression getHead() {
        return head;
    }

    @Override
    public ExpressionList getTail() {
        return null;
    }

    @Override
    public String toXPathString() {
        return head.toXPathString();
    }

    public LastExpressionList(Expression head) {
        this(head.getCodePosition(), head);
    }

    @Override
    public ExpressionState getState() {
        return head.getState();
    }

    @Override
    public Type getValueType() {
        return head.getValueType();
    }

    @Override
    public Object getValue() {
        return head.getValue();
    }

    @Override
    public void setValue(Object value) {
        head.setValue(value);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithLastExpressionList(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitLastExpressionList(this);
        if (head.handleProceedWith(visitor)) {
            head.accept(visitor);
        }
        visitor.leaveLastExpressionList(this);
    }

    @Override
    public String toString() {
        return "LastExpressionList{" +
                "codePosition=" + getCodePosition() +
                "head='" + head.toString() +
                '}';
    }

}

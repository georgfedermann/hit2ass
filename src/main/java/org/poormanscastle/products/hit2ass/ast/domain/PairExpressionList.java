package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 4/19/16.
 */
public class PairExpressionList extends AbstractAstItem implements ExpressionList {

    private final Expression head;

    private final ExpressionList tail;

    public PairExpressionList(CodePosition codePosition, Expression head, ExpressionList tail) {
        super(codePosition);
        checkNotNull(head);
        checkNotNull(tail);
        this.head = head;
        this.tail = tail;
    }

    @Override
    public String toXPathString() {
        return StringUtils.join(head.toXPathString(), " , ", tail.toXPathString());
    }

    public PairExpressionList(Expression head, ExpressionList tail) {
        this(head.getCodePosition(), head, tail);
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
        return visitor.proceedWithPairExpressionList(this);
    }

    public Expression getHead() {
        return head;
    }

    public ExpressionList getTail() {
        return tail;
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitPairExpressionList(this);
        if (head.handleProceedWith(visitor)) {
            head.accept(visitor);
        }
        if (tail.handleProceedWith(visitor)) {
            tail.accept(visitor);
        }
        visitor.leavePairExpressionList(this);
    }
}

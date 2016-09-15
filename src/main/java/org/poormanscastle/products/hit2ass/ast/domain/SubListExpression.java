package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a new ListExpression from an existing ListExpression by
 * defining a sublist of the original list by giving a start index
 * and an end index.
 * Created by georg.federmann@poormanscastle.com on 5/3/16.
 */
public class SubListExpression extends AbstractExpression<List> {

    /**
     * must be given for any SubListExpression.
     */
    private final Expression startIndex;

    /**
     * must be given for any SubListExpression. Can be equal to
     * startIndex, if the list consists of only one element.
     */
    private final Expression endIndex;

    /**
     * this field can be used by an interpreter evaluating the expressionList field.
     */
    private List value;

    public SubListExpression(CodePosition codePosition, Expression startIndex, Expression endIndex) {
        super(codePosition);
        checkNotNull(startIndex);
        this.startIndex = startIndex;
        this.endIndex = endIndex == null ? startIndex : endIndex;
    }

    @Override
    public String toXPathString() {
        throw new UnsupportedOperationException("yet to be implemented.");
    }

    /**
     * Convenience constructor for single element sub lists, where startIndex
     * equals endIndex.
     *
     * @param codePosition the position within the source code
     * @param startIndex the starting index of the sublist
     */
    public SubListExpression(CodePosition codePosition, Expression startIndex) {
        this(codePosition, startIndex, startIndex);
    }

    public Expression getStartIndex() {
        return startIndex;
    }

    public Expression getEndIndex() {
        return endIndex;
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
        this.value = value;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithSubListExpression(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitSubListExpression(this);
        if (startIndex.handleProceedWith(visitor)) {
            startIndex.accept(visitor);
        }
        if (endIndex.handleProceedWith(visitor)) {
            endIndex.accept(visitor);
        }
        visitor.leaveSubListExpression(this);
    }

    @Override
    public String toString() {
        return "SubListExpression{" +
                "codePosition=" + getCodePosition() +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", value=" + value +
                '}';
    }
}

package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public class PairClouBausteinElementList extends AbstractClouBausteinElementList {

    private final ClouBausteinElement head;

    private ClouBausteinElementList tail;

    public PairClouBausteinElementList(CodePosition codePosition, ClouBausteinElement head, ClouBausteinElementList tail) {
        super(codePosition);
        checkNotNull(head);
        checkNotNull(tail);
        this.head = head;
        this.tail = tail;
        this.tail.setParent(this);
    }

    public PairClouBausteinElementList(ClouBausteinElement head, ClouBausteinElementList tail) {
        this(head.getCodePosition(), head, tail);
    }

    public void setTail(ClouBausteinElementList tail) {
        this.tail = tail;
    }

    public ClouBausteinElementList getTail() {
        return tail;
    }

    public ClouBausteinElement getHead() {
        return head;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithPairClouBausteinElementList(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitPairClouBausteinElementList(this);
        if (head.handleProceedWith(visitor)) {
            head.accept(visitor);
        }
        if (tail.handleProceedWith(visitor)) {
            tail.accept(visitor);
        }
        visitor.leavePairClouBausteinElementList(this);
    }

    @Override
    public String toString() {
        return "PairClouBausteinElementList{" +
                "codePosition=" + getCodePosition() +
                ", head=" + head +
                ", tail=" + tail +
                '}';
    }
}

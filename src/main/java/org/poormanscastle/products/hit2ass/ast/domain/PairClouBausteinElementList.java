package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public class PairClouBausteinElementList extends AbstractClouBausteinElementList implements ClouBausteinElementList {

    private final ClouBausteinElement head;

    private ClouBausteinElementList tail;

    public PairClouBausteinElementList(CodePosition codePosition, ClouBausteinElement head, ClouBausteinElementList tail) {
        super(codePosition);
        checkNotNull(head);
        this.head = head;
        checkNotNull(tail);
        this.tail = tail;
        this.tail.setParent(this);
    }

    public PairClouBausteinElementList(ClouBausteinElement head, ClouBausteinElementList tail) {
        this(head.getCodePosition(), head, tail);
    }

    @Override
    public final ClouBausteinElement getHead() {
        return head;
    }

    @Override
    public void replaceTail(ClouBausteinElementList oldTail, ClouBausteinElementList newTail) {
        this.tail = newTail;
        this.tail.setParent(this);
    }

    @Override
    public ClouBausteinElementList getTail() {
        return tail;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithPairClouBausteinElementList(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitPairClouBausteinElementList(this);
        if (getHead().handleProceedWith(visitor)) {
            getHead().accept(visitor);
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
                ", head=" + getHead() +
                ", tail=" + tail +
                '}';
    }
}

package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public class LastClouBausteinElementList extends AbstractClouBausteinElementList implements ClouBausteinElementList {

    private final ClouBausteinElement head;

    public LastClouBausteinElementList(CodePosition codePosition, ClouBausteinElement head) {
        super(codePosition);
        checkNotNull(head);
        this.head = head;
    }

    public LastClouBausteinElementList(ClouBausteinElement head) {
        this(head.getCodePosition(), head);
    }

    @Override
    public final ClouBausteinElement getHead() {
        return head;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithLastClouBausteinElementList(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitLastClouBausteinElementList(this);
        if (getHead().handleProceedWith(visitor)) {
            getHead().accept(visitor);
        }
        visitor.leaveLastClouBausteinElementList(this);
    }

    @Override
    public String toString() {
        return "LastClouBausteinElementList{" +
                "head=" + getHead() +
                '}';
    }
}

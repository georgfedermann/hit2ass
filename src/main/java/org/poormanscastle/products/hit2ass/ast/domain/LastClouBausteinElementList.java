package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public class LastClouBausteinElementList extends AbstractClouBausteinElementList {

    private final ClouBausteinElement head;

    public LastClouBausteinElementList(CodePosition codePosition, ClouBausteinElement head) {
        super(codePosition);
        checkNotNull(head);
        this.head = head;
    }

    public LastClouBausteinElementList(ClouBausteinElement head) {
        this(head.getCodePosition(), head);
    }

    public ClouBausteinElement getHead() {
        return head;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithLastClouBausteinElementList(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitLastClouBausteinElementList(this);
        if (head.handleProceedWith(visitor)) {
            head.accept(visitor);
        }
        visitor.leaveLastClouBausteinElementList(this);
    }

    @Override
    public String toString() {
        return "LastClouBausteinElementList{" +
                "head=" + head +
                '}';
    }
}

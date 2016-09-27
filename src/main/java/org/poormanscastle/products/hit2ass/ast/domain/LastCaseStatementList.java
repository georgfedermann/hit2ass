package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 4/18/16.
 */
public class LastCaseStatementList extends AbstractAstItem implements CaseStatementList {

    private final CaseStatement head;

    public LastCaseStatementList(CodePosition codePosition, CaseStatement head) {
        super(codePosition);
        checkNotNull(head);
        this.head = head;
    }

    public LastCaseStatementList(CaseStatement head) {
        this(head.getCodePosition(), head);
    }

    @Override
    public String getMatch() {
        throw new UnsupportedOperationException("Call this method directly on the CaseStatement, via getHead().getMatch()");
    }

    @Override
    public ClouBausteinElement getClouBausteinElement() {
        return head != null ? head.getClouBausteinElement() : null;
    }

    @Override
    public CaseStatement getHead() {
        return head;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithLastCaseStatementList(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitLastCaseStatementList(this);
        if (head.handleProceedWith(visitor)) {
            head.accept(visitor);
        }
        visitor.leaveLastCaseStatementList(this);
    }

    @Override
    public String toString() {
        return "LastCaseStatementList{" +
                "codePosition=" + getCodePosition() +
                ", head=" + head +
                '}';
    }
}

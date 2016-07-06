package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 4/18/16.
 */
public class PairCaseStatementList extends AbstractAstItem implements CaseStatementList {

    private final CaseStatement head;

    private final CaseStatement tail;

    public PairCaseStatementList(CodePosition codePosition, CaseStatement head, CaseStatement tail) {
        super(codePosition);
        checkNotNull(head);
        checkNotNull(tail);
        this.head = head;
        this.tail = tail;
    }

    public PairCaseStatementList(CaseStatement head, CaseStatement tail) {
        this(head.getCodePosition(), head, tail);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithPairCaseStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitPairCaseStatementList(this);
        if (head.handleProceedWith(visitor)) {
            head.accept(visitor);
        }
        if (tail.handleProceedWith(visitor)) {
            tail.accept(visitor);
        }
        visitor.leavePairCaseStatementList(this);
    }

    @Override
    public String toString() {
        return "PairCaseStatementList{" +
                "codePosition=" + getCodePosition() +
                ", head=" + head +
                ", tail=" + tail +
                '}';
    }
}

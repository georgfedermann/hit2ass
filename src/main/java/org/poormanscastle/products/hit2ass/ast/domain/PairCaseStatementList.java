package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 4/18/16.
 */
public class PairCaseStatementList extends AbstractAstItem implements CaseStatementList {

    private final CaseStatement head;

    private final CaseStatementList tail;

    public PairCaseStatementList(CodePosition codePosition, CaseStatement head, CaseStatementList tail) {
        super(codePosition);
        checkNotNull(head);
        checkNotNull(tail);
        this.head = head;
        this.tail = tail;
    }

    public PairCaseStatementList(CaseStatement head, CaseStatementList tail) {
        this(head.getCodePosition(), head, tail);
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
    public CaseStatementList getTail() {
        return tail;
    }

    @Override
    public String getMatch() {
        throw new UnsupportedOperationException("Call this method directly on the CaseStatement, via getHead().getMatch()");
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

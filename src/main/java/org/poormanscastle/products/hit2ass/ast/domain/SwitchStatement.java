package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This type maps the HIT/CLOU CASE statement #C,
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/15/16.
 */
public class SwitchStatement extends AbstractAstItem implements Statement {

    /**
     * This is expected to be an IdExpression, since HIT/CLOU expects the name
     * of a variable here. The value of the variable will matched with the
     * various cases of this switch statement.
     */
    private final Expression expression;

    private final CaseStatement caseStatement;

    public SwitchStatement(CodePosition codePosition, Expression expression, CaseStatement caseStatement) {
        super(codePosition);
        checkNotNull(expression);
        checkNotNull(caseStatement);
        this.expression = expression;
        this.caseStatement = caseStatement;
    }

    public SwitchStatement(Expression condition, CaseStatement caseStatement) {
        this(condition.getCodePosition(), condition, caseStatement);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithSwitchStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitSwitchStatement(this);
        if (expression.handleProceedWith(visitor)) {
            expression.accept(visitor);
        }
        if (caseStatement.handleProceedWith(visitor)) {
            caseStatement.accept(visitor);
        }
        visitor.leaveSwitchStatement(this);
    }

    @Override
    public String toString() {
        return "SwitchStatement{" +
                "codePosition=" + getCodePosition() +
                ", idExpression=" + expression +
                ", caseStatement=" + caseStatement +
                '}';
    }
}

package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This type maps the HIT/CLOU WHILE statement #S.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/15/16.
 */
public class WhileStatement extends AbstractAstItem implements Statement {

    /**
     * The body of the WhileStatement will be executed as long as the statement
     * expressed by this expression holds.
     */
    private final Expression condition;

    private final ClouBausteinElement whileBody;

    public WhileStatement(CodePosition codePosition, Expression condition, ClouBausteinElement whileBody) {
        super(codePosition);
        checkNotNull(condition);
        checkNotNull(whileBody);
        this.condition = condition;
        this.whileBody = whileBody;
    }

    public WhileStatement(Expression condition, ClouBausteinElementList whileBody) {
        this(condition.getCodePosition(), condition, whileBody);
    }

    public Expression getCondition() {
        return condition;
    }

    public ClouBausteinElement getWhileBody() {
        return whileBody;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithWhileStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitWhileStatement(this);
        if (condition.handleProceedWith(visitor)) {
            condition.accept(visitor);
        }
        if (whileBody.handleProceedWith(visitor)) {
            whileBody.accept(visitor);
        }
        visitor.leaveWhileStatement(this);
    }

    @Override
    public String toString() {
        return "WhileStatement{" +
                "codePosition=" + getCodePosition() +
                ", condition=" + condition +
                ", whileBody=" + whileBody +
                '}';
    }
}

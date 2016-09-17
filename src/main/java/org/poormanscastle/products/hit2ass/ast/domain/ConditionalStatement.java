package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a CLOU IF statement.
 * Created by georg.federmann@poormanscastle.com on 01.04.2016.
 */
public final class ConditionalStatement extends AbstractAstItem implements Statement {

    /**
     * The condition of this conditional statement.
     */
    private final Expression condition;

    /**
     * may be {@code null} if there is no THEN statement in the CLOU component.
     */
    private final ClouBausteinElementList thenElement;
    /**
     * may be {@code null} if there is no ELSE statement in the CLOU component.
     */
    private final ClouBausteinElementList elseElement;

    public ConditionalStatement(CodePosition codePosition, Expression condition, ClouBausteinElementList thenElement, ClouBausteinElementList elseElement) {
        super(codePosition);
        checkNotNull(condition);
        this.condition = condition;
        this.thenElement = thenElement;
        this.elseElement = elseElement;
    }

    public ConditionalStatement(Expression condition, ClouBausteinElementList thenElement, ClouBausteinElementList elseElement) {
        this(condition.getCodePosition(), condition, thenElement, elseElement);
    }

    public Expression getCondition() {
        return condition;
    }

    public ClouBausteinElementList getThenElement() {
        return thenElement;
    }

    public ClouBausteinElementList getElseElement() {
        return elseElement;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithConditionalStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitConditionalStatementStatement(this);
        if (thenElement != null && thenElement.handleProceedWith(visitor)) {
            thenElement.accept(visitor);
        }
        if (condition.handleProceedWith(visitor)) {
            condition.accept(visitor);
        }
        if (elseElement != null && elseElement.handleProceedWith(visitor)) {
            elseElement.accept(visitor);
        }
        visitor.leaveConditionalStatementStatement(this);
    }

    @Override
    public String toString() {
        return "ConditionalStatement{" +
                "codePosition=" + getCodePosition() +
                ", condition=" + condition +
                ", thenElement=" + thenElement +
                ", elseElement=" + elseElement +
                '}';
    }

}

package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents a CLOU IF statement.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 01.04.2016.
 */
public final class ConditionalStatement extends AbstractClouBausteinElementList implements Statement {

    /**
     * The condition of this conditional statement.
     */
    private final Expression condition;

    /**
     * may be {@code null} if there is no THEN statement in the CLOU component.
     */
    private ClouBausteinElementList thenElement;
    /**
     * may be {@code null} if there is no ELSE statement in the CLOU component.
     */
    private ClouBausteinElementList elseElement;

    public ConditionalStatement(CodePosition codePosition, Expression condition, ClouBausteinElementList thenElement, ClouBausteinElementList elseElement) {
        super(codePosition);
        checkNotNull(condition);
        this.condition = condition;
        this.thenElement = thenElement;
        this.elseElement = elseElement;
        if (thenElement != null) {
            thenElement.setParent(this);
        }
        if (elseElement != null) {
            elseElement.setParent(this);
        }
    }

    public ConditionalStatement(Expression condition, ClouBausteinElementList thenElement, ClouBausteinElementList elseElement) {
        this(condition.getCodePosition(), condition, thenElement, elseElement);
    }

    @Override
    public ClouBausteinElement getHead() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClouBausteinElementList getTail() {
        throw new UnsupportedOperationException();
    }

    private int tailToggle = 0;
    @Override
    public void replaceTail(ClouBausteinElementList oldTail, ClouBausteinElementList newTail) {
        // I want the FixedTextMerger to work. Right now I don't see how to merge the ConditionalStatement logic
        // with the ClouBausteinElementList logic. The sub tree wants to register a new child element, be it the
        // THEN branch or the ELSE branch - both call the setTail() method.
        if(oldTail == thenElement){
            thenElement = newTail;
            thenElement.setParent(this);
        } else if(oldTail == elseElement){
            elseElement = newTail;
            elseElement.setParent(this);
        } else {
            throw new IllegalArgumentException(StringUtils.join("Attempt was made to replace unknown conditional branch: ", oldTail));
        }
    }

    public Expression getCondition() {
        return condition;
    }

    public ClouBausteinElement getThenElement() {
        return thenElement;
    }

    public ClouBausteinElement getElseElement() {
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

package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This type represents the HIT / CLOU FOR statement #W.
 * An expression gives the number of repetitions. The body of the FOR loop
 * shall be applied that number of times.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 7/7/16.
 */
public class ForStatement extends AbstractAstItem implements Statement {

    private final Expression repetitionCount;

    private final ClouBausteinElement forBody;

    public ForStatement(CodePosition codePosition, Expression repetitionCount, ClouBausteinElement forBody) {
        super(codePosition);
        checkNotNull(repetitionCount);
        checkNotNull(forBody);
        this.repetitionCount = repetitionCount;
        this.forBody = forBody;
    }

    public ForStatement(Expression repetitionCount, ClouBausteinElement forBody){
        this(repetitionCount.getCodePosition(), repetitionCount, forBody);
    }

    public Expression getRepetitionCount() {
        return repetitionCount;
    }

    public ClouBausteinElement getForBody() {
        return forBody;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithForStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitForStatement(this);
        if (repetitionCount.handleProceedWith(visitor)) {
            repetitionCount.accept(visitor);
        }
        if (forBody.handleProceedWith(visitor)) {
            forBody.accept(visitor);
        }
        visitor.leaveForStatement(this);
    }

    @Override
    public String toString() {
        return "ForStatement{" +
                "repetitionCount=" + repetitionCount +
                ", forBody=" + forBody +
                '}';
    }

}

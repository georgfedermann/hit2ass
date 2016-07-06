package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents the integration of HIT commands into CLOU text components.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 07.04.2016.
 */
public class HitCommandStatement extends AbstractAstItem implements Statement {

    private final HitCommand hitCommand;

    /**
     * this has to resolve to an integer value. E.g. via a NumExpr or an IdExpr
     * that refers to an integer value. Is allowed to be null, in which case it
     * has to be interpreted as the value 1 (one) (this requirement was reverse
     * engineered from the behavior of HIT / CLOU).
     */
    private final Expression repetitor;

    private boolean verstarkt = false;

    public HitCommandStatement(CodePosition codePosition, HitCommand hitCommand, Expression repetitor) {
        super(codePosition);
        checkNotNull(hitCommand);
        this.hitCommand = hitCommand;
        this.repetitor = repetitor;
    }

    /**
     * tell this HitCommandStatement instance that it shall be verstarkt.
     * verstarkt is HIT/CLOU speak and means intensified or boosted. In some
     * cases this may mean bold, or repeatedly (like with applying new lines)
     */
    public void verstarken() {
        verstarkt = true;
    }

    public boolean isVerstarkt() {
        return verstarkt;
    }

    public HitCommand getHitCommand() {
        return hitCommand;
    }

    public Expression getRepetitor() {
        // if the repetitor is null (i.e. no repetitor was defined in the
        // HIT / CLOU Baustein), the default repetitor with value 1 (one)
        // is used.
        return repetitor != null ? repetitor : new NumExpression(CodePosition.createZeroPosition(), 1);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithHitCommandStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitHitCommandStatement(this);
        if (repetitor != null && repetitor.handleProceedWith(visitor)) {
            repetitor.accept(visitor);
        }
        visitor.leaveHitCommandStatement(this);
    }

    @Override
    public String toString() {
        return "HitCommandStatement{" +
                "codePosition=" + getCodePosition() +
                ", hitCommand=" + hitCommand +
                ", repetitor=" + repetitor +
                ", verstarkt=" + verstarkt +
                '}';
    }
}

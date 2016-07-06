package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Positions the cursor in the Zeilenlineal and assigns a tab marker to this position.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/26/16.
 */
public class GStatement extends AbstractAstItem implements Statement {

    private final Expression xpos;

    private final Expression value;

    public GStatement(CodePosition codePosition, Expression xpos, Expression value) {
        super(codePosition);
        this.xpos = xpos;
        this.value = value;
    }

    public Expression getXpos() {
        return xpos;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithGStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitGStatement(this);
        if(value.handleProceedWith(visitor)){
            value.accept(visitor);
        }
        visitor.leaveGStatement(this);
    }

    @Override
    public String toString() {
        return "GStatement{" +
                "codePosition=" + getCodePosition() +
                ", xpos=" + xpos +
                ", value=" + value +
                '}';
    }

}

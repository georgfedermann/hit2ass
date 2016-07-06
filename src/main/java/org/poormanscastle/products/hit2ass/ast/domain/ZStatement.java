package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Remember current cursor positions.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/26/16.
 */
public class ZStatement extends AbstractAstItem implements Statement {

    private final Expression colPos;

    private final Expression rowPos;

    public ZStatement(CodePosition codePosition, Expression colPos, Expression rowPos) {
        super(codePosition);
        this.colPos = colPos;
        this.rowPos = rowPos;
    }

    public Expression getColPos() {
        return colPos;
    }

    public Expression getRowPos() {
        return rowPos;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithZStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitZStatement(this);
        if(colPos.handleProceedWith(visitor)){
            colPos.accept(visitor);
        }
        if(rowPos.handleProceedWith(visitor)){
            rowPos.accept(visitor);
        }
        visitor.leaveZStatement(this);
    }

    @Override
    public String toString() {
        return "ZStatement{" +
                "codePosition=" + getCodePosition() +
                "colPos=" + colPos +
                ", rowPos=" + rowPos +
                '}';
    }
}

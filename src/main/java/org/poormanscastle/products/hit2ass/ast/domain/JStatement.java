package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Positions the cursor com a specified col/row location
 *
 * Created by georg.federmann@poormanscastle.com on 4/26/16.
 */
public class JStatement extends AbstractAstItem implements Statement{

    private final Expression colPos;

    private final Expression rowPos;

    public JStatement(CodePosition codePosition, Expression colPos, Expression rowPos) {
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
        return visitor.proceedWithJStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitJStatement(this);
        if(colPos.handleProceedWith(visitor)){
            colPos.accept(visitor);
        }
        if(rowPos.handleProceedWith(visitor)){
            rowPos.accept(visitor);
        }
        visitor.leaveJStatement(this);
    }

    @Override
    public String toString() {
        return "JStatement{" +
                "codePosition=" + getCodePosition() +
                "colPos=" + colPos +
                ", rowPos=" + rowPos +
                '}';
    }

}

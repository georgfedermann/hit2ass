package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg on 26.09.16.
 */
public class InsertMonth extends AbstractAstItem implements ClouBausteinElement, InsertDate {


    public InsertMonth(CodePosition codePosition) {
        super(codePosition);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithInsertMonth(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitInsertMonth(this);
        visitor.leaveInsertMonth(this);
    }

    @Override
    public String toString() {
        return "InsertMonth{}";
    }
}

package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg on 26.09.16.
 */
public class InsertYear extends AbstractAstItem implements ClouBausteinElement, InsertDate {

    public InsertYear(CodePosition codePosition) {
        super(codePosition);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithInsertYear(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitInsertYear(this);
        visitor.leaveInsertYear(this);
    }

    @Override
    public String toString() {
        return "InsertYear{}";
    }
}

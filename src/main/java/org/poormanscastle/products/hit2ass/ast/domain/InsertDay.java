package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg on 26.09.16.
 */
public class InsertDay extends AbstractAstItem implements ClouBausteinElement, InsertDate {
    
    public InsertDay(CodePosition codePosition){
        super(codePosition);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithInsertDay(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitInsertDay(this);
        visitor.leaveInsertDay(this);
    }

    @Override
    public String toString() {
        return "InsertDay{}";
    }
    
}

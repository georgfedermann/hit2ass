package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg.federmann@poormanscastle.com on 4/21/16.
 */
public class GlobalListDeclarationStatement extends ListDeclarationStatement {

    public GlobalListDeclarationStatement(CodePosition codePosition, String listId, Expression listExpression) {
        super(codePosition, listId, listExpression);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithGlobalListDeclarationStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitGlobalListDeclarationStatement(this);
        if (getListExpression() != null && getListExpression().handleProceedWith(visitor)) {
            getListExpression().accept(visitor);
        }
        visitor.leaveGlobalListDeclarationStatement(this);
    }

    @Override
    public String toString() {
        return "GlobalListDeclarationStatement{" +
                "codePosition=" + getCodePosition() +
                ", listId='" + getListId() + '\'' +
                ", listExpression=" + getListExpression() +
                '}';
    }

}

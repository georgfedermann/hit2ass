package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg.federmann@poormanscastle.com on 4/21/16.
 */
public class LocalListDeclarationStatement extends ListDeclarationStatement {

    public LocalListDeclarationStatement(CodePosition codePosition, String listId, Expression listExpression) {
        super(codePosition, listId, listExpression);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithLocalListDeclarationStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitLocalListDeclarationStatement(this);
        if (getListExpression().handleProceedWith(visitor)) {
            getListExpression().accept(visitor);
        }
        visitor.leaveLocalListDeclarationStatement(this);
    }

    @Override
    public String toString() {
        return "LocalListDeclarationStatement{" +
                "codePosition=" + getCodePosition() +
                "listId='" + getListId() + '\'' +
                ", listExpression=" + getListExpression() +
                '}';
    }

}

package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg.federmann@poormanscastle.com on 12.04.2016.
 */
public class LocalDeclarationStatement extends DeclarationStatement {

    public LocalDeclarationStatement(CodePosition codePosition, String id, Expression expression, String formatDefinition) {
        super(codePosition, id, expression, formatDefinition);
    }

    public LocalDeclarationStatement(String id, Expression expression) {
        super(id, expression);
    }

    public LocalDeclarationStatement(String id, Expression expression, String formatDefinition) {
        super(id, expression, formatDefinition);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithLocalDeclarationStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitLocalDeclarationStatement(this);
        if (getExpression().handleProceedWith(visitor)) {
            getExpression().accept(visitor);
        }
        visitor.leaveLocalDeclarationStatement(this);
    }

    @Override
    public String toString() {
        return "LocalDeclarationStatement{" +
                "codePosition=" + getCodePosition() +
                ", expression=" + getExpression() +
                ", id='" + getId() + '\'' +
                ", formatDefinition='" + getFormatDefinition() + '\'' +
                '}';

    }
}

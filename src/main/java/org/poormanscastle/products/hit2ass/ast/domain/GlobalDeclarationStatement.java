package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg.federmann@poormanscastle.com on 12.04.2016.
 */
public class GlobalDeclarationStatement extends DeclarationStatement {

    public GlobalDeclarationStatement(CodePosition codePosition, String id, Expression expression, String formatDefinition) {
        super(codePosition, id, expression, formatDefinition);
    }

    public GlobalDeclarationStatement(String id, Expression expression) {
        super(id, expression);
    }

    public GlobalDeclarationStatement(String id, Expression expression, String formatDefinition) {
        super(id, expression, formatDefinition);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithGlobalDeclarationStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitGlobalDeclarationStatement(this);
        if (getExpression().handleProceedWith(visitor)) {
            getExpression().accept(visitor);
        }
        visitor.leaveGlobalDeclarationStatement(this);
    }

    @Override
    public String toString() {
        return "GlobalDeclarationStatement{" +
                "codePosition=" + getCodePosition() +
                ", expression=" + getExpression() +
                ", id='" + getId() + '\'' +
                ", formatDefinition='" + getFormatDefinition() + '\'' +
                '}';

    }
}

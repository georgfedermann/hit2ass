package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents HIT / CLOU assignment statement #= as in
 * <p>
 * #= bauname "UE108"
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/15/16.
 */
public class AssignmentStatement extends AbstractAstItem implements Statement {

    /**
     * Name of the symbol (variable) that shall get assigned the given value.
     */
    private final IdExpression idExpression;

    /**
     * The value of this expression shall get assigned to the variable specified by the given id.
     */
    private final Expression expression;

    public AssignmentStatement(CodePosition codePosition, IdExpression idExpression, Expression expression) {
        super(codePosition);
        checkNotNull(idExpression);
        checkNotNull(expression, StringUtils.join("In the assignment statement for symbol ",
                idExpression.toXPathString(), " at ", codePosition, " the expression appears to be null."));
        this.idExpression = idExpression;
        this.expression = expression;
    }

    public IdExpression getIdExpression() {
        return idExpression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithAssignmentStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitAssignmentStatement(this);
        if (expression.handleProceedWith(visitor)) {
            expression.accept(visitor);
        }
        visitor.leaveAssignmentStatement(this);
    }

    @Override
    public String toString() {
        return "AssignmentStatement{" +
                "codePosition=" + getCodePosition() +
                ", idExpression='" + idExpression + '\'' +
                ", expression=" + expression +
                '}';
    }

}

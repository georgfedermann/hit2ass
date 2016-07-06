package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 4/15/16.
 */
public class AssignmentStatement extends AbstractAstItem implements Statement {

    /**
     * Name of the symbol (variable) that shall get assigned the given value.
     */
    private final String id;

    /**
     * The value of this expression shall get assigned to the variable specified by the given id.
     */
    private final Expression expression;

    public AssignmentStatement(CodePosition codePosition, String id, Expression expression) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(id));
        checkNotNull(expression);
        this.id = id;
        this.expression = expression;
    }

    public AssignmentStatement(String id, Expression expression) {
        this(expression.getCodePosition(), id, expression);
    }

    public String getId() {
        return id;
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
        if(expression.handleProceedWith(visitor)){
            expression.accept(visitor);
        }
        visitor.leaveAssignmentStatement(this);
    }

    @Override
    public String toString() {
        return "AssignmentStatement{" +
                "codePosition=" + getCodePosition() +
                ", id='" + id + '\'' +
                ", expression=" + expression +
                '}';
    }

}

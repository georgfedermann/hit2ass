package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The evaluated value of some listExpression shall be appended to a list specified by the given listId.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/26/16.
 */
public class ListConcatenationStatement extends AbstractAstItem implements Statement {

    private final String listId;

    private final Expression listExpression;

    public ListConcatenationStatement(CodePosition codePosition, String listId, Expression listExpression) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(listId));
        checkNotNull(listExpression);
        this.listId = listId;
        this.listExpression = listExpression;
    }

    public ListConcatenationStatement(String listId, Expression listExpression) {
        this(listExpression.getCodePosition(), listId, listExpression);
    }

    public String getListId() {
        return listId;
    }

    public Expression getListExpression() {
        return listExpression;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithListConcatenationStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitListConcatenationStatement(this);
        if(listExpression.handleProceedWith(visitor)){
            listExpression.accept(visitor);
        }
        visitor.leaveListConcatenationStatement(this);
    }

    @Override
    public String toString() {
        return "ListConcatenationStatement{" +
                "codePosition=" + getCodePosition() +
                "listId='" + listId + '\'' +
                ", listExpression=" + listExpression +
                '}';
    }
}

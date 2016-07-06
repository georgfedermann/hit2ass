package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Declares a LIST variable.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/21/16.
 */
public abstract class ListDeclarationStatement extends AbstractAstItem implements Statement {

    private final String listId;

    /**
     * This should be of type list expression. Semantic correctness is protected by
     * semantic visitors of the translator system, not by the parser. So, the parser
     * writes into the field whatever it finds while creating the AST. In later phases,
     * semantic type checkers can freak all out if there are invalid objects found here.
     *
     * Also, this can be null if an empty expression list is given when the given
     * list is declares. As in: #L listVarName D {}
     */
    private final Expression listExpression;

    public ListDeclarationStatement(CodePosition codePosition, String listId, Expression listExpression) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(listId));
        this.listId = listId;
        this.listExpression = listExpression;
    }

    public String getListId() {
        return listId;
    }

    public Expression getListExpression() {
        return listExpression;
    }

}

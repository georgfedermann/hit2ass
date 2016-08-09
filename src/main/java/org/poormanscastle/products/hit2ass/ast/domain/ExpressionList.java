package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Represents a list of expressions that can be used as a parameter list in function calls and in macro calls.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/19/16.
 */
public interface ExpressionList extends Expression {

    Expression getHead();

    ExpressionList getTail();

}

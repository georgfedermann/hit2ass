package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * An expression can be evaluated to a value of various types defined by the given grammar.
 * Created by georg.federmann@poormanscastle.com on 05.04.2016.
 */
public interface Expression<T> extends AstItem {

    /**
     * It's intented to call this method com the 'root' of an expression sub tree
     * within the HIT/CLOU Baustein AST. Recursively, all sub trees within the
     * expression tree will be evaluated by calling their respective node toXPathString()
     * methods down to the leaves and bubbling the results back up to the expression
     * root. The result is a string holding a String representation of an XPath representation
     * of this expression.
     *
     * @return an XPath representation for this expression
     */
    String toXPathString();

    /**
     * Can be used to query the state of the given expression.
     * This state is determined during semantic analysis.
     *
     * @return whether this expression is valid or corrupted.
     */
    ExpressionState getState();

    /**
     * @return the type of this expression's value.
     * For the types BooleanExpression, NumExpression, TextExpression and DecimalExpression,
     * the return value is statically implemented in the expression. For IdExpression, the
     * value must be looked up in the symbol table. For OperatorExpressions, the value is
     * inferred from their sub expressions.
     * correcting above statement (2016-06-28): the target system is a DocDesign workspace.
     * As far as I see all epxressions here will be turned to XPath expressions which will
     * later be evaluated by some DocFamily XSL transformer engines. Thus, we do not really
     * care about the value type here. Also, as values stemming from XPath expressions,
     * all of them will evaluate to XPath sequences, which again will consist of 0 or more
     * entries of some xs types (xml schema types), like xs:string, xs:boolean, etc. But not
     * that we would care here(?)!
     */
    Type getValueType();

    /**
     * I vote for deletion of this method. It does make no sense whatsoever in the context
     * of the HIT/CLOU2ASS transformer.
     * The decision was made against deleting this method because it is used by PrettyPrintVisitor
     * for static Expressions (like NumExpression, TextExpression, etc.)
     *
     * @return the value which the AST interpreter parked here before for later retrieval
     */
    T getValue();

    /**
     * I vote for deletion of this method. It does make no sense whatsoever in the context
     * of the HIT/CLOU2ASS transformer.
     * The decision was made against deleting this method because it is used by PrettyPrintVisitor
     * for static Expressions (like NumExpression, TextExpression, etc.)
     * @param value backup a value for this expression. needed in interpreter mode which is not
     *              available for the hit2ass project.
     */
    void setValue(T value);

}

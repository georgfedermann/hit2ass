package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Operators abstract operations on operands, like arithmetic operators, logical operators, text operators, etc.
 * Operators can be overloaded to operate on different types, like + operates on num, dec and text values.
 * There are binary and unary operators.
 * Operators can return the same value type as their operands, or different ones. like == operates on
 * all data types but always returns boolean values. < operates on int and double and returns boolean.
 * Created by georg.federmann@poormanscastle.com on 23.02.2016.
 */
public interface Operator {

    /**
     * This method is intented to work together with Expression.toXPathString(). When an
     * Expression is asked to serialize itself to an XPath String it may delegate the task
     * to its operator if there is any (as in BinaryOperatorExpression or UnaryOperatorExpression),
     * since the operator has impact on the syntax of the expression.
     *
     * E.g. the unary minus operator just stands before its operand, as in -4, -varName, etc. while
     * the unary not operator is more like a function call as in not(true), not(varName), etc.
     *
     * Other Expressions like NumExpression will be able to solve their task without delegating
     * to any operator.
     * @return an XPath representation of this expression
     * @param operands to which this operator is applied
     */
    public String toXPathString(Expression... operands);

    /**
     * this method delivers the value type of the expression using this operator.
     * the compiler will see to it that the operands will be cast to be of the same type (if
     * compatible) before the operator is executed, thus there is only on input parameter
     * in this method.
     * e.g. called on the Operator == with input type Type.TEXT will return Type.BOOLEAN.
     *
     * @param operandType the type of this operator
     * @return the resulting type of applying the given operator to the given operands
     */
    Type getInferredType(Type operandType);



}

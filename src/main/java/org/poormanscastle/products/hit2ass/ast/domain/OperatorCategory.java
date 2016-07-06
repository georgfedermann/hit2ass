package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * These categories help strcuture operators in groups that can behave differently under certain circumstances.
 * <p/>
 * Imtroduced for the return value types of unary and binary operators: arithmetic operators evaluate to the value
 * type of the operands, relational operators to boolean values, etc.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 23.02.2016.
 */
public enum OperatorCategory {

    POSTFIX, UNARY, MULTIPLICATIVE_ARITHMETIC, ADDITIVE_ARITHMETIC, ADDITIVE_TEXT, RELATIONAL, EQUALITY, BITWISE_AND,
    BITWISE_XOR, BITWISE_OR, LOGICAL_AND, LOGICAL_OR, LOGICAL_XOR, CONDITIONAL, ASSIGNMENT;

}

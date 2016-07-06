package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Enumerates the various states an expression can be in during and
 * after semantic analysis of the AST.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 05.04.2016.
 */
public enum ExpressionState {

    /**
     * operands are compatible, i.e. they have equal types, or their types can
     * automatically be cast to fit by the compiler.
     * e.g. boolean and text, or double and int.
     * <p/>
     * operator and operands are compatible.
     */
    VALID,
    /**
     * the operands are of different types and they cannot be cast to fit. e.g. boolean and int
     */
    OPERANDS_INCOMPATIBLE,
    /**
     * the operator cannot handle the given operand(s). e.g. * and text
     */
    OPERATOR_INCOMPATIBLE,
    /**
     * one or more of the expression's operands are invalid.
     */
    OPERANDS_INVALID,
    /**
     * the id used in an id expression has not been declared beforehand.
     */
    UNDECLARED_ID,
    /**
     * the state of this expression has not been determined yet.
     * The Semantic phase has not been executed (fully), yet.
     */
    NOT_DETERMINED_YET;

}

package org.poormanscastle.products.hit2ass.ast.domain;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.poormanscastle.products.hit2ass.exceptions.HitAssTransformerException;

import com.google.common.base.Preconditions;

/**
 * Created by georg.federmann@poormanscastle.com on 17.02.2016.
 */
public enum BinaryOperator implements Operator {

    PLUS("+", OperatorCategory.ADDITIVE_ARITHMETIC, Type.INT, Type.DOUBLE, Type.TEXT),
    MINUS("-", OperatorCategory.ADDITIVE_ARITHMETIC, Type.INT, Type.DOUBLE),
    TIMES("*", OperatorCategory.MULTIPLICATIVE_ARITHMETIC, Type.INT, Type.DOUBLE),
    DIV("div", OperatorCategory.MULTIPLICATIVE_ARITHMETIC, Type.INT, Type.DOUBLE),
    STRING_CONCAT("&", OperatorCategory.ADDITIVE_TEXT, Type.TEXT) {
        @Override
        public String toXPathString(Expression... operands) {
            Preconditions.checkArgument(operands.length == 2);
            return StringUtils.join("concat( ", operands[0].toXPathString(), ", ", operands[1].toXPathString(), " ) ");
        }
    },
    XOR("xor", OperatorCategory.BITWISE_OR, Type.BOOLEAN),
    AND("and", OperatorCategory.LOGICAL_AND, Type.BOOLEAN),
    OR("or", OperatorCategory.LOGICAL_OR, Type.BOOLEAN),
    LT("<", OperatorCategory.RELATIONAL, Type.INT, Type.DOUBLE),
    LTE("<=", OperatorCategory.RELATIONAL, Type.INT, Type.DOUBLE),
    GT(">", OperatorCategory.RELATIONAL, Type.INT, Type.DOUBLE),
    GTE(">=", OperatorCategory.RELATIONAL, Type.INT, Type.DOUBLE),
    EQ("=", OperatorCategory.EQUALITY, Type.INT, Type.DOUBLE, Type.TEXT, Type.BOOLEAN),
    NEQ("!=", OperatorCategory.EQUALITY, Type.INT, Type.DOUBLE, Type.BOOLEAN, Type.TEXT);

    private String label;

    private List<Type> supportedTypes;

    private OperatorCategory operatorCategory;

    BinaryOperator(String label, OperatorCategory operatorCategory, Type... supportedTypes) {
        this.label = label;
        this.supportedTypes = Arrays.asList(supportedTypes);
        this.operatorCategory = operatorCategory;
    }

    @Override
    public String toXPathString(Expression... operands) {
        Preconditions.checkArgument(operands.length == 2);
        return StringUtils.join(operands[0].toXPathString(), " ", getLabel(), " ", operands[1].toXPathString());
    }

    @Override
    public String toDebugString(Expression... operands) {
        Preconditions.checkArgument(operands.length == 2);
        return StringUtils.join(operands[0].toDebugString(), getDebugLabel(), operands[1].toXPathString());
    }


    public String getLabel() {
        return label;
    }

    String getDebugLabel() {
        return label.replace("<", "lt").replace(">", "gt");
    }

    public boolean supportsType(Type type) {
        return supportedTypes.contains(type);
    }

    @Override
    public Type getInferredType(Type operandType) {
        switch (operatorCategory) {
            case ADDITIVE_ARITHMETIC:
            case MULTIPLICATIVE_ARITHMETIC:
                return operandType;
            case LOGICAL_AND:
            case LOGICAL_OR:
            case LOGICAL_XOR:
            case RELATIONAL:
            case EQUALITY:
                return Type.BOOLEAN;
            case ADDITIVE_TEXT:
                return Type.TEXT;
            default:
                throw new HitAssTransformerException("Unsupported Operator Category.");
        }
    }
}

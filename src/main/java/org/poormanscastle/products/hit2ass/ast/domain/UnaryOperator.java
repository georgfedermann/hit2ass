package org.poormanscastle.products.hit2ass.ast.domain;

import org.poormanscastle.products.hit2ass.exceptions.HitAssTransformerException;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by georg.federmann@poormanscastle.com on 17.02.2016.
 */
public enum UnaryOperator implements Operator {

    NOT("not", OperatorCategory.UNARY, Type.BOOLEAN) {
        @Override
        public String toXPathString(Expression... operands) {
            Preconditions.checkArgument(operands.length == 1);
            return StringUtils.join(getLabel(), "(", operands[0].toXPathString(), ")");
        }
    };

    private String label;

    private List<Type> supportedTypes;

    private OperatorCategory operatorCategory;

    UnaryOperator(String label, OperatorCategory operatorCategory, Type... supportedTypes) {
        this.label = label;
        this.supportedTypes = Arrays.asList(supportedTypes);
        this.operatorCategory = operatorCategory;
    }

    public String getLabel() {
        return label;
    }

    public boolean supportsType(Type type) {
        return supportedTypes.contains(type);
    }


    @Override
    public Type getInferredType(Type operandType) {
        switch (operatorCategory) {
            case UNARY:
                return operandType;
            default:
                throw new HitAssTransformerException("Unsupported Operator Category.");
        }
    }
}

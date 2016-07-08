package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents calls to CLOU specific functions like in listlen("listName").
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/3/16.
 */
public class ClouFunctionCall extends AbstractExpression<Object> {

    final static Logger logger = Logger.getLogger(ClouFunctionCall.class);

    private final String functionName;

    private final ExpressionList args;

    private Object value;

    public ClouFunctionCall(CodePosition codePosition, String functionName, ExpressionList args) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(functionName));
        checkNotNull(args);
        this.functionName = functionName;
        this.args = args;
    }

    @Override
    public String toXPathString() {
        logger.warn(StringUtils.join(
                "Returning dummy value 0 for not yet implemented FunctionCall feature for this function:\n",
                this.toString()));
        return "0";
    }

    public String getFunctionName() {
        return functionName;
    }

    public ExpressionList getArgs() {
        return args;
    }

    @Override
    public Type getValueType() {
        return null;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithClouFunctionCall(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitClouFunctionCall(this);
        if (args.handleProceedWith(visitor)) {
            args.accept(visitor);
        }
        visitor.leaveClouFunctionCall(this);
    }

    @Override
    public String toString() {
        return "ClouFunctionCall{" +
                "codePosition=" + getCodePosition() +
                ", functionName='" + functionName + '\'' +
                ", args=" + args +
                '}';
    }

}

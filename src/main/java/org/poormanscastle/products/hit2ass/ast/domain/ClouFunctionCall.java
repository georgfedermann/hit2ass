package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.hit2ass.exceptions.HitAssTransformerException;
import org.poormanscastle.products.hit2ass.tools.HitAssTools;

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
    public String toDebugString() {
        return StringUtils.join(functionName, "(", args.toDebugString(), ")");
    }

    @Override
    public String toXPathString() {
        // TODO short-cut: implement a set of specific HIT/CLOU functions as needed
        if ("listlen".equals(functionName)) {
            return StringUtils.join(" hit2assext:getListLength(var:read('renderSessionUuid'), ",
                    HitAssTools.getExpressionListAsString(args), ") ");
        } else if ("SHELLVARIABLE".equals(functionName)) {
            return StringUtils.join(" hit2assext:getSystemProperty(", HitAssTools.getExpressionListAsString(args), ") ");
        } else if ("strlen".equals(functionName)) {
            return StringUtils.join(" string-length( ", HitAssTools.getExpressionListAsString(args), ") ");
        } else if ("fdate".equals(functionName)) {
            // straight-forward implementation of the basic 6 cases identified in valid bausteins.
            // if you stumble upon more use cases, it might be wise to implement a more generic approach
            Expression expression1 = getArgs().getHead();
            Expression expression2 = getArgs().getTail().getHead();
            if (expression1 instanceof IdExpression && "today".equals(((IdExpression) expression1).getId())
                    && expression2 instanceof TextExpression) {
                String pattern = ((TextExpression) expression2).getValue();
                if ("0M".equals(pattern)) {
                    return " fn:substring(string(100 + fn:month-from-date(fn:current-date())), 2) ";
                } else if ("0M0T".equals(pattern)) {
                    return " fn:concat(fn:substring(string(100 + fn:month-from-date(fn:current-date())), 2),fn:substring(string(100 + fn:day-from-date(fn:current-date())), 2)) ";
                } else if ("JJ0M0T".equals(pattern)) {
                    return " fn:concat( fn:substring(  fn:year-from-date(fn:current-date()), 3, 2), fn:concat(fn:substring(string(100 + fn:month-from-date(fn:current-date())), 2),fn:substring(string(100 + fn:day-from-date(fn:current-date())), 2))) ";
                } else if ("JJJJ0M0T".equals(pattern)) {
                    return " fn:concat( fn:year-from-date(fn:current-date()), fn:concat(fn:substring(string(100 + fn:month-from-date(fn:current-date())), 2),fn:substring(string(100 + fn:day-from-date(fn:current-date())), 2))) ";
                }
            } else if (expression1 instanceof BinaryOperatorExpression && expression2 instanceof TextExpression) {
                // suspect this is the term where 100 days get added to the current date
                return " fn:substring(fn:year-from-date(fn:current-date() + xs:dayTimeDuration('P100D')), 3, 2) ";
            } else if (expression1 instanceof ClouFunctionCall && "idate".equals(((ClouFunctionCall) expression1).getFunctionName())) {
                // suspect this is where the value of element1 shall get interpreted as date by calling idate(element1, "T.M.JJJJ")
                // in e.wvsvzeitpfl2

                return StringUtils.join(" fn:concat( fn:year-from-date( ", expression1.toXPathString(),
                        " ), fn:concat(fn:substring(string(100 + fn:month-from-date(", expression1.toXPathString(),
                        " )), 2),fn:substring(string(100 + fn:day-from-date( ",
                        expression1.toXPathString(),
                        " )), 2))) ");
            } else {
                String errMsg = StringUtils.join("Invalid Expression ", getArgs().toXPathString(),
                        ", check ClouFunctionCall.toXPathString()");
                logger.error(errMsg);
                throw new HitAssTransformerException(errMsg);
            }
            return "http://www.w3.org/2005/xpath-functions";
        } else if ("idate".equals(functionName)) {
            // assuming dateformat T.M.JJJJ, if more date formats arise, think of a more generic approach.
            String varName = "listelem1";
            // Assuming that a variable is referred to via an ID expression, try to extract the variable name from args:
            if (args.getHead() instanceof IdExpression) {
                varName = ((IdExpression) args.getHead()).getId();
            }
            return " hit2assext:convert_TMJJJJ_DateToIso8601Format(hit2assext:getScalarVariableValue(var:read('renderSessionUuid'), '" + varName + "')) ";
        } else {
            logger.warn(StringUtils.join(
                    "Returning dummy value for not yet implemented FunctionCall feature for this function:\n",
                    this.toString()));
            StringBuilder output = new StringBuilder(StringUtils.join(getFunctionName(), "("));
            ExpressionList expressionList = getArgs();
            boolean firstArg = true;
            while (expressionList != null) {
                if (firstArg) {
                    firstArg = false;
                } else {
                    output.append(", ");
                }
                output.append(expressionList.getHead().toXPathString());
                expressionList = expressionList.getTail();
            }
            output.append(" )");

            return output.toString();
        }
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
        StringBuilder output = new StringBuilder("ClouFunctionCall{" +
                "codePosition=" + getCodePosition() +
                ", functionName='" + functionName + '\'' +
                ", args=(");

        ExpressionList expressionList = getArgs();
        boolean firstArg = true;
        while (expressionList != null) {
            if (firstArg) {
                firstArg = false;
            } else {
                output.append(", ");
            }
            output.append(expressionList.getHead().toXPathString());
            expressionList = expressionList.getTail();
        }
        output.append(" ) }");
        return output.toString();
    }

}

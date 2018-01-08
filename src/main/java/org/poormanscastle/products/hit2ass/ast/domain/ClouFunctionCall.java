package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.hit2ass.exceptions.HitAssTransformerException;
import org.poormanscastle.products.hit2ass.tools.HitAssTools;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

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
    public String toDebugString() {
        return StringUtils.join(functionName, "(", args.toDebugString(), ")");
    }

    @Override
    public String toXPathString() {
        // TODO short-cut: implement a set of specific HIT/CLOU functions as needed
        String failureString = StringUtils.join("ERROR: Unable to resolve ClouFunction ", functionName);
        if ("listlen".equals(functionName)) {
            return StringUtils.join(" hit2assext:getListLength(var:read('renderSessionUuid'), ",
                    HitAssTools.getExpressionListAsString(args), ") ");
        } else if ("SHELLVARIABLE".equals(functionName)) {
            return StringUtils.join(" hit2assext:getSystemProperty(", HitAssTools.getExpressionListAsString(args), ") ");
        } else if ("strlen".equals(functionName)) {
            return StringUtils.join(" string-length( ", HitAssTools.getExpressionListAsString(args), ") ");
        } else if ("fdate".equals(functionName)) {
            try {
                // straight-forward implementation of the basic 6 cases identified in valid bausteins.
                // if you stumble upon more use cases, it might be wise to implement a more generic approach
                Expression expression1 = getArgs().getHead();
                Expression expression2 = getArgs().getTail().getHead();
                failureString = StringUtils.join(failureString, "(", expression1.toDebugString(), ",", expression2.toDebugString().replace("'", "&apos;"), ")");
                if (expression1 instanceof IdExpression && "today".equals(((IdExpression) expression1).getId())
                        && expression2 instanceof TextExpression) {
                    // processing input like this: fdate(today, "0M")
                    String pattern = ((TextExpression) expression2).getValue();

                    return ClouFunctionCall.applyPatternInFdate("fn:current-date()", pattern);
                } else if (expression1 instanceof BinaryOperatorExpression && expression2 instanceof TextExpression) {
                    // example input:
                    // fdate(today+365*24*60*60,"JJ")
                    // fdate(today-365*24*60*60,"JJ")
                    // fdate(today+100*24*60*60,"JJ")
                    // suspect this is the term where 100 days get added to the current date
                    // 2017-11-09 00:01 HIT/CLOU formats years as 4 digits, even though the format string is "JJ"!
                    // return " fn:substring(fn:year-from-date(fn:current-date() + xs:dayTimeDuration('P100D')), 3, 2) "; 
                    // 2017-12-20 there also is the more generel situation: dateValue is something like "2018-12-20" 
                    // fdate(dateValue
                    return applyPatternInFdate(evaluateDateExpressionInFdate((BinaryOperatorExpression) expression1),
                            ((TextExpression) expression2).getValue());
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
            } catch (Exception exception) {
                logger.warn(StringUtils.join("Could not process fdate function call, because: ", failureString));
                return StringUtils.join("'", failureString, "'");
            }
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
                    "Returning dummy value for not yet implemented FunctionCall feature for this function: ",
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

    /**
     * TODO refactor to FdateUtils type (?)
     *
     * @param dateString an XPath expression creates the basic date. like "fn:current-date()" or
     *                   "fn:current-date() + xs:dayTimeDuration('PT31.536.000S')"
     * @param pattern    pattern which shall be applied to the dateString as defined above. Allowed options are
     *                   0M, 0M0T, JJ0M0T, JJ, 0T.0M.JJ
     * @return XPath String that formats the dateString as defined by pattern
     * @throws HitAssTransformerException if the given arguments cannot be processed
     */
    public static String applyPatternInFdate(String dateString, String pattern) throws HitAssTransformerException {
        if ("0M".equals(pattern)) {
            // 0M two digit month, 01 02 03 04 05 06 07 08 09 10 11 12
            // Trick to get two digit months or days:
            // fn:month-from-date returns a value without leading zero. So here, we add the month value
            // to 100 and then (more or less) truncate the leading 1. 
            // e.g. for January: 100 + 1 = 101, last two digits: 01. Success!  
            return StringUtils.join(" fn:substring(string(100 + fn:month-from-date(", dateString, ")), 2) ");
        } else if ("0M0T".equals(pattern)) {
            // 0M0T two digit month and two digit day 0101 1201 1231
            // compare: Trick to get two digit months or days above
            return StringUtils.join(" fn:concat(fn:substring(string(100 + fn:month-from-date(", dateString, ")), 2),",
                    "fn:substring(string(100 + fn:day-from-date(", dateString, ")), 2)) ");
        } else if ("JJ0M0T".equals(pattern) || "JJJJ0M0T".equals(pattern)) {
            // JJ0M0T four digit year, two digit month, two digit day 20171220
            // resulting format is the same as for JJJJ0M0T
            // compare: Trick to get two digit months or days above
            return StringUtils.join(" fn:concat( fn:year-from-date(", dateString, "), ",
                    "fn:concat(fn:substring(string(100 + fn:month-from-date(", dateString, ")), 2),",
                    "fn:substring(string(100 + fn:day-from-date(", dateString, ")), 2))) ");
        } else if ("JJ".equals(pattern)) {
            // JJ four digit year 2017, 2018, 2019
            return StringUtils.join(" fn:year-from-date(", dateString, ") ");
        } else if ("0T.0M.JJ".equals(pattern)) {
            // 0T.0M.JJ two digit day followed by dot followed by 2 digit month followed by dot followed by four digit year 21.12.2017
            return StringUtils.join(" fn:concat( fn:substring(string(100 + fn:day-from-date(", dateString, ")), 2), '.', ",
                    "fn:concat(fn:substring(string(100 + fn:month-from-date(", dateString, ")), 2), '.', ",
                    "fn:year-from-date(", dateString, "))) ");
        } else {
            String errMsg = StringUtils.join("Unsupported date format pattern found: ", pattern,
                    " - please implement strategy in ClouFunctionCall.toXPathString().");
            logger.warn(errMsg);
            throw new HitAssTransformerException(errMsg);
        }
    }

    /**
     * evaluates more complex date expressions relative to the time of processing as can be found in Hit/CLOU components.
     * examples:
     * fdate(today+365*24*60*60,"JJ"), fdate(today-365*24*60*60,"JJ") or fdate(today+100*24*60*60,"JJ").
     * The common factor here is that the first argument to fdate is an expression that starts with today as a
     * reference to the time of processing and than continues to add or subtract a number of seconds to / from the
     * time of processing to result in a date in the past or the future.
     * TODO refactor to FdateUtils type (?)
     *
     * @param expression expects a binary expression where the first operand is an IdExpression with the value
     *                   "today", and the second operand is some arithmetic expression. Examples:
     *                   today+365*24*60*60, today-365*24*60*60, today+100*24*60*60
     * @return a dateString that can e.g. be used as an input for applyPatternInFdate(dateString, pattern). That dateString
     * can be evaluated as an XPath (if the required dependencies are provided)
     * @throws HitAssTransformerException
     */
    public static String evaluateDateExpressionInFdate(BinaryOperatorExpression expression) throws HitAssTransformerException {
        try {
            ScriptEngineManager jsEngineManager = new ScriptEngineManager();
            ScriptEngine jsEngine = jsEngineManager.getEngineByName("JavaScript");
            Integer timeInSeconds = (Integer) jsEngine.eval(expression.getRhs().toDebugString());
            String result = StringUtils.join(" fn:current-date() + xs:dayTimeDuration('",
                    expression.getOperator() == BinaryOperator.MINUS ? "-" : "", "PT", Math.abs(timeInSeconds), "S') ");
            return result;
        } catch (Exception exception) {
            String errMsg = StringUtils.join("Could not evaluateDateExpression because of: ", exception.getClass().getSimpleName(),
                    " - ", exception.getMessage());
            throw new HitAssTransformerException(errMsg, exception);
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

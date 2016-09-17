package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * resolves to the value of a variable identified by an id.
 * Created by georg.federmann@poormanscastle.com on 17.02.2016.
 */
public final class IdExpression extends AbstractExpression<Object> {

    /**
     * this must evaluate to a string value.
     */
    private final String id;

    /**
     * idxExp1 is needed for IdExpressions that have indices as in names[5] and name[1,5].
     * It can be null for idExpressions that refer to scalar variables.
     */
    private final Expression idxExp1;

    /**
     * idExp2 is needed for IdExpressions that refer to string valued vectors, and where
     * a substring will be derived from the first part of the IdExpression, as in
     * names[5][1,5]. It can be null when not needed and must be null if idxExp1 is null.
     */
    private final Expression idxExp2;

    /**
     * the type of this identifier needs to be set in the semantic compiler phase.
     */
    private Type valueType;

    private Object value;

    @Override
    public String toXPathString() {
        if (idxExp1 == null) {
            // this is the HIT/CLOU syntax to evaluate a variable name to the respective value
            // return StringUtils.join(" var:read('", id, "') ");
            return StringUtils.join(" hit2assext:getScalarVariableValue(var:read('renderSessionUuid'), '", id, "')  ");
        } else if (idxExp2 == null && (idxExp1 instanceof LastExpressionList)) {
            // varName[5]
            // this can be one of two things, depending on the data type of the given symbol:
            // the 5th element of a list variable / indexed variable.
            // the 5th character of a string variable.
            // TODO blah[4] could refer to the 4th entry of a list variable or the 4th character of a string variable!
            // TODO implement a basic type of symbol table to track whether a variable is a list or a string
            return StringUtils.join(" hit2assext:getListValueAt(var:read('renderSessionUuid'), '",
                    id, "', ", idxExp1.toXPathString(), ")");
        } else if (idxExp2 == null && (idxExp1 instanceof PairExpressionList)) {
            // this is the HIT/CLOU syntax to extract substrings from string variables.
            // #D myString "Let freedom ring"
            // #D mySubstring myString[5,11]
            // where 5 is the start index and 11 is the end index of the substring within the parent string
            // this needs to be transformed to XPath speak:
            // substring(stringvar, startindex, lengthInCharacter)
            PairExpressionList head = (PairExpressionList) idxExp1;
            checkState(head.getTail() instanceof LastExpressionList);
            LastExpressionList tail = (LastExpressionList) head.getTail();
//            return StringUtils.join(" substring(var:read('", id, "'), ", head.toXPathString(), ", ", tail.toXPathString(),
//                    " + 1 - ", head.toXPathString(), " ) ");
            return StringUtils.join(" substring(hit2assext:getScalarVariableValue(var:read('renderSessionUuid'), '", id, "'), ",
                    head.toXPathString(), ", ", tail.toXPathString(), " + 1 - ", head.toXPathString(), " ) ");
        } else {
            throw new IllegalStateException(StringUtils.join("Cannot create an XPath expression for this IdExpression: ", toString()));
        }
    }

    public IdExpression(CodePosition codePosition, String id, Expression idxExp1, Expression idxExp2) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(id));
        // check idxExp1: must be null or a NumExpression or an ExpressionList with two entries
        checkArgument(idxExp1 == null || idxExp1 instanceof NumExpression || idxExp1 instanceof ExpressionList);
        checkArgument((idxExp1 != null && idxExp2 != null) || idxExp2 == null);
        this.id = id;
        this.idxExp1 = idxExp1;
        this.idxExp2 = idxExp2;
    }

    @Override
    public String toString() {
        return "IdExpression{" +
                "id='" + id + '\'' +
                ", idxExp1=" + idxExp1 +
                ", idxExp2=" + idxExp2 +
                ", valueType=" + valueType +
                ", value=" + value +
                '}';
    }

    public String getId() {
        return id;
    }

    public Expression getIdxExp1() {
        return idxExp1;
    }

    public Expression getIdxExp2() {
        return idxExp2;
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
    public Type getValueType() {
        return valueType;
    }

    public void setValueType(Type valueType) {
        this.valueType = valueType;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithIdExpression(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitIdExpression(this);
        visitor.leaveIdExpression(this);
    }

}

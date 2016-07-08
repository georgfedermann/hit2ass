package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * In CLOU text components, expressions can also be evaluated using values
 * of shell variables of the cli where HIT/CLOU processing was started.
 * TODO I vote for deletion of this type since it's in fact a function call,
 * represented by the type FunctionCall. It may make sense to subclass
 * FunctionCall though to provide function specific behavior.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/28/16.
 *
 * @deprecated
 */
public final class ShellVariableExpression extends AbstractExpression<String> {

    private final String shellVariableId;

    private String value;

    public ShellVariableExpression(CodePosition codePosition, String shellVariableId) {
        super(codePosition);
        this.shellVariableId = shellVariableId.replaceAll("\"", "");
    }

    @Override
    public String toXPathString() {
        return "not implemented!";
    }

    public String getShellVariableId() {
        return shellVariableId;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Type getValueType() {
        return Type.TEXT;
    }

    @Override
    public String toString() {
        return "ShellVariableExpression{" +
                "codePosition=" + getCodePosition() +
                ", shellVariableId='" + shellVariableId + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithShellVariableExpression(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitShellVariableExpression(this);
        visitor.leaveShellVariableExpression(this);
    }

}

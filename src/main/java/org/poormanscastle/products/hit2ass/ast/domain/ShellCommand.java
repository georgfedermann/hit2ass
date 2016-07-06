package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg.federmann@poormanscastle.com on 4/29/16.
 */
public class ShellCommand extends AbstractAstItem implements Statement {

    /**
     * This contains some command string which shall be executed on the shell.
     * <p>
     * If the commando starts with a $ character, than the value of this field
     * is the name of a variable which needs to be evaluated to retrieve the
     * actual command that shall be executed on the cli.
     */
    private final String commando;

    public ShellCommand(CodePosition codePosition, String commando) {
        super(codePosition);
        this.commando = commando;
    }

    public String getCommando() {
        return commando;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithShellCommand(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitShellCommand(this);
        visitor.leaveShellCommand(this);
    }

    @Override
    public String toString() {
        return "ShellCommand{" +
                "codePosition=" + getCodePosition() +
                ", commando='" + commando + '\'' +
                '}';
    }

}

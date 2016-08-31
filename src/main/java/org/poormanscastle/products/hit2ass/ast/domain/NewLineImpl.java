package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg on 26.08.16.
 */
final class NewLineImpl extends AbstractAstItem implements NewLine {

    public NewLineImpl(CodePosition codePosition) {
        super(codePosition);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithNewLine(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitNewLine(this);
        visitor.leaveNewLine(this);
    }

    @Override
    public String toString() {
        return "NewLineImpl{" +
                "codePosition=" + getCodePosition() +
                "}";
    }
}

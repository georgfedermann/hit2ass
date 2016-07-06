package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg.federmann@poormanscastle.com on 5/3/16.
 */
public class WriteStatement extends AbstractAstItem implements Statement {

    private final String fileName;

    public WriteStatement(CodePosition codePosition, String fileName) {
        super(codePosition);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithWriteStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitWriteStatement(this);

        visitor.leaveWriteStatement(this);
    }

    @Override
    public String toString() {
        return "WriteStatement{" +
                "codePosition=" + getCodePosition() +
                ", fileName='" + fileName + '\'' +
                '}';
    }

}

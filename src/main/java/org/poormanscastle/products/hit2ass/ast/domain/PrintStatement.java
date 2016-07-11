package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * CLOU Comman: #X<
 * A PrintStatement inserts the value of a given symbol com the current cursor position
 * within the document. Sample code from an actual HIT/CLOU text component:
 * <p>
 * Wir teilen Ihnen mit, dass Ihre Zahlung
 * von #> W #> zahlung
 * am #> eingdat bei uns eingelangt ist.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/28/16.
 */
public final class PrintStatement extends AbstractAstItem implements Statement {

    private final String symbolId;

    public PrintStatement(CodePosition codePosition, String symbolId) {
        super(codePosition);
        this.symbolId = symbolId;
    }

    public String getSymbolId() {
        return symbolId;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithPrintStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitPrintStatement(this);

        visitor.leavePrintStatement(this);
    }

    @Override
    public String toString() {
        return "PrintStatement{" +
                "codePosition=" + getCodePosition() +
                ", symbolId='" + symbolId + '\'' +
                '}';
    }
}

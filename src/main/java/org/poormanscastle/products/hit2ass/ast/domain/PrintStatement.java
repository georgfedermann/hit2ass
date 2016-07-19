package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * CLOU Comman: #X<
 * A PrintStatement inserts the value of a given symbol com the current cursor position
 * within the document. Nota bene: only printing of variables is supported, not printing
 * of generic expressions. This might be a limitation resulting from the not very structured
 * approach chosen in the implementation of the HIT/CLOU templating language.
 * <p>
 * Sample code from an actual HIT/CLOU text component:
 * <p>
 * Wir teilen Ihnen mit, dass Ihre Zahlung
 * von #> W #> zahlung
 * am #> eingdat bei uns eingelangt ist.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/28/16.
 */
public final class PrintStatement extends AbstractAstItem implements Statement {

    /**
     * sinse HIT/CLOU PrintStatements only support id expressions, this must be of type IdExpression.
     */
    private final IdExpression idExpression;

    public PrintStatement(CodePosition codePosition, IdExpression idExpression) {
        super(codePosition);
        checkNotNull(idExpression);
        this.idExpression = (IdExpression) idExpression;
    }

    public IdExpression getIdExpression() {
        return idExpression;
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
                ", idExpression='" + idExpression + '\'' +
                '}';
    }
}

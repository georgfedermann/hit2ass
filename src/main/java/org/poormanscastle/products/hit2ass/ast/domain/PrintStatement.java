package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkArgument;
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
     * since HIT/CLOU PrintStatements only support id expressions and standard function calls,
     * this must be either of type IdExpression or of type ClouFunctionCall.
     */
    private final Expression expression;

    public PrintStatement(CodePosition codePosition, Expression expression) {
        super(codePosition);
        checkNotNull(expression);
        checkArgument(expression instanceof IdExpression || expression instanceof ClouFunctionCall);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
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
                ", expression='" + expression + '\'' +
                '}';
    }
}

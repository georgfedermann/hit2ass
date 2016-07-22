package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by georg.federmann@poormanscastle.com on 4/19/16.
 */
public class MacroCallStatement extends AbstractAstItem implements Statement {

    private final String macroId;

    /**
     * Can be null if the given macro does not accept parameters.
     */
    private final ExpressionList argumentList;

    public MacroCallStatement(CodePosition codePosition, String macroId, ExpressionList argumentList) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(macroId));
        this.macroId = macroId;
        this.argumentList = argumentList;
    }

    public MacroCallStatement(String macroId, ExpressionList argumentList) {
        this(argumentList.getCodePosition(), macroId, argumentList);
    }

    public String getMacroId() {
        return macroId;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithMacroCallStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitMacroCallStatement(this);
        if (argumentList != null && argumentList.handleProceedWith(visitor)) {
            argumentList.accept(visitor);
        }
        visitor.leaveMacroCallStatement(this);
    }

    @Override
    public String toString() {
        return "MacroCallStatement{" +
                "macroId='" + macroId + '\'' +
                ", argumentList=" + argumentList +
                '}';
    }

}

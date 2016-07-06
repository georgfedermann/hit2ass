package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by georg.federmann@poormanscastle.com on 4/19/16.
 */
public class MacroDefinitionStatement extends AbstractAstItem implements Statement {

    /**
     * The name of this marco; by which this macro can be called and executed.
     */
    private final String macroId;

    /**
     * The macroDefinition is copied from the HIT/CLOU text component and
     * stored here for later processing. Probably, this value will be parsed
     * and stored in some structured way like an AST, com the time it gets
     * clear that the macro is actually used in the given document.
     */
    private final String macroDefinition;

    public MacroDefinitionStatement(CodePosition codePosition, String macroId, String macroDefinition) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(macroId));
        checkArgument(!StringUtils.isBlank(macroDefinition));
        this.macroId = macroId;
        this.macroDefinition = macroDefinition;
    }

    public String getMacroDefinition() {
        return macroDefinition;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithMacroDefinitionStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitMacroDefinitionStatement(this);
        visitor.leaveMacroDefinitionStatement(this);
    }
}

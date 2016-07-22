package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Represents a HIT/CLOU Absatzmarke, delimiting a section in the text flow.
 * <p>
 * This is similar to a line break or a new line character.
 * <p>
 * In the pre-processed HIT/CLOU text components, this control is represented by the "@" character.
 * The text componentes are preprocessed by the statement:
 * <p>
 * procon -p -TK 40 ClouBausteinFile
 * <p>
 * Created by georg on 7/11/16.
 */
public class SectionStatement extends AbstractAstItem implements Statement {

    public SectionStatement(CodePosition codePosition) {
        super(codePosition);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithSectionStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitSectionStatement(this);
        visitor.leaveSectionStatement(this);
    }

    @Override
    public String toString() {
        return "SectionStatement{}";
    }
}

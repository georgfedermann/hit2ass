package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * CLOU Command: n.n. just text within the CLOU Baustein that is not a comment.
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
final class FixedTextImpl extends AbstractAstItem implements FixedText {

    private final StringBuilder textBuffer;

    FixedTextImpl(CodePosition codePosition, String text) {
        super(codePosition);
        checkNotNull(text);
        textBuffer = new StringBuilder();
        textBuffer.append(text);
    }

    @Override
    public void appendText(String text, boolean useBlank) {
        textBuffer.append(useBlank ? StringUtils.join(" ", text) : text);
    }

    @Override
    public String getText() {
        return textBuffer.toString();
    }

    @Override
    public void reset() {
        textBuffer.setLength(0);
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithFixedText(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitFixedText(this);
        visitor.leaveFixedText(this);
    }

    @Override
    public String toString() {
        return "FixedTextImpl{" +
                "codePosition=" + getCodePosition() +
                ", textBuffer=" + textBuffer +
                '}';
    }
}

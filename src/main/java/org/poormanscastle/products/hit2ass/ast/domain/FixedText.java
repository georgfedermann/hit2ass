package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * This type represents fixed text in HIT/CLOU document templates.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public interface FixedText extends ClouBausteinElement {

    static FixedText create(CodePosition codePosition, String text) {
        return new FixedTextImpl(codePosition, text);
    }

    void appendText(String text, boolean useBlank);

    String getText();

    void reset();
}

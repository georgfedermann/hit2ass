package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public abstract class AbstractAstItem implements AstItem {

    private final CodePosition codePosition;

    public AbstractAstItem(CodePosition codePosition) {
        checkNotNull(codePosition);
        this.codePosition = codePosition;
    }

    @Override
    public CodePosition getCodePosition() {
        return codePosition;
    }

    @Override
    public String toString() {
        return "AbstractAstItem{" +
                "codePosition=" + codePosition +
                '}';
    }
}

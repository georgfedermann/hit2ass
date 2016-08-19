package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by georg on 19.08.16.
 */
public abstract class AbstractClouBausteinElementList extends AbstractAstItem implements ClouBausteinElementList {

    private ClouBausteinElementList parent;

    public AbstractClouBausteinElementList(CodePosition codePosition) {
        super(codePosition);
    }

    @Override
    public ClouBausteinElementList getParent() {
        return parent;
    }

    public void setParent(ClouBausteinElementList parent) {
        this.parent = parent;
    }
}

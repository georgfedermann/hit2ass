package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg on 26.08.16.
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

    @Override
    public void setParent(ClouBausteinElementList parent) {
        this.parent = parent;
    }

}

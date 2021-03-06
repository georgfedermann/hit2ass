package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Gets the tree aspect into ClouBaustein lists, rather than flat lists which
 * would be achieved by using standard lists here, which would largely kill
 * the visitor pattern.
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public interface ClouBausteinElementList extends ClouBausteinElement {

    ClouBausteinElementList getParent();

    void setParent(ClouBausteinElementList parent);

    ClouBausteinElement getHead();

    default ClouBausteinElementList getTail() {
        return null;
    }

}

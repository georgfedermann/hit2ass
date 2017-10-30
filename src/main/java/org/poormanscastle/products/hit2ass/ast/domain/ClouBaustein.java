package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * a Clou Baustein is the content of a file that starts with a single # in the first line,
 * followed only by a new line character.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public interface ClouBaustein extends AstItem {

    ClouBausteinElement getClouBausteinElement();

    void setClouBausteinName(String name);

    String getClouBausteinName();

}

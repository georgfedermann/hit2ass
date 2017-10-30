package org.poormanscastle.products.hit2ass.renderer.domain;

/**
 * The IRTransformer can store in its state what font weight is applicable for DocFamily workspace elements.
 * Created by georg on 7/9/16.
 */
public enum FontWeight {

    BOLD("bold"), INHERIT("inherit");

    private String value;

    private FontWeight(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

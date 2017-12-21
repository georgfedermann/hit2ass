package org.poormanscastle.products.hit2ass.renderer.domain;

/**
 * Created by georg on 07/12/2017.
 */
public enum TextDecoration {

    UNDERLINED("underline"), INHERIT("inherit");

    private String value;

    private TextDecoration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

package org.poormanscastle.products.hit2ass.renderer.domain;

/**
 * This enumerates the available text alignments like left, right, justified, centered.
 * Created by georg on 8/11/16.
 */
public enum TextAlignment {

    LEFT("start"), RIGHT("end"), JUSTIFIED("justify"), CENTER("center");

    private String value;

    private TextAlignment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

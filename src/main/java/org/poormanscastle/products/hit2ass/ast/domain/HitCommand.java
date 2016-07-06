package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Represents the integration of HIT commands into CLOU text components.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 07.04.2016.
 */
public enum HitCommand {

    A_OBEN("A_OBEN"), A_UNTEN("A_UNTEN"), ABS_FORMATIEREN("ABS_FORMATIEREN"), C_LINKS("C_LINKS"),
    C_OBEN("C_OBEN"), RETURN("RETURN"), T_LINKS("T_LINKS"), T_RECHTS("T_RECHTS"), VERSTAERKER("VERSTÄRKER"),
    W_LOESCHEN("W_LÖSCHEN"), W_RECHTS("W_RECHTS"), ZL_NEU("ZL_NEU");

    private final String label;

    HitCommand(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}

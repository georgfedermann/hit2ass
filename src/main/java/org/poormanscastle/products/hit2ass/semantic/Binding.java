package org.poormanscastle.products.hit2ass.semantic;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * In a SymbolTable, each variable name and formal-parameter name are bound to their metadata.
 * In the case of HIT/CLOU textcomponents, those are the formatting of the respective variables,
 * since HIT/CLOU does not support typed variables.
 * <p/>
 * Each function is bound to its parameters, its result type and its local variables.
 * <p/>
 * Each class name is bound to its variable declarations and its method declarations.
 * <p/>
 * The Binding type is capable of storing all of this information.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 08.04.2016.
 */
public class Binding {

    /**
     * HIT/CLOU symbols have a specified format. This is optional an may be null;
     */
    private String format;

    /**
     * com this stage also store a value here, so they are available to the transformer
     * when the HIT/CLOU text components get transformed to DocFamily workspaces. This
     * is not optional, HIT/CLOU requires all declared variables to be initialized on
     * declaration.
     */
    private Object value;
    public Binding(String format, Object value) {
        checkNotNull(value);
        this.format = format;
        this.value = value;
    }

    public Binding(Object value) {
        this("", value);
    }

    @Override
    public String toString() {
        return StringUtils.join("Binding: specified format: ", format, "; value: ", value);
    }

    public String getFormat() {
        return format;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

package org.poormanscastle.products.hit2ass.semantic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * This is a language symbol which is managed in a symbol table.
 * A symbol is any identifier, like the names of variables, names of makros or names of functions.
 * requirements into the symbol implementation: comparing symbols for equality has to be fast.
 * Calculating an int hash key for a symbol has to be fast. Comparing 2 symbols for "greater than"
 * has to be fast.
 * Created by georg.federmann@poormanscastle.com on 08.04.2016.
 */
public class Symbol {

    /**
     * The symbol's name.
     */
    private final String name;

    private final static Map<String, Symbol> symbols = new HashMap<>();

    private Symbol(String name) {
        this.name = name.intern();
    }

    /**
     * Delivers the symbol for the given id. If there exists no such symbol yet,
     * a new one is created for the given id.
     *
     * @param name the name of the symbol.
     * @return The managed symbol for the given name.
     */
    public static Symbol getSymbol(String name) {
        String u = name.intern();
        Symbol result = symbols.get(u);
        if (result == null) {
            result = new Symbol(u);
            symbols.put(u, result);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Symbol symbol = (Symbol) obj;
        return Objects.equals(name, symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}

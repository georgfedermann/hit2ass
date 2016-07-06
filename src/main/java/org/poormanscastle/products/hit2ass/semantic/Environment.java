package org.poormanscastle.products.hit2ass.semantic;

import org.poormanscastle.products.hit2ass.exceptions.SymbolAlreadyDeclaredException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * An environment is a collection of symbols that have been declared and are known
 * in the current scope. Environments can be nested and thus symbols can be
 * inherited to nested environments. Environments can also be referenced as is the
 * case with e.g. static class members: T.b the field b in class T. So T is kind
 * of environment here.
 * <p/>
 * New environments are created when new scopes are added, like blocks of a conditional
 * statement. When a scope goes out of scope (end of a function, end of a block), the
 * whole environment with all its registered symbols (variables) gets removed.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 08.04.2016.
 */
public class Environment {

    private final Map<Symbol, Binding> bindings = new HashMap<>();

    public Binding addSymbol(String name, String format, Object value) {
        Symbol symbol = Symbol.getSymbol(name);
        if (bindings.containsKey(symbol)) {
            throw new SymbolAlreadyDeclaredException(StringUtils.join("Symbol alreadey in Symboltable: ", symbol));
        }
        Binding binding = new Binding(format, value);
        bindings.put(symbol, binding);
        return binding;
    }

    public Binding addSymbol(String name, Object value) {
        return addSymbol(name, null, value);
    }

    public boolean contains(Symbol symbol) {
        return bindings.containsKey(symbol);
    }

    public Binding getSymbol(Symbol symbol) {
        return bindings.get(symbol);
    }

    public int getSize() {
        return bindings.size();
    }
}

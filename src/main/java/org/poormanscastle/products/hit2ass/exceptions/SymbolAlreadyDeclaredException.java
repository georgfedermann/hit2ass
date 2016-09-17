package org.poormanscastle.products.hit2ass.exceptions;

/**
 * This exception type shall be used to indicate that a symbol was already declared
 * in a given scope and taht the current action cannot complete.
 * Created by georg.federmann@poormanscastle.com on 08.04.2016.
 */
public class SymbolAlreadyDeclaredException extends HitAssTransformerException {

    public SymbolAlreadyDeclaredException() {
    }

    public SymbolAlreadyDeclaredException(Throwable cause) {
        super(cause);
    }

    public SymbolAlreadyDeclaredException(String message) {
        super(message);
    }

    public SymbolAlreadyDeclaredException(String message, Throwable cause) {
        super(message, cause);
    }

    public SymbolAlreadyDeclaredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

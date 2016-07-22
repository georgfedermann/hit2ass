package org.poormanscastle.products.hit2ass.exceptions;

/**
 * Shall be throws to indicate exceptions while parsing CLOU text components,
 * i.e. typically situations where CLOU syntax is violated.
 * Created by georg on 7/20/16.
 */
public class ClouParserException extends HitAssTransformerException {

    public ClouParserException() {
    }

    public ClouParserException(Throwable cause) {
        super(cause);
    }

    public ClouParserException(String message) {
        super(message);
    }

    public ClouParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClouParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

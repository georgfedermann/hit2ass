package org.poormanscastle.products.hit2ass.exceptions;

/**
 * This exception shall be sued to indicate errors that occurred during
 * any of the phase of compiling.
 * Created by georg.federmann@poormanscastle.com on 05.04.2016.
 */
public class HitAssTransformerException extends RuntimeException {

    public HitAssTransformerException() {
    }

    public HitAssTransformerException(Throwable cause) {
        super(cause);
    }

    public HitAssTransformerException(String message) {
        super(message);
    }

    public HitAssTransformerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HitAssTransformerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

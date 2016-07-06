package org.poormanscastle.products.hit2ass.exceptions;

/**
 * This exception indicates problems when resolving logical text component names
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/27/16.
 */
public class ClouBausteinIoException extends HitAssTransformerException {

    public ClouBausteinIoException() {
    }

    public ClouBausteinIoException(Throwable cause) {
        super(cause);
    }

    public ClouBausteinIoException(String message) {
        super(message);
    }

    public ClouBausteinIoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClouBausteinIoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

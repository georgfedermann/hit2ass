package org.poormanscastle.products.hit2ass.exceptions;

/**
 * Shall be thrown to indicate exceptional conditions when parsing and merging Bausteine.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/27/16.
 */
public class BausteinMergerException extends HitAssTransformerException {
    public BausteinMergerException() {
    }

    public BausteinMergerException(Throwable cause) {
        super(cause);
    }

    public BausteinMergerException(String message) {
        super(message);
    }

    public BausteinMergerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BausteinMergerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

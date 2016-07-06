package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import org.poormanscastle.products.hit2ass.exceptions.HitAssTransformerException;

/**
 * This exception shall be used to indicate exceptional conditions
 * during creation of DocFamily data XMLs from input data text files
 * using XML templates.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/11/16.
 */
public class DataXmlMergerException extends HitAssTransformerException {

    public DataXmlMergerException() {
    }

    public DataXmlMergerException(Throwable cause) {
        super(cause);
    }

    public DataXmlMergerException(String message) {
        super(message);
    }

    public DataXmlMergerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataXmlMergerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

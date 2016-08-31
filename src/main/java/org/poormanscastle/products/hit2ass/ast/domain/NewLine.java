package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * This is a marker for a Newline found in the CLOU Baustein while the
 * parser was in TXT_MODE. FixedText found directly before a NewLine will
 * be trimmed at the end, FixedText found directly after a NewLine will
 * be trimmed at the beginning.
 * <p>
 * Created by georg on 26.08.16.
 */
public interface NewLine extends ClouBausteinElement {

    static NewLine create(CodePosition codePosition){
        return new NewLineImpl(codePosition);
    }

}

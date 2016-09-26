package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg on 26.09.16.
 */
public interface InsertDate {

    static InsertDay insertDay(CodePosition codePosition) {
        return new InsertDay(codePosition);
    }

    static InsertMonth insertMonth(CodePosition codePosition) {
        return new InsertMonth(codePosition);
    }

    static InsertYear insertYear(CodePosition codePosition) {
        return new InsertYear(codePosition);
    }

}

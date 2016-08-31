package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Introduces the tree hierarchy aspect into a sequence of Case Statements within a switch structure.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/18/16.
 */
public interface CaseStatementList extends CaseStatement {

    CaseStatement getHead();

    default CaseStatementList getTail() {
        return null;
    }

}

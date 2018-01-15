package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.poormanscastle.products.hit2ass.ast.domain.Expression;
import org.poormanscastle.products.hit2ass.renderer.domain.Content;

/**
 * this type is used to introduce a repetition of rows within a table.
 * This implements the logic often found in HIT/ClOU Bausteins:
 *
 * Table preamble
 * #^ "ZL NEU"
 * #G 9 1
 * #G 15 2
 * #G 30 4
 * #G 88 8
 *
 * #W loopCounter
 *   Some text #> variable1 #$TAB #> variable2 #$TAB
 *
 * finish table
 * #^ "ZL NEU"
 * #G 9 1
 * #G 88 8
 *
 */
public interface TableRowRepetition extends Content {

    static TableRowRepetition createTableRowRepetition(Expression repetitionExpression){
        return new TableRowRepetitionImpl(repetitionExpression);
    }

}

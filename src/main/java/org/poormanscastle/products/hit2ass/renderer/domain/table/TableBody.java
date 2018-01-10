package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.poormanscastle.products.hit2ass.renderer.domain.Content;

public interface TableBody extends Content {

    /**
     * add a new row to the table body. This will be called if another cell shall be added to a table
     * when the current row already holds as many cells as the table has columns.
     * @param tableRow
     */
    void addTableRow(TableRow tableRow);

    /**
     *
     * @return the current row, i.e. during rendering this is the row to which cells should get
     * added. null if the table body is empty.
     */
    TableRow getCurrentRow();

    static TableBody createTableBody(){
        return new TableBodyImpl();
    }

}

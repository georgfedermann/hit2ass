package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.poormanscastle.products.hit2ass.renderer.domain.Content;

public interface TableRow extends Content {

    void addTableCell(TableCell tableCell);

    /**
     * get the current table cell
     * @return the table cell of null if this TableRow is still empty.
     */
    TableCell getCurrentTableCell();

    /**
     * @return an integer representing the number of cells contained in this row.
     */
    int getSize();

    static TableRow createTableRow() {
        return new TableRowImpl();
    }

}

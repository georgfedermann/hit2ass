package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.poormanscastle.products.hit2ass.renderer.domain.Content;

public interface TableRow extends Content {

    void addTableCell(TableCell tableCell);

    /**
     * get the current table cell
     *
     * @return the table cell of null if this TableRow is still empty.
     */
    TableCell getCurrentTableCell();

    void setTableRowRepetition(TableRowRepetition tableRowRepetition);

    /**
     * @return an integer representing the number of cells contained in this row.
     */
    int getSize();

    /**
     * @param tableRowRepetition can be null. if not null, it will be added to this table row instance.
     * @return
     */
    static TableRow createTableRow(TableRowRepetition tableRowRepetition) {
        TableRow tableRow = new TableRowImpl();
        tableRow.setTableRowRepetition(tableRowRepetition);
        return tableRow;
    }

}

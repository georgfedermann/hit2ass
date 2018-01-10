package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.poormanscastle.products.hit2ass.renderer.domain.Content;

public interface TableRow extends Content {

    void addTableCell(TableCell tableCell);

}

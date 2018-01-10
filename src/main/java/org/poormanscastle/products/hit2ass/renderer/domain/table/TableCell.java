package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.poormanscastle.products.hit2ass.renderer.domain.Container;

public interface TableCell extends Container {

    static TableCell createTableCell(){
        return new TableCellImpl();
    }

}

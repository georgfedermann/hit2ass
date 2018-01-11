package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.apache.velocity.VelocityContext;
import org.poormanscastle.products.hit2ass.renderer.domain.AbstractContainer;

public class TableCellImpl extends AbstractContainer implements TableCell {

    TableCellImpl() {
        super("TableCell", "/velocity/tables/TemplateCell.vlt");
    }

    @Override
    public void setupContext(VelocityContext context) {
        // nothing to do here.
    }

}

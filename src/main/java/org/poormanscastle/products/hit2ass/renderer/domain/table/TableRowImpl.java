package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

public class TableRowImpl implements TableRow {

    private final List<TableCell> tableCells = new LinkedList<>();
    private TableRowRepetition tableRowRepetition;

    TableRowImpl() {
    }

    @Override
    public void setTableRowRepetition(TableRowRepetition tableRowRepetition) {
        this.tableRowRepetition = tableRowRepetition;
    }

    @Override
    public void addTableCell(TableCell tableCell) {
        tableCells.add(tableCell);
    }

    @Override
    public TableCell getCurrentTableCell() {
        return tableCells.size() == 0 ? null : tableCells.get(tableCells.size() - 1);
    }

    @Override
    public int getSize() {
        return tableCells.size();
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        StringBuilder tableCellsContent = new StringBuilder();
        tableCells.stream().forEach(tableCell -> tableCellsContent.append(tableCell.getContent()));
        context.put("tableCells", tableCellsContent.toString());
        context.put("tableRowRepetition",
                tableRowRepetition == null ? "" : tableRowRepetition.getContent());
        Template template = Velocity.getTemplate("/velocity/tables/TemplateRow.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    @Override
    public String toString() {
        return "TableRowImpl{" +
                "tableCells=" + tableCells +
                '}';
    }

}

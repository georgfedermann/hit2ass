package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;
import org.poormanscastle.products.hit2ass.renderer.domain.Content;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class TableImpl implements Table {

    private final List<TableColumn> tableColumns = new LinkedList<>();
    private final TableBody tableBody;

    TableImpl() {
        tableBody = TableBody.createTableBody();
    }

    @Override
    public int getColumnCount() {
        return tableColumns.size();
    }

    @Override
    public void addTableColumn(TableColumn column) {
        checkState(tableBody.getCurrentRow() == null,
                "No columns can be added after content has been added.");
        tableColumns.add(column);
    }

    @Override
    public void addContent(Content content) {
        checkState(tableColumns.size() > 0,
                "Table must be initialized with columns before content can be added to it.");
        TableRow currentRow = tableBody.getCurrentRow();
        if (currentRow == null) {
            tableBody.addTableRow(currentRow = TableRow.createTableRow());
        }
        if (currentRow.getSize() >= getColumnCount()) {
            tableBody.addTableRow(currentRow = TableRow.createTableRow());
        }
        TableCell tableCell = TableCell.createTableCell();
        tableCell.addContent(content);
        currentRow.addTableCell(tableCell);
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        StringBuilder columnsContent = new StringBuilder();
        tableColumns.stream().forEach(column -> columnsContent.append(column.getContent()));
        context.put("columns", columnsContent.toString());
        context.put("tableBody", tableBody.getContent());

        Template template = Velocity.getTemplate("/velocity/tables/TemplateTable.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

}

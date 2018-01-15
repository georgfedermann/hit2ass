package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.apache.log4j.Logger;
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

    private final static Logger logger = Logger.getLogger(TableImpl.class);

    private final List<TableColumn> tableColumns = new LinkedList<>();
    private final TableBody tableBody;

    /**
     * when a TableRowRepetition is found, it shall get added to the next row which is created
     * by the table builder and then be discarded. This is a parking place for the repetition
     * until the next row gets created.
     */
    private TableRowRepetition tableRowRepetition;

    TableImpl() {
        logger.info("Creating a new table instance");
        tableBody = TableBody.createTableBody();
    }

    @Override
    public void setTableRowRepetition(TableRowRepetition tableRowRepetition) {
        this.tableRowRepetition = tableRowRepetition;
    }

    @Override
    public int getColumnCount() {
        return tableColumns.size();
    }

    @Override
    public void addTableColumn(int width) {
        checkState(tableBody.getCurrentRow() == null,
                "No columns can be added after content has been added.");
        tableColumns.add(TableColumn.createTableColumnWithSpecifiedWidthinCharacters(width));
    }

    @Override
    public void addContent(Content content) {
        checkState(tableColumns.size() > 0,
                "Table must be initialized with columns before content can be added to it.");
        TableRow currentRow = tableBody.getCurrentRow();
        if (currentRow == null) {
            tableBody.addTableRow(currentRow = TableRow.createTableRow(tableRowRepetition));
            tableRowRepetition = null;
        } else if (tableRowRepetition != null){
            // the situation should be: within a table, a for loop was declared. the for loop declaration is
            // hanging within the first table cell(s), but the repetition was not yet assigned to the row.
            // so add it here and now
            currentRow.setTableRowRepetition(tableRowRepetition);
            tableRowRepetition = null;
        }
        TableCell currentTableCell = currentRow.getCurrentTableCell();
        if (currentTableCell == null) {
            currentRow.addTableCell(currentTableCell = TableCell.createTableCell());
        }
        currentTableCell.addContent(content);
    }

    @Override
    public void startNewCell() {
        TableRow currentRow = tableBody.getCurrentRow();
        if (currentRow == null) {
            tableBody.addTableRow(currentRow = TableRow.createTableRow(tableRowRepetition));
            tableRowRepetition = null;
        }
        if (currentRow.getSize() >= getColumnCount()) {
            tableBody.addTableRow(currentRow = TableRow.createTableRow(tableRowRepetition));
            tableRowRepetition = null;
        }
        currentRow.addTableCell(TableCell.createTableCell());
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

    @Override
    public List<Content> getComponents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Content getContentAt(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addContentAt(Content content, int index) {
        throw new UnsupportedOperationException();
    }
}

package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

public class TableImpl implements Table {

    private final List<TableColumn> tableColumns = new LinkedList<>();
    private TableBody tableBody;

    @Override
    public void addTableColumn(TableColumn column) {
        tableColumns.add(column);
    }

    @Override
    public void setTableBody(TableBody body) {
        this.tableBody = body;
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

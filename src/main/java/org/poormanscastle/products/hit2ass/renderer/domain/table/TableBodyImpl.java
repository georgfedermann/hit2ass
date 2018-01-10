package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class TableBodyImpl implements TableBody {

    private final List<TableRow> tableRows = new LinkedList<>();

    TableBodyImpl(){}

    @Override
    public void addTableRow(TableRow tableRow) {
        tableRows.add(tableRow);
    }

    @Override
    public TableRow getCurrentRow() {
        return tableRows.isEmpty() ? null : tableRows.get(tableRows.size() - 1);
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        StringBuilder tableRowsContent = new StringBuilder();
        tableRows.stream().forEach(tableRow -> tableRowsContent.append(tableRow.getContent()));
        context.put("tableRowsContent", tableRowsContent.toString());
        Template template = Velocity.getTemplate("/velocity/tables/TemplateBody");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }
}

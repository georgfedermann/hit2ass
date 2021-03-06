package org.poormanscastle.products.hit2ass.renderer.domain.table;

import com.ctc.wstx.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;

import java.io.StringWriter;

public class TableColumnImpl implements TableColumn {

    private int width;

    /**
     * this factor shall help calculate the width of a column in cm which is stated in characters between tab stops.
     * first guess is a 12th of an inch (12cpi) => 0.212 cm
     * 0.212 still appears to narrow, setting it to 0.27 and get feedback from the customer
     */
    private final static double factorCharacterToCm = 0.27;

    TableColumnImpl(int width) {
        this.width = width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public String getTranslatedWidth() {
        return StringUtils.join(Math.round(100 * TableColumnImpl.factorCharacterToCm * width) / 100.0, " cm");
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("columnWidth", getTranslatedWidth());
        Template template = Velocity.getTemplate("/velocity/tables/TemplateColumn.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    @Override
    public String toString() {
        return "TableColumnImpl{" +
                "width=" + width +
                '}';
    }
}

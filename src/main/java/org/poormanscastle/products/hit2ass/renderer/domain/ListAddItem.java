package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;

/**
 * Corresponds to HIT/CLOU statements like #L listName D { 1, 2, 3, 4, 5 }
 * where values get added to a list.
 * <p>
 * Created by georg on 7/22/16.
 */
public class ListAddItem extends AbstractContainer {

    private final String listVariableName;

    private final String newValue;

    public ListAddItem(String name, String listVariableName, String newValue) {
        super(name, "/velocity/TemplateListAddItem.vlt");
        this.listVariableName = listVariableName;
        this.newValue = newValue;
    }

    @Override
    public void setupContext(VelocityContext context) {
        context.put("listVariableName", listVariableName);
        context.put("newValue", newValue);
    }
}

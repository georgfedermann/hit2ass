package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;

/**
 * Corresponds to the HIT/CLOU statement #L listName D {}
 * <p>
 * This is the statement to declare a new list variable, i.e. a symbol that refers to a list (somewhere).
 * This will just create the list. To add values to the list, use the respective statement.
 * <p>
 * In other words:
 * this statement does only map parts of the HIT/CLOU statement. The adding of values is done with another
 * statement, see AddListItem.
 * <p>
 * Created by georg on 7/15/16.
 */
public class ListDeclaration extends AbstractContainer {

    /**
     * the name of the new list variable.
     */
    private final String listVariableName;

    public ListDeclaration(String elementName, String listVariableName) {
        super(elementName, "/velocity/TemplateListDeclaration.vlt");
        this.listVariableName = listVariableName;
    }

    @Override
    public void setupContext(VelocityContext context) {
        context.put("listVariableName", listVariableName);
    }
}

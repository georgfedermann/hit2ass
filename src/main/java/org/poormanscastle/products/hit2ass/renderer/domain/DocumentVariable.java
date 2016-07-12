package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;

/**
 * Represents the type com.assentis.cockpit.bo.BoDocumentVariable.
 * <p>
 * This type provides support for imperial variables. i.e. variables whose
 * values can change in the course of execution. Which breaks the concept
 * of declarative / functional XSLT, I guess.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 6/28/16.
 */
public class DocumentVariable extends AbstractContainer {

    private final String variableName;
    private final String variableValue;

    /**
     * @param elementName   the name of the workspace element (as displayed in DocDesign)
     * @param variableName  the name of the variable. This ha to be a valid XPath expression.
     * @param variableValue The value of the variable. This has to be a valid XPath expression.
     */
    public DocumentVariable(String elementName, String variableName, String variableValue) {
        super(elementName, "/velocity/TemplateDocumentVariable.vlt");
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    @Override
    public void setupContext(VelocityContext context) {
        context.put("variableName", variableName);
        context.put("variableValue", variableValue);
    }

    @Override
    public String toString() {
        return "DocumentVariable{" +
                "variableName='" + variableName + '\'' +
                ", variableValue='" + variableValue + '\'' +
                '}';
    }

}

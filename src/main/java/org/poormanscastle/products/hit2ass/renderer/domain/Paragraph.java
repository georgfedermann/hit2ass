package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents the type com.assentis.cockpit.bo.BoParagraph
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class Paragraph extends AbstractContainer {

    private final TextAlignment textAlignment;

    public Paragraph(String name) {
        this(name, TextAlignment.JUSTIFIED);
    }

    public Paragraph(String name, TextAlignment textAlignment) {
        super(name, "/velocity/TemplateParagraph.vlt");
        checkNotNull(textAlignment);
        this.textAlignment = textAlignment;
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "contentElements=" + getComponents() +
                ", textAlignment=" + textAlignment +
                '}';
    }

    @Override
    public void setupContext(VelocityContext context) {
        context.put("textAlignment", textAlignment.getValue());
    }
}

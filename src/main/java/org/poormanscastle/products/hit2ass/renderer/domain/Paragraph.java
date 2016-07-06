package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;

/**
 * Represents the type com.assentis.cockpit.bo.BoParagraph
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class Paragraph extends AbstractContainer {

    public Paragraph(String name) {
        super(name, "/velocity/TemplateParagraph.vlt");
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "contentElements=" + getComponents() +
                '}';
    }

    @Override
    public void setupContext(VelocityContext context) {

    }
}

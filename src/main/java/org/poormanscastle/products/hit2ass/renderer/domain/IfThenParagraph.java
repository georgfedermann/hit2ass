package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;

/**
 * Created by georg.federmann@poormanscastle.com on 5/11/16.
 */
public final class IfThenParagraph extends AbstractContainer {

    public IfThenParagraph(String name) {
        super(name, "/velocity/TemplateIfThenParagraph.vlt");
    }

    @Override
    public void setupContext(VelocityContext context) {

    }
}

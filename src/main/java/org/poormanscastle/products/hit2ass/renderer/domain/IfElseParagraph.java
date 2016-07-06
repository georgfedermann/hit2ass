package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;

/**
 * Created by georg.federmann@poormanscastle.com on 5/11/16.
 */
public final class IfElseParagraph extends AbstractContainer {

    public IfElseParagraph(String name) {
        super(name, "/velocity/TemplateIfElseParagraph.vlt");
    }

    @Override
    public void setupContext(VelocityContext context) {

    }

}

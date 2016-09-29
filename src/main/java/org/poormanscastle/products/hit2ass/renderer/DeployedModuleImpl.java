package org.poormanscastle.products.hit2ass.renderer;

import org.apache.velocity.VelocityContext;
import org.poormanscastle.products.hit2ass.renderer.domain.AbstractContainer;

/**
 * Created by georg on 29.09.16.
 */
class DeployedModuleImpl extends AbstractContainer implements DeployedModule {

    private final String elementId;

    DeployedModuleImpl(String name) {
        super(name, "/velocity/dplib/TemplateDeployedModule.vlt");
        this.elementId = DocFamUtils.createCockpitElementId();
    }

    @Override
    public void setupContext(VelocityContext context) {
        context.put("elementId", elementId);
    }
}

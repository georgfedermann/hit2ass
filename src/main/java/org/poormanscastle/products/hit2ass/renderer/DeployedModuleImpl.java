package org.poormanscastle.products.hit2ass.renderer;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Created by georg.federmann@poormanscastle.com on 29.09.16.
 */
class DeployedModuleImpl implements DeployedModule {

    private final String elementId;
    private final String name;
    private final String content;

    DeployedModuleImpl(String name, String content, String elementId) {
        this.name = name;
        this.content = content;
        this.elementId = elementId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("name", name);
        context.put("elementId", elementId);
        context.put("content", content);
        Template template = Velocity.getTemplate("/velocity/dplib/TemplateDeployedModule.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    @Override
    public String toString() {
        return "DeployedModuleImpl{" +
                "elementId='" + elementId + '\'' +
                ", name='" + name + '\'' +
                ", content size=" + content.length() + "}'";
    }

}

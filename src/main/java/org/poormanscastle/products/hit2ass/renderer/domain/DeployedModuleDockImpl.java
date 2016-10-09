package org.poormanscastle.products.hit2ass.renderer.domain;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;

/**
 * Created by georg.federmann@poormanscastle.com on 07.10.16.
 */
public class DeployedModuleDockImpl implements DeployedModuleDock {

    private final String name;
    private final String callModule;
    private final String moduleName;

    DeployedModuleDockImpl(String name, String callModule, String moduleName) {
        this.name = name;
        this.callModule = callModule;
        this.moduleName = moduleName;
    }

    @Override
    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("name", name);
        context.put("calledModuleName", callModule);
        context.put("calledModuleElementId", moduleName);
        Template template = Velocity.getTemplate("/velocity/dplib/TemplateDeployedModuleDock.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCallModule() {
        return callModule;
    }

    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public String toString() {
        return "DeployedModuleDockImpl{" +
                "name='" + name + '\'' +
                ", callModule='" + callModule + '\'' +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }

}

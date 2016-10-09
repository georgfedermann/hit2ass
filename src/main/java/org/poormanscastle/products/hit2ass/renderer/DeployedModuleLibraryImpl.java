package org.poormanscastle.products.hit2ass.renderer;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Created by georg on 29.09.16.
 */
class DeployedModuleLibraryImpl implements DeployedModuleLibrary {

    private String elementId;
    private Map<String, DeployedModule> moduleMap = new HashMap<>();

    DeployedModuleLibraryImpl() {
        elementId = DocFamUtils.createCockpitElementId();
    }

    @Override
    public String getElementId() {
        return elementId;
    }

    @Override
    public DeployedModule addDeployedModule(DeployedModule deployedModule) {
        return moduleMap.put(deployedModule.getName(), deployedModule);
    }

    @Override
    public boolean containsDeployedModule(DeployedModule deployedModule) {
        return moduleMap.containsKey(deployedModule.getName());
    }

    @Override
    public boolean containsDeployedModule(String deployedModuleName) {
        return moduleMap.containsKey(deployedModuleName);
    }

    @Override
    public DeployedModule getDeployedModuleByName(String name) {
        return null;
    }

    @Override
    public byte[] renderToDocFamilyWorkspace() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("elementId", elementId);
        context.put("content", createContent());
        Template template = Velocity.getTemplate("/velocity/dplib/TemplateDeployedModuleLibrary.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString().getBytes();
    }

    String createContent() {
        // iterate over contained modules and concatenate their respective 
        // contents into one String.
        StringBuilder result = new StringBuilder();
        for (DeployedModule deployedModule : moduleMap.values()) {
            result.append(deployedModule.getContent());
        }
        return result.toString();
    }
}

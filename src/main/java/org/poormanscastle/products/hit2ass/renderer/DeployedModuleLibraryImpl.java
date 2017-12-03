package org.poormanscastle.products.hit2ass.renderer;

import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Created by georg on 29.09.16.
 */
class DeployedModuleLibraryImpl implements DeployedModuleLibrary {

    /**
     * The unique elementId for this Library. When a deployment package gets created
     * for this library it will have this elementId with a ".dp" appended to it.
     */
    private String elementId = "20160326T133147.780-1673.9391843.274898.6732";
    /**
     * when this library is referenced from within another workspace, the
     * corresponding BoDeployedModuleComposition element will have to specify
     * the elementId of this library. This composition element also will be
     * referenced by all module docks within the containing workspace that
     * reference modules contained within this library. So this elementId
     * needs to be distributed to all moduleDocks inferred from module contained
     * in this library. So, this library appears to be a good place to hold and
     * distribute this elementId.
     */
    private String referencingCompositionElementId = "20160326T133147.780-1673.9391843.214898.6732";
    private Map<String, DeployedModule> moduleMap = new TreeMap<>();

    DeployedModuleLibraryImpl() {
        elementId = DocFamUtils.createCockpitElementId();
    }

    @Override
    public String getElementId() {
        return elementId;
    }

    @Override
    public DeployedModule addDeployedModule(DeployedModule deployedModule) {
        return moduleMap.put(deployedModule.getName().replaceAll("\\.", "_"), deployedModule);
    }

    @Override
    public boolean containsDeployedModule(DeployedModule deployedModule) {
        return moduleMap.containsKey(deployedModule.getName().replaceAll("\\.", "_"));
    }

    @Override
    public boolean containsDeployedModule(String deployedModuleName) {
        return moduleMap.containsKey(deployedModuleName.replaceAll("\\.", "_"));
    }

    @Override
    public DeployedModule getDeployedModuleByName(String name) {
        return moduleMap.get(name.replaceAll("\\.", "_"));
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

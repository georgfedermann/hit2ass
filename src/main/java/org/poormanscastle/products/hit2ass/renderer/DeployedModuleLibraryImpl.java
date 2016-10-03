package org.poormanscastle.products.hit2ass.renderer;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;

/**
 * Created by georg on 29.09.16.
 */
class DeployedModuleLibraryImpl implements DeployedModuleLibrary {

    private String name;
    private String elementId;
    private Map<String, DeployedModule> moduleMap = new HashMap<>();

    DeployedModuleLibraryImpl(String name) {
        this.name = name;
        elementId = DocFamUtils.createCockpitElementId();
    }

    @Override
    public String getName() {
        return name;
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
        
    }
}

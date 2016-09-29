package org.poormanscastle.products.hit2ass.renderer;

import java.util.Map;

/**
 * Created by georg on 29.09.16.
 */
class DeployedModuleLibraryImpl implements DeployedModuleLibrary {

    private String name;
    private String elementId;

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

    private Map<String, DeployedModule> moduleMap;

    @Override
    public boolean addDeployedModule(DeployedModule deployedModule) {
        return false;
    }

    @Override
    public boolean containsDeployedModule(DeployedModule deployedModule) {
        return false;
    }

    @Override
    public DeployedModule getDeployedModuleByName(String name) {
        return null;
    }

}

package org.poormanscastle.products.hit2ass.renderer;

/**
 * represents the DocFamily ModuleDeploymentLibrary to support modularization and re-use when mapping
 * HIT/CLOU (sub) Bausteins to DocFamily Workspaces.
 * Created by georg.federmann@poormanscastle.com on 29.09.16.
 */
public interface DeployedModuleLibrary {

    static String HIT2ASS_DEPLOYED_MODULE_LIBRARY_NAME = "HitAssDeploymentPackageLibrary";

    static DeployedModuleLibrary createNewHitAssDeploymentPackageLibrary() {
        return new DeployedModuleLibraryImpl(DeployedModuleLibrary.HIT2ASS_DEPLOYED_MODULE_LIBRARY_NAME);
    }

    boolean addDeployedModule(DeployedModule deployedModule);

    boolean containsDeployedModule(DeployedModule deployedModule);

    DeployedModule getDeployedModuleByName(String name);

    String getName();

    String getElementId();

}

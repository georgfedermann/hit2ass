package org.poormanscastle.products.hit2ass.renderer.domain;

/**
 * represents the type BoDeployedModuleDock.
 * A DeployedModuleDock references a module made available via a DeployedModuleLibrary import.
 * Created by georg.federmann@poormanscastle.com on 07.10.16.
 */
public interface DeployedModuleDock extends Content {

    /**
     * the name of this module dock.
     *
     * @return the name of this module dock. a String representation.
     */
    String getName();

    /**
     * the name of the referenced module
     *
     * @return a string representation of the respective module's name
     */
    String getCallModule();

    /**
     * the elementId of the refernced module. The naming of the mehtods corresponds
     * to the naming of the respective elements within DocFamily workspaces.
     *
     * @return a string representation of the respective module's DocFamily elementId
     */
    String getModuleName();

    static DeployedModuleDock createDeployedModuleDock(String elementName, String calledModuleName,
                                                       String calledModuleElementId, boolean withinModuleLibrary) {
        return new DeployedModuleDockImpl(elementName, calledModuleName, calledModuleElementId, withinModuleLibrary);
    }

}

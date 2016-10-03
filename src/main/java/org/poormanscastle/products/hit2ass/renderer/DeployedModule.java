package org.poormanscastle.products.hit2ass.renderer;

import org.poormanscastle.products.hit2ass.renderer.domain.Container;

/**
 * Represents the DocFamily BoModuleComposition to support re-use and modularization when mapping HIT/CLOU
 * (sub) bausteins to DocFamily Workspaces.
 * Created by georg on 29.09.16.
 */
public interface DeployedModule extends Container {

    static DeployedModule createNew(String name) {
        return new DeployedModuleImpl(name);
    }

    String getName();

}

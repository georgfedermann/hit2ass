package org.poormanscastle.products.hit2ass.renderer;

import org.poormanscastle.products.hit2ass.renderer.domain.Content;

/**
 * Represents the DocFamily BoModuleComposition to support re-use and modularization
 * when mapping HIT/CLOU (sub) bausteins to DocFamily Workspaces.
 * Created by georg.federmann@poormanscastle.com on 29.09.16.
 */
public interface DeployedModule extends Content {

    static DeployedModule createNew(String name, String content, String elementId) {
        return new DeployedModuleImpl(name, content, elementId);
    }

    String getName();
    
    String getElementId();

}

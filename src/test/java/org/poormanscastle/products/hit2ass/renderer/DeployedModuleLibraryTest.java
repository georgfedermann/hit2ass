package org.poormanscastle.products.hit2ass.renderer;

import org.junit.Test;

/**
 * Created by georg on 03.10.16.
 */
public class DeployedModuleLibraryTest {
    @Test
    public void getHitAssDeploymentPackageLibrary() throws Exception {
        DeployedModuleLibrary library = DeployedModuleLibrary.loadHitAssDeployedModuleLibrary();
    }

}
package org.poormanscastle.products.hit2ass.renderer;

/**
 * Created by georg.federmann@poormanscastle.com on 29.09.16.
 */
class DeployedModuleImpl implements DeployedModule {

    private final String elementId;
    private final String name;
    private final String content;

    DeployedModuleImpl(String name, String content, String elementId) {
        this.name = name;
        this.content = content;
        this.elementId = elementId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DeployedModuleImpl{" +
                "elementId='" + elementId + '\'' +
                ", name='" + name + '\'' +
                ", content size=" + content.length() + "}'";
    }
    
}

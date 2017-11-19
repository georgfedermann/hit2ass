package org.poormanscastle.products.hit2ass.renderer.domain;

import org.apache.velocity.VelocityContext;

/**
 * This container is intended to be the direct contentContainer of a Workspace instance.
 * Previously, the Paragraph type was used as this role, but this was abuse of the Paragraph
 * type.
 * <p>
 * When a WorkspaceContainer is rendered to a DocFamily workspace, it only renders its
 * child elements and vanishes, thus being an intermediate collection of DocFamily
 * concepts for the time between parsing of HIT/CLOU text components and transformation
 * to Assentis DocFamily workspaces.
 * <p>
 * Created by georg on 8/11/16.
 */
public class WorkspaceContainer extends AbstractContainer {

    public WorkspaceContainer() {
        this("no name");
    }

    /**
     * @param name the name will mostly get ignored. It might be of some use
     *             in some debugging scenarios.
     */
    public WorkspaceContainer(String name) {
        super(name, "/velocity/TemplateWorkspaceContainer.vlt");
    }

    @Override
    public void setupContext(VelocityContext context) {

    }

    @Override
    public String toString() {
        return "WorkspaceContainer{" +
                "contentElements=" + getComponents() +
                "}";
    }
}

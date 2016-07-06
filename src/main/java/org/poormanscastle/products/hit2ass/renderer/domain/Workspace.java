package org.poormanscastle.products.hit2ass.renderer.domain;

import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;

/**
 * Represents the type com.assentis.cockpit.bo.BoWorkspace.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class Workspace {

    private String workspaceName = "HitAssWorkspace";
    private String projectsName = "HitAssProjects";
    private String projectName = "HitAssProject";
    private String documentName = "HitAssDocument";
    private String repeatingPageName = "HitAssRepeatingPage";
    private String pageContentName = "HitAssPageContent";

    private Container contentContainer;

    public Workspace(String workspaceName, String projectsName, String projectName,
                     String documentName, String repeatingPageName, String pageContentName) {
        this.workspaceName = workspaceName;
        this.projectsName = projectsName;
        this.projectName = projectName;
        this.documentName = documentName;
        this.repeatingPageName = repeatingPageName;
        this.pageContentName = pageContentName;
    }

    public Workspace() {
    }

    public String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("workspaceName", workspaceName);
        context.put("projectsName", projectsName);
        context.put("projectName", projectName);
        context.put("documentName", documentName);
        context.put("repeatingPageName", repeatingPageName);
        context.put("pageContentName", pageContentName);
        context.put("content", contentContainer.getContent());
        Template template = Velocity.getTemplate("/velocity/TemplateWorkspace.vlt");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public String getProjectsName() {
        return projectsName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDocumentName() {
        return documentName;
    }

    public String getRepeatingPageName() {
        return repeatingPageName;
    }

    public String getPageContentName() {
        return pageContentName;
    }

    public Container getContentContainer() {
        return contentContainer;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public void setProjectsName(String projectsName) {
        this.projectsName = projectsName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setRepeatingPageName(String repeatingPageName) {
        this.repeatingPageName = repeatingPageName;
    }

    public void setPageContentName(String pageContentName) {
        this.pageContentName = pageContentName;
    }

    public void setContentContainer(Container contentContainer) {
        this.contentContainer = contentContainer;
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "workspaceName='" + workspaceName + '\'' +
                ", projectsName='" + projectsName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", documentName='" + documentName + '\'' +
                ", repeatingPageName='" + repeatingPageName + '\'' +
                ", pageContentName='" + pageContentName + '\'' +
                ", contentContainer=" + contentContainer +
                '}';
    }

}

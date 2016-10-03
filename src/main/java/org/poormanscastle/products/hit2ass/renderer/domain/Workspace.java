package org.poormanscastle.products.hit2ass.renderer.domain;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jaxen.JaxenException;
import org.poormanscastle.products.hit2ass.exceptions.BausteinMergerException;
import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;

/**
 * Represents the type com.assentis.cockpit.bo.BoWorkspace.
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class Workspace {

    private final static Logger logger = Logger.getLogger(Workspace.class);

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

    /**
     * delivers the complete Workspace in its XML based ACR form.
     *
     * @return a String holding an XML version of the complete workspace.
     */
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

    /**
     * delivers the actual contents of the workspace on a page content level.
     * This method is helpful if you're creating modules from the page contents.
     *
     * @return a String holding a String representation of the page contents.
     */
    public String getPageContent() {
        try {
            OMElement workspaceElement = OMXMLBuilderFactory.createOMBuilder(
                    new ByteArrayInputStream(getContent().getBytes())).getDocumentElement();
            AXIOMXPath xPath = new AXIOMXPath(
                    "/Cockpit/Object[@type='com.assentis.cockpit.bo.BoWorkspace']/" +
                            "Object[@type='com.assentis.cockpit.bo.BoProjectGroup']/" +
                            "Object[@type='com.assentis.cockpit.bo.BoProject']/" +
                            "Object[@type='com.assentis.cockpit.bo.BoDocument']/" +
                            "Object[@type='com.assentis.cockpit.bo.BoPageRepetition'/" +
                            "Object[@type='com.assentis.cockpit.bo.BoPage/element()");
            StringBuilder result = new StringBuilder();
            List<OMElement> pageElements = ((List<OMElement>) xPath.selectNodes(workspaceElement));
            for (int counter = 1; counter < pageElements.size() - 2; counter++) {
                result.append(pageElements.get(counter).toString());
            }
            return result.toString();
        } catch (JaxenException e) {
            String errMsg = StringUtils.join("Could not create module from sub Baustein because: ",
                    e.getClass().getName(), " - ", e.getMessage());
            logger.error(errMsg);
            throw new BausteinMergerException(errMsg, e);
        }
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

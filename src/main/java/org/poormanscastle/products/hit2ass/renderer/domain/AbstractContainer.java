package org.poormanscastle.products.hit2ass.renderer.domain;

import org.poormanscastle.products.hit2ass.renderer.VelocityHelper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public abstract class AbstractContainer implements Container {

    /**
     * this name will be used as the name of the corresponding Assentis DocDesign BO Object
     * in the DocDesign Workspace.
     */
    private final String name;

    /**
     * a path to the velocity template file or a logical template name, depending on the setup
     * of the system. e.g. "/velocity/TemplateIfThenElseParagraph.vlt"
     */
    private final String templateName;

    private final List<Content> contentElements = new LinkedList<>();

    public AbstractContainer(String name, String templateName) {
        // DocDesign doesn't accept element names which contain an exclamation mark (!)
        this.name = name.replaceAll("!", " NOT ");
        this.templateName = templateName;
    }

    @Override
    public void addContent(Content content) {
        contentElements.add(content);
    }

    @Override
    public List<Content> getComponents() {
        return Collections.unmodifiableList(contentElements);
    }

    @Override
    public Content getContentAt(int index) {
        return contentElements.get(index);
    }

    @Override
    public void addContentAt(Content content, int index) {
        contentElements.add(index, content);
    }

    @Override
    public final String getContent() {
        VelocityContext context = VelocityHelper.getVelocityContext();
        context.put("name", name);
        setupContext(context);

        StringBuilder content = new StringBuilder();
        for (Content component : getComponents()) {
            content.append(component.getContent());
        }

        context.put("content", content);
        Template template = Velocity.getTemplate(templateName);
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    /**
     * by implementing this method extension classes can add their 2 cents
     * to the velocity context before rendering.
     *
     * @param context
     */
    public abstract void setupContext(VelocityContext context);
}

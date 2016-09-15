package org.poormanscastle.products.hit2ass.renderer.domain;

import java.util.List;

/**
 * Serves as a container element for contents. Typically a paragraph
 * or similar items will serve as a container for content like Text,
 * DynamicContentReferences, NewLine etc.
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public interface Container extends Content {

    /**
     * append content to this container.
     *
     * @param content the document content
     */
    void addContent(Content content);

    List<Content> getComponents();

    Content getContentAt(int index);

    void addContentAt(Content content, int index);

}

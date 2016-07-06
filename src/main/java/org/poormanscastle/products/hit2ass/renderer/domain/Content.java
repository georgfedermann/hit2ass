package org.poormanscastle.products.hit2ass.renderer.domain;

/**
 * Content is any of the elements that are available to add to DocDesign
 * templates within DocDesign. Like Business Charts, Barcodes, Tabulator,
 * Text, Symbol, Dynamic Content, Date, Space, Page number, Graphic, SVG,
 * well, you name it. With the restriction that the content is specified
 * within HIT/CLOU components and thus is restricted to the expressive
 * power of the HIT / CLOU system.
 * <p>
 * Anyway: content will typically be something like a paragraph, a fixed
 * text, a dynamic text or a new line character.
 * <p>
 * When rendering of the IRT (intermediate representation tree) to
 * DocDesign Workspaces happens, the content object will be serialized
 * to XML text which will be embedded more or less into an XML representation
 * of its parent element, all the way up to the Workspace element.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public interface Content {

    String getContent();

}

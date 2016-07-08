package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import org.apache.commons.lang3.StringUtils;
import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.DynamicValue;

/**
 * Iterates an AST representing a HIT/CLOU text component and creates from it and
 * for it a basic XML structure containing all (locally or globally declared) variables
 * into which values are read in the course of the Baustein using the CLOU statement
 * #X< .
 * <p>
 * Thus, this visitor will look for DynamicValue instances in the AST in the
 * sequence of occurrence in the original HIT/CLOU text component, take the name
 * of the referenced variable and write it into an XML template file. The sequence
 * of the elements in the XML corresponds exactly (hopefully) to the sequence of
 * values in the input file.
 * <p>
 * When in production mode an input file arrives in some hotfolder or whatever, its
 * contents can be pasted into a new instance of the XML template file, resulting
 * in a DocFamily data XML which can then be further processed in standard
 * DocFamily ways.
 * <p>
 * Thus, where ever a variable name is used within a HIT/CLOU text component, this
 * variable name should be validly replaceable with an XPath referencing the
 * respective value in the generated DocFamily input XML.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 5/10/16.
 *
 * @deprecated the strategy to create the userdata xml has changed fundamentally from
 * parsing an AST to find #X> statements to embedding the input text file into a
 * generic XML structure. So, this type is not needed any more. It never worked, anyway.
 */
final public class XmlCreator extends AstItemVisitorAdapter {
    // the implementation of this class could well be replaced by code
    // using the DOM API.

    private StringBuilder xmlBuffer = new StringBuilder();
    private int sequence = 0;

    {
        init();
    }

    /**
     * After calling this method the XmlCreator is ready to iterate
     * a new AST. After iterating an AST, the XmlCreator can be reused
     * after calling this method again. For your convenience the XmlCreator
     * comes loaded and ready to work, so for a one-shot action, you don't
     * need to call this method at all, pal.
     */
    public void init() {
        xmlBuffer = new StringBuilder();
        xmlBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<xml>\n");
        sequence = 0;
    }

    /**
     * After the XmlCreator traversed an AST, call this serialize method
     * to get the XML template for this AST.
     */
    public String serialize() {
        return xmlBuffer.append("</xml>\n").toString();
    }

    @Override
    public void visitDynamicValue(DynamicValue dynamicValue) {
        xmlBuffer.append(StringUtils.join("  <", dynamicValue.getName(), ">${param", sequence++, "}</", dynamicValue.getName(), ">\n"));
    }

}

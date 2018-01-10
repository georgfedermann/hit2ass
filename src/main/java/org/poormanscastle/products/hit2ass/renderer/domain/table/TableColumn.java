package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.poormanscastle.products.hit2ass.renderer.domain.Content;

/**
 * the TableColumn is content insofar, as it needs to be rendered to DocFamily acr output as well.
 * its only content might in fact be the width of the column :-)
 */
public interface TableColumn extends Content {

    /**
     * set the column width as specified in the hit/clou baustein, which is given in
     * a difference of tab positions there.
     * @param width
     */
    void setWidth(int width);

    int getWidth();

    /**
     * this method takes the width of the column specified as a difference of tab positions
     * and returns a string that can be used by DocFamily to actually define an xsl-fo table column width.
     * e.g. 3.4 cm or something.
     * @return
     */
    String getTranslatedWidth();

    static TableColumn createTableColumnWithSpecifiedWidthinCharacters(int width){
        return new TableColumnImpl(width);
    }

}

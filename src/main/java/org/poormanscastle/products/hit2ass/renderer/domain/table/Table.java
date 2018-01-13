package org.poormanscastle.products.hit2ass.renderer.domain.table;

import org.apache.velocity.VelocityContext;
import org.poormanscastle.products.hit2ass.renderer.domain.AbstractContainer;
import org.poormanscastle.products.hit2ass.renderer.domain.Container;
import org.poormanscastle.products.hit2ass.renderer.domain.Content;

import java.util.List;

/**
 * Table is the main class in the IRTree table package. It represents a tabular structure
 * within a document. The table is a static structure and gets constructed following
 * the definitions within the given HIT/CLOU Baustein.
 * The hit/clou command #^ZL NEU followed by a sequence of #G statements is the hit/clou
 * preamble to introduce a new table. The #G statements will define the table columns.
 * There will be one column less than the total number of #G statements. #G statements
 * kind of set tab markers. #G statements take two arguments. 1st on probably is a
 * character position, 2nd probably is some code to define what shall happen at this
 * character position.
 * <pre>
 * E.g.
 * #^"ZL NEU"
 * #G 9 1     - text line starts a character position 9
 * #G 30 2    - 1st column ends at position 30, 2 means contents are left aligned
 * #G 46 4    - 2nd column ends at position 46, 4 means contents are right aligned
 * #G 88 8    - line ends at position 88, don't know if there is another column before that
 *              or what alignment it might have?
 * </pre>
 * Anyway, this table will be added columns for each #G statement after the first #G 9 1
 * which will probably occur for all bausteins available.
 * Afterwards, all content will be added to table cells.
 * HIT/CLOU switches between cells by doing TAB jumps from tab position to tab position.
 * The Assentis DocFamily table abstraction makes use of a more expressive domain model.
 * Here we find the additional concepts of body, row and cell.
 * Thus, the IR transformer will have to create cells for each content found and add
 * content to those cells, and create rows to add the cells to. And create new cells
 * every time a tab is found and the current row is already fully filled.
 * All those rows have to be added to a table body.
 * Thus, this table concept can then be rendered to an Assentis DocFamily table.
 * A hit/clou table definition will end in one of two ways:
 * 1. A new table gets defined using a preamble as described above.
 * 2. Normal text formatting continues, triggered by these statements:
 * <pre>
 * #^"ZL NEU"
 * #^"W LÖSCHEN"
 * #G 9 1
 * #G 88 8
 * #^"RETURN"
 * </pre>
 * This is the idea how to do this:
 * - When the command #^"ZL NEU" is found, a table builder takes over and iterates the AST
 * until it finds the next instance of #^"ZL NEU" (which will end the table, maybe by starting
 * the next table ...)
 * - if the table builder finds #G statements as expected it continues working
 * - if the following statements do not reflect the hit/clou logic of a table structure as outlined above,
 * the table builder finishes and throws a NoTableFoundException so the IRTransformer knows that the
 * given #^"ZL NEU" statement cannot be interpreted as a table preamble.
 * - the table builder will create an irtree.Table object
 * - the table builder will create irtree.Column objects as motivated by #G statements and add the to the table
 * - the table builder will create one irtree.Body object and add it to the table
 * - the table builder one irtree.Row object and add it to the body
 * - the table builder will create one irtree.Cell object and add it to the available row
 * - the table builder will add all content to the available cell
 * - when a tab stop is encountered, the table builder will create a new irtree.Cell object and add it to the row
 * except if that row is already full (number of cells = number of columns), then the
 * table builder will create a new row, add it to the body, and then add the cell to the available row.
 * - When the #^"ZL NEU" statement is encountered, the table builder returns whatever it has
 * constructed so far, no questions asked ...
 */
public interface Table extends Container {

    void addTableColumn(int width);


    /**
     * @return an integer value representing the number of columns in this table
     */
    int getColumnCount();

    static Table createTable() {
        return new TableImpl();
    }

    /**
     * this method will be called by the IRTransformer, if in table builder mode a TABU marker is found.
     */
    void startNewCell();

    /**
     * add some content to this table
     *
     * @param content
     */
    void addContent(Content content);

}

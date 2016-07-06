package org.poormanscastle.products.hit2ass.transformer;

import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.FixedText;
import org.poormanscastle.products.hit2ass.ast.domain.LastClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.PairClouBausteinElementList;

/**
 * The FixedTextMerger merges neighbouring fixtext elements into one to save space
 * in the AST and in memory, and produce a less complex AST for further processing.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 08.04.2016.
 */
public class FixedTextMerger extends AstItemVisitorAdapter {

    /**
     * This stores the parent element of the last found FixText element.
     */
    private PairClouBausteinElementList parent;

    /**
     * The first FixedText element found in contiguous row is saved here to append the
     * contents of the following FixedText elements to.
     */
    private FixedText master;

    @Override
    public void visitPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {
        if (pairClouBausteinElementList.getHead() instanceof FixedText) {
            if (parent == null) {
                parent = pairClouBausteinElementList;
                master = (FixedText) pairClouBausteinElementList.getHead();
            } else {
                master.appendText(((FixedText) pairClouBausteinElementList.getHead()).getText(), true);
                parent.setTail(pairClouBausteinElementList.getTail());
            }
        } else {
            parent = null;
            master = null;
        }
    }

    @Override
    public void visitLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList) {
        if (lastClouBausteinElementList.getHead() instanceof FixedText && parent != null) {
            FixedText fixedText = (FixedText) lastClouBausteinElementList.getHead();
            master.appendText(fixedText.getText(), true);
            fixedText.reset();
            parent = null;
            master = null;
        } else if (!(lastClouBausteinElementList.getHead() instanceof FixedText)) {
            parent = null;
            master = null;
        }
    }
}

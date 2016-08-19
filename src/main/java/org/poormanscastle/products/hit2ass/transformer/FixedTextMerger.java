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
    public void visitPairClouBausteinElementList(final PairClouBausteinElementList pairClouBausteinElementList) {
        if (pairClouBausteinElementList.getHead() instanceof FixedText) {
            if (parent == null) {
                parent = pairClouBausteinElementList;
                master = (FixedText) pairClouBausteinElementList.getHead();
            } else {
                FixedText followingText = ((FixedText) pairClouBausteinElementList.getHead());
                boolean withBlank = !(master.getText().endsWith("\"") && followingText.getText().startsWith("."));
                master.appendText(((FixedText) pairClouBausteinElementList.getHead()).getText(), withBlank);
                parent.replaceTail(pairClouBausteinElementList, pairClouBausteinElementList.getTail());
            }
        } else {
            parent = null;
            master = null;
        }
    }

    @Override
    public void visitLastClouBausteinElementList(final LastClouBausteinElementList lastClouBausteinElementList) {
        if (lastClouBausteinElementList.getHead() instanceof FixedText && parent != null) {
            FixedText fixedText = (FixedText) lastClouBausteinElementList.getHead();
            master.appendText(fixedText.getText(), true);
            // In this case, the FixedText streak ends in a LastClouBausteinElementList, which would render an
            // appendix with an empty FixedText element. Which just is not beautiful. So, we will remove the appendix.
            parent.getParent().replaceTail(parent, new LastClouBausteinElementList(parent.getCodePosition(), parent.getHead()));
            parent = null;
            master = null;
        } else if (!(lastClouBausteinElementList.getHead() instanceof FixedText)) {
            parent = null;
            master = null;
        }
    }
}

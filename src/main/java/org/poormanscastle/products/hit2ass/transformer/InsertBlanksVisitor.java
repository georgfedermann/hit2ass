package org.poormanscastle.products.hit2ass.transformer;

import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.CodePosition;
import org.poormanscastle.products.hit2ass.ast.domain.FixedText;
import org.poormanscastle.products.hit2ass.ast.domain.PairClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.PrintStatement;

import java.util.regex.Pattern;

/**
 * To be executed after the FixedTextMerger.
 * <p>
 * The HIT/CLOU Baustein parser will create one FixedText object for each word found
 * in text mode in the HIT/CLOU Baustein
 * <p>
 * The FixedTextMerger will merge adjacent FixedTexts, among other reasons to
 * reduce the size of the resulting DocFamily workspace. The strategy of inserting
 * blanks between words works for the merging of FixText objects, but is insufficient
 * for a mix of fixed text and dynamic values.
 * <p>
 * This visitor is to be executed after the FixedTextMerger. It will search for
 * direct successions of FixedText objects and dynamic values and will insert blanks there.
 * <p>
 * Created by georg on 7/10/16.
 */
public class InsertBlanksVisitor extends AstItemVisitorAdapter {

    /**
     * This stores a reference to the direct ancestor of the current element.
     */
    private PairClouBausteinElementList previousElementList;

    @Override
    public void visitPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {
        if (pairClouBausteinElementList.getHead() instanceof FixedText && previousElementList != null && previousElementList.getHead() instanceof PrintStatement) {
            FixedText fixedText = (FixedText) pairClouBausteinElementList.getHead();
            String content = fixedText.getText();
            if (!Pattern.matches("\\p{Punct}", content.substring(0, 1))) {
                fixedText.reset();
                fixedText.appendText(content, true);
            }
        } else if (pairClouBausteinElementList.getHead() instanceof PrintStatement && previousElementList != null && previousElementList.getHead() instanceof FixedText) {
            FixedText fixedText = (FixedText) previousElementList.getHead();
            fixedText.appendText(" ", false);
        } else if (previousElementList != null && previousElementList.getHead() instanceof PrintStatement && pairClouBausteinElementList.getHead() instanceof PrintStatement) {
            previousElementList.setTail(new PairClouBausteinElementList(FixedText.create(CodePosition.createZeroPosition(), " "), pairClouBausteinElementList));
        }
        previousElementList = pairClouBausteinElementList;
    }
}

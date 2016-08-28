package org.poormanscastle.products.hit2ass.transformer;

import static com.google.common.base.Preconditions.checkState;

import org.apache.commons.lang3.StringUtils;
import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.FixedText;
import org.poormanscastle.products.hit2ass.ast.domain.LastClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.NewLine;
import org.poormanscastle.products.hit2ass.ast.domain.PairClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.PrintStatement;

/**
 * The parser strategy is this:
 * First read in all fixed text including blanks and new line characters.
 * <p>
 * In a later processing step look for all new line characters and delete all
 * blanks before them and after them, by trimming the head and tails respectively
 * of trailing and preceding fixed text elements.
 * <p>
 * Created by georg on 26.08.16.
 */
public class EraseBlanksVisitor extends AstItemVisitorAdapter {

    void visitClouBausteinElementList(ClouBausteinElementList clouBausteinElementList) {
        // blanks erasure rules:
        // 1. Please give #> firstName my love. --> if there is one blank in the end, leave it. but only one, truncate excessive blanks.
        // 2. HIT/CLOU text is often indented like source code (argl) --> If new lines are inserted into flow text to 
        // make it better readable, insert blanks for new lines. --> If the parent element is not null and parent.head()
        // is NewLine, and the parent of NewLine is not null and (FixedText or PrintStatement), let there be one leading blank. Truncate all
        // leading blanks in all other cases.
        // 3. If there is a FixedText with a single blank, leave it (especially if it appears between otherwise sequential Print Statements.
        // 4. If there is a FixedText which solely consists of more than one blank, erase it completely. It should stem from an empty line with indentation.
        // 5. If the direct predecessor is a PrintStatement leave leading blanks as found.
        if (!(clouBausteinElementList.getHead() instanceof FixedText)) {
            return;
        }
        FixedText fixedText = (FixedText) clouBausteinElementList.getHead();
        checkState(fixedText.getText() != null);
        if (StringUtils.isBlank(fixedText.getText()) && fixedText.getText().length() != 1) {
            // Rule 3, Rule 4
            fixedText.reset();
        } else if (fixedText.getText().length() > 1) {
            String strippedText = fixedText.getText();
            if (strippedText.matches(".+\\s\\s+$")) {
                // Rule 1 -- this string ends in more than one blanks, just leave one.
                strippedText = StringUtils.join(StringUtils.stripEnd(strippedText, null), " ");
                fixedText.reset();
                fixedText.appendText(strippedText, false);
            }

            if (clouBausteinElementList.getParent() != null && (clouBausteinElementList.getParent().getHead() instanceof PrintStatement)) {
                // Rule 5
                return;
            }

            // Rule 2
            boolean conserveOneLeadingBlank = clouBausteinElementList.getParent() != null && clouBausteinElementList.getParent().getHead() instanceof NewLine
                    && clouBausteinElementList.getParent().getParent() != null &&
                    ((clouBausteinElementList.getParent().getParent().getHead() instanceof FixedText) || (clouBausteinElementList.getParent().getParent().getHead() instanceof PrintStatement));
            strippedText = StringUtils.stripStart(strippedText, null);
            fixedText.reset();
            fixedText.appendText(strippedText, conserveOneLeadingBlank);
        }
    }

    @Override
    public void visitPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {
        this.visitClouBausteinElementList(pairClouBausteinElementList);
    }

    @Override
    public void leaveLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList) {
        this.visitClouBausteinElementList(lastClouBausteinElementList);
    }

}

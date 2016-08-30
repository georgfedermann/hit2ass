package org.poormanscastle.products.hit2ass.transformer;

import static com.google.common.base.Preconditions.checkState;

import org.apache.commons.lang3.StringUtils;
import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.ConditionalStatement;
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
        // blanks erasure rules for FixedText elements:
        // 1. Please give #> firstName my love. --> if there is one trailing blank, leave it. but only one, truncate excessive blanks.
        // 2. HIT/CLOU text is often indented like source code (argl) --> If new lines are inserted into flow text to 
        //    make it better readable, insert blanks for new lines. --> If the parent element is not null and parent.head()
        //    is NewLine, and the parent of NewLine is not null and (FixedText or PrintStatement), let there be one leading blank. Truncate all
        //    leading blanks in all other cases.
        // 3. If there is a FixedText with a single blank, leave it (especially if it appears between otherwise sequential Print Statements.
        // 4. If there is a FixedText which solely consists of blanks and more than one blank, erase it completely. It should stem from an empty line with indentation.
        // 5. If the direct predecessor is a PrintStatement leave leading blanks as found.
        // 6. If the parent ClouBausteinElementList is null leave one leading blank. DocFamily will only display it, if
        //    it occurs within a text flow and erase it if it appears at the start of a paragraph. So this should be save
        //    to resolve the problem where text that continues within an IF statement will miss blanks.
        // 7. To resolve situations where the text flow fully encloses a conditional statement, allow a single leading blank
        //    in the first FixedText element after the IF. If flow text passes through the IF, that's good. If new text
        //    starts after the IF, there is hope that a new paragraph is started and thus the blank is erased by DocFamily.
        // 8. If a PrintStatement follows in the next line and the FixedText does not end in a quote, add one trailing blank.
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
                    ((clouBausteinElementList.getParent().getParent().getHead() instanceof FixedText)
                            || (clouBausteinElementList.getParent().getParent().getHead() instanceof PrintStatement)
                    );
            // Rule 6: first FixedText within a THEN or ELSE shall have a leading blank.
            conserveOneLeadingBlank |= clouBausteinElementList.getParent() != null && clouBausteinElementList.getParent().getParent() == null;
            // Rule 7
            conserveOneLeadingBlank |= isPrecedingElementConditional(clouBausteinElementList.getParent());
            // Rule 8
            if (!strippedText.endsWith("\"") && !strippedText.endsWith(" ") &&
                    clouBausteinElementList.getTail() != null && (clouBausteinElementList.getTail().getHead() instanceof NewLine) &&
                    clouBausteinElementList.getTail().getTail() != null && isPrintStatementFollowing(clouBausteinElementList.getTail())) {
                strippedText = StringUtils.join(strippedText, " ");
            }
            strippedText = StringUtils.stripStart(strippedText, null);
            fixedText.reset();
            fixedText.appendText(strippedText, conserveOneLeadingBlank);
        }
    }

    /**
     * Tells whether the directly following relevant element is a PrintStatement. Non-relevant elements would be
     * NewLine and FixedText if it only contains blanks and more than one. This method also looks into the bodies
     * of IF statements.
     *
     * @param elementList
     * @return
     */
    private boolean isPrintStatementFollowing(ClouBausteinElementList elementList) {
        if (elementList == null) {
            return false;
        } else if (elementList.getHead() instanceof PrintStatement) {
            return true;
        } else if ((elementList.getHead() instanceof NewLine) ||
                (elementList.getHead() instanceof FixedText) && StringUtils.isBlank(((FixedText) elementList.getHead()).getText())) {
            return isPrintStatementFollowing(elementList.getTail());
        } else if (elementList.getHead() instanceof ConditionalStatement) {
            ConditionalStatement ifStatement = (ConditionalStatement) elementList.getHead();
            // TODO some pondering can be done if following should be an AND or an OR, or how to proceed if 
            // a PrintStatement was following in one but not the other branch. E.g. the THEN branch contains a
            // dynamic text expressed by a PrintStatement, while the ELSE branch contains some fixed text. Gross.
            return (isPrintStatementFollowing(ifStatement.getThenElement()) || isPrintStatementFollowing(ifStatement.getElseElement()));
        } else {
            return false;
        }
    }

    /**
     * Tells whether the directly preceding relevant element is a conditional statement. Non-relevant elements would
     * be NewLine.
     *
     * @param elementList
     * @return
     */
    private boolean isPrecedingElementConditional(ClouBausteinElementList elementList) {
        if (elementList == null) {
            return false;
        } else if (elementList.getHead() instanceof ConditionalStatement) {
            return true;
        } else if (elementList.getHead() instanceof NewLine) {
            return isPrecedingElementConditional(elementList.getParent());
        } else {
            return false;
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

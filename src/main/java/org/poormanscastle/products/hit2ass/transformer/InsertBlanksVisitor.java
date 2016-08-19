package org.poormanscastle.products.hit2ass.transformer;

import java.util.regex.Pattern;

import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBausteinElement;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.CodePosition;
import org.poormanscastle.products.hit2ass.ast.domain.FixedText;
import org.poormanscastle.products.hit2ass.ast.domain.HitCommand;
import org.poormanscastle.products.hit2ass.ast.domain.HitCommandStatement;
import org.poormanscastle.products.hit2ass.ast.domain.LastClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.MacroCallStatement;
import org.poormanscastle.products.hit2ass.ast.domain.PairClouBausteinElementList;
import org.poormanscastle.products.hit2ass.ast.domain.PrintStatement;
import org.poormanscastle.products.hit2ass.ast.domain.SectionStatement;

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
    private ClouBausteinElementList previousElementList;

    /**
     * keep track of line breaks so not to add too many blanks. E.g. if between the previous fixed text and the
     * current fixed text there was a line break, no blank needs to be added to the last fixed text.
     */
    private boolean lineBreakFlag;

    @Override
    public void visitSectionStatement(SectionStatement sectionStatement) {
        lineBreakFlag = true;
    }

    @Override
    public void visitHitCommandStatement(HitCommandStatement hitCommandStatement) {
        if (hitCommandStatement.getHitCommand() == HitCommand.RETURN) {
            lineBreakFlag = true;
        }
    }

    @Override
    public void visitMacroCallStatement(MacroCallStatement macroCallStatement) {
        if (macroCallStatement.getMacroId().equals("TABU")) {
            lineBreakFlag = true;
        }
    }

    void visitClouBausteinElementList(final ClouBausteinElementList clouBausteinElementList) {
        ClouBausteinElement head = clouBausteinElementList.getHead(), previousHead = previousElementList == null ? null : previousElementList.getHead();
        if (!(head instanceof FixedText || head instanceof PrintStatement)) {
            // nothing to do here. This item has no direct impact on the behavior of the InsertBlanksVisitor.
            return;
        }
        if (lineBreakFlag) {
            // if there was a linke break since the last FixedText or PrintStatement, do nothing.
            // Just consume the linke break flag and go on.
            return;
        }
        // In all other cases, check the situation and process ...
        if (head instanceof FixedText && (previousHead instanceof FixedText && !((FixedText) previousHead).getText().endsWith((" ")) || previousHead instanceof PrintStatement)) {
            // Cover the case where e.g. an IF or WHILE structure directly follows a fixed text, and the structure
            // contains a fixed text. Then a blank needs to be added to the previous word / fixed text.
            // Also cover the case: add a blank to the start of a fixed text if the previous element was a PrintStatement
            FixedText fixedText = (FixedText) head;
            String content = fixedText.getText();
            if (!Pattern.matches("\\p{Punct}|\"", content.substring(0, 1))) {
                fixedText.reset();
                fixedText.appendText(content, true);
            }
        } else if (head instanceof PrintStatement && previousHead instanceof FixedText) {
            // Cover the case when the current element is a PrintStatement and cannot be prefixed with a blank, but
            // the previous element must be manipulated.
            FixedText fixedText = (FixedText) previousHead;
            if (!fixedText.getText().endsWith("\"")) {
                fixedText.appendText(" ", false);
            }
        } else if (clouBausteinElementList instanceof PairClouBausteinElementList && head instanceof PrintStatement && previousHead instanceof PrintStatement) {
            // in case of a direct sequence of PrintStatements, separate the PrintStatements with a FixedText element containing a blank.
            previousElementList.replaceTail(clouBausteinElementList, new PairClouBausteinElementList(FixedText.create(CodePosition.createZeroPosition(), " "), clouBausteinElementList));
        } else if (clouBausteinElementList instanceof LastClouBausteinElementList && head instanceof PrintStatement && previousHead instanceof PrintStatement) {
            // similar to the last case. But there is always the chance that the first PrintStatement is the last statement
            // in an IF structure and thus is a LastClouBausteinElementList, which has no tail element. In this case, the
            // LastClouBausteinElementList must be replaced by a PairClouBausteinElementList with the same head and tail.
            previousElementList.getParent().replaceTail(clouBausteinElementList,
                    new PairClouBausteinElementList(previousElementList.getHead(),
                            new PairClouBausteinElementList(FixedText.create(CodePosition.createZeroPosition(), " "), clouBausteinElementList)));
        }
        previousElementList = clouBausteinElementList;
    }

    @Override
    public void visitPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {
        visitClouBausteinElementList(pairClouBausteinElementList);
    }

    @Override
    public void visitLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList) {
        // for explanations and comments, see visitPairClouBausteinElementList()
        visitClouBausteinElementList(lastClouBausteinElementList);
        // this line seems to be stupid. previousElementList shall track whether there has been any output yet and if so,
        // provide a reference to that output so blanks can be appended if necessary. Setting it to null appears so
        // contra. I can't think of the situation right now where this would be helpful, but can show test case
        // FixedTextMergerWithIf, where this is harmful.
        // previousElementList = null;
    }
}

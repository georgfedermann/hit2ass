package org.poormanscastle.products.hit2ass.transformer;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
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
 *
 * @deprecated Is not needed any more with the new parser strategy of respecting
 * blanks and new lines and keeping them for later processing stages.
 */
public class InsertBlanksVisitor extends AstItemVisitorAdapter {

    /**
     * This stores a reference to the direct ancestor of the current element.
     */
    private PairClouBausteinElementList previousElementList;

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

    @Override
    public void visitPairClouBausteinElementList(PairClouBausteinElementList pairClouBausteinElementList) {
        if ((pairClouBausteinElementList.getHead() instanceof FixedText || pairClouBausteinElementList.getHead() instanceof PrintStatement) && lineBreakFlag) {
            // if there was a line break since the last fixed text or print statement, do nothing. Just consume
            // the line break flag and go on.
            lineBreakFlag = false;
        } else {
            if (pairClouBausteinElementList.getHead() instanceof FixedText && previousElementList != null && previousElementList.getHead() instanceof FixedText && !((FixedText) previousElementList.getHead()).getText().endsWith((" "))) {
                // cover the case where e.g. an IF or WHILE structure directly follows a fixed text, and the structure contains a fixed text.
                // Then a blank needs to be added to the previous word / fixed text.
                FixedText previousFixedText = (FixedText) previousElementList.getHead();
                String previousContent = previousFixedText.getText();
                String thisContent = ((FixedText) pairClouBausteinElementList.getHead()).getText();
                if (!Pattern.matches("\\p{Punct}|\"", thisContent.substring(0, 1))) {
                    previousFixedText.reset();
                    previousFixedText.appendText(StringUtils.join(previousContent, " "), false);
                }
            } else if (pairClouBausteinElementList.getHead() instanceof FixedText && previousElementList != null && previousElementList.getHead() instanceof PrintStatement) {
                // add a blank to the start of a fixed text if the previous element was a PrintStatement
                FixedText fixedText = (FixedText) pairClouBausteinElementList.getHead();
                String content = fixedText.getText();
                if (!Pattern.matches("\\p{Punct}|\"", content.substring(0, 1))) {
                    fixedText.reset();
                    fixedText.appendText(content, true);
                }
            } else if (pairClouBausteinElementList.getHead() instanceof PrintStatement && previousElementList != null && previousElementList.getHead() instanceof FixedText) {
                FixedText fixedText = (FixedText) previousElementList.getHead();
                if (!fixedText.getText().endsWith("\"")) {
                    fixedText.appendText(" ", false);
                }
            } else if (previousElementList != null &&
                    previousElementList.getHead() instanceof PrintStatement && pairClouBausteinElementList.getHead() instanceof PrintStatement) {
                // in case of a direct sequence of PrintStatements, separate the PrintStatements with a FixedText element containing a blank.
                previousElementList.setTail(new PairClouBausteinElementList(FixedText.create(CodePosition.createZeroPosition(), " "), pairClouBausteinElementList));
            }
        }
        // keep in memory whether there already was a text output in the current AST branch
        if (pairClouBausteinElementList.getHead() instanceof FixedText || pairClouBausteinElementList.getHead() instanceof PrintStatement) {
            previousElementList = pairClouBausteinElementList;
        }
    }

    @Override
    public void visitLastClouBausteinElementList(LastClouBausteinElementList lastClouBausteinElementList) {
        // for explanations and comments, see visitPairClouBausteinElementList()
        if (lastClouBausteinElementList.getHead() instanceof FixedText && previousElementList != null && previousElementList.getHead() instanceof PrintStatement) {
            FixedText fixedText = (FixedText) lastClouBausteinElementList.getHead();
            String content = fixedText.getText();
            if (!Pattern.matches("\\p{Punct}|\"", content.substring(0, 1))) {
                fixedText.reset();
                fixedText.appendText(content, true);
            }
        } else if (lastClouBausteinElementList.getHead() instanceof PrintStatement && previousElementList != null && previousElementList.getHead() instanceof FixedText) {
            FixedText fixedText = (FixedText) previousElementList.getHead();
            if (!fixedText.getText().endsWith("\"")) {
                fixedText.appendText(" ", false);
            }
        } else if (previousElementList != null &&
                previousElementList.getHead() instanceof PrintStatement && lastClouBausteinElementList.getHead() instanceof PrintStatement) {
            previousElementList.setTail(new PairClouBausteinElementList(FixedText.create(CodePosition.createZeroPosition(), " "), lastClouBausteinElementList));
        }
        previousElementList = null;
    }


}

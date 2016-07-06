package org.poormanscastle.products.hit2ass.transformer;

import org.poormanscastle.products.hit2ass.ast.domain.AstItemVisitorAdapter;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.ast.domain.IncludeBausteinStatement;
import org.poormanscastle.products.hit2ass.exceptions.BausteinMergerException;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.tools.HitAssTools;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Scans a CLOU AST for #B Baustein imports. When found one the
 * ClouBausteinMergerVisitor will use a instance of HitAssAstParser to
 * parse the referenced CLOU Baustein and then import the content
 * of the given Baustein into the place where the import element
 * was before.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/27/16.
 */
public class ClouBausteinMergerVisitor extends AstItemVisitorAdapter {

    private final static Logger logger = Logger.getLogger(ClouBausteinMergerVisitor.class);

    @Override
    public void visitIncludeBausteinStatement(IncludeBausteinStatement includeBausteinStatement) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info(StringUtils.join("Found CLOU Baustein ", includeBausteinStatement.getPathToBaustein()));
            }
            // the following two lines deal with the issue that legacy Bausteins refer to child Bausteins with a
            // lengthy path which should not be necessary in the new system, where the system is configured with the
            // path to the HIT / CLOU Baustein library.
            String[] bausteinCoordinates = includeBausteinStatement.getPathToBaustein().replaceAll("\"", "").split("/");
            String bausteinName = bausteinCoordinates[bausteinCoordinates.length - 1];
            ClouBaustein baustein = new HitAssAstParser(
                    HitAssTools.getClouBausteinAsInputStream(bausteinName), System.getProperty("hit2ass.clou.encoding")).CB();
            baustein.accept(new FixedTextMerger());
            includeBausteinStatement.setContent(baustein.getClouBausteinElement());
        } catch (Throwable e) {
            throw new BausteinMergerException(StringUtils.join("Could not parse child Baustein ", includeBausteinStatement.getPathToBaustein(), " because: ", e.getMessage()), e);
        }
    }

}

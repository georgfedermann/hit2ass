package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by georg.federmann@poormanscastle.com on 4/18/16.
 */
public class CaseStatementImpl extends AbstractAstItem implements CaseStatement {

    private final String match;

    private final ClouBausteinElement clouBausteinElement;

    public CaseStatementImpl(CodePosition codePosition, String match, ClouBausteinElement clouBausteinElement) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(match));
        // 2016-07-04 15:17 ClouBausteinElement can be null, e.g. in default block, you can just write: /:
        // checkNotNull(clouBausteinElement);
        match = match.trim();
        checkArgument(match.startsWith("/"));
        match = StringUtils.stripStart(match, "/");
        match = StringUtils.stripEnd(match, ":");
        match = StringUtils.strip(match, "\"");
        this.match = match.trim();
        this.clouBausteinElement = clouBausteinElement;
    }

    public CaseStatementImpl(String match, ClouBausteinElement clouBausteinElement) {
        this(clouBausteinElement.getCodePosition(), match, clouBausteinElement);
    }

    @Override
    public String getMatch() {
        return match;
    }

    @Override
    public ClouBausteinElement getClouBausteinElement() {
        return clouBausteinElement;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithCaseStatementImpl(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitCaseStatementImpl(this);
        // clouBausteinElement may be null if the CaseStatement was empty, for what reason ever.
        // Does not seem to make much sense to implement CASEs with an empty body in HIT/CLOU, because
        // branches are not accumulating up to the next break. But such legacy code can be found.
        if (clouBausteinElement != null && clouBausteinElement.handleProceedWith(visitor)) {
            clouBausteinElement.accept(visitor);
        }
        visitor.leaveCaseStatementImpl(this);
    }

    @Override
    public String toString() {
        return "CaseStatementImpl{" +
                "codePosition=" + getCodePosition() +
                ", match=" + match +
                ", clouBausteinElement=" + clouBausteinElement +
                '}';
    }

}

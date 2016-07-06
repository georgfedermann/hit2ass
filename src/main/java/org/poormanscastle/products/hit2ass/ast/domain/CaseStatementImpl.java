package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
        this.match = match;
        this.clouBausteinElement = clouBausteinElement;
    }

    public CaseStatementImpl(String match, ClouBausteinElement clouBausteinElement) {
        this(clouBausteinElement.getCodePosition(), match, clouBausteinElement);
    }

    public String getMatch() {
        return match;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithCaseStatementImpl(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitCaseStatementImpl(this);
        if (clouBausteinElement.handleProceedWith(visitor)) {
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

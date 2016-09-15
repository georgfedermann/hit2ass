package org.poormanscastle.products.hit2ass.ast.domain;

/**
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public class ClouBausteinImpl extends AbstractAstItem implements ClouBaustein {

    private final ClouBausteinElement clouBausteinElement;

    /**
     * @param codePosition colum/row position
     * @param clouBausteinElement may be {@code null} if the ClouBaustein is empty.
     */
    public ClouBausteinImpl(CodePosition codePosition, ClouBausteinElement clouBausteinElement) {
        super(codePosition);
        this.clouBausteinElement = clouBausteinElement;
    }

    public ClouBausteinImpl(ClouBausteinElement clouBausteinElement) {
        this(clouBausteinElement.getCodePosition(), clouBausteinElement);
    }

    @Override
    public ClouBausteinElement getClouBausteinElement() {
        return clouBausteinElement;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithClouBaustein(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitClouBaustein(this);
        if (clouBausteinElement != null && clouBausteinElement.handleProceedWith(visitor)) {
            clouBausteinElement.accept(visitor);
        }
        visitor.leaveClouBaustein(this);
    }

    @Override
    public String toString() {
        return "ClouBausteinImpl{" +
                "codePosition=" + getCodePosition() +
                ", clouBausteinElement=" + clouBausteinElement +
                '}';
    }
}

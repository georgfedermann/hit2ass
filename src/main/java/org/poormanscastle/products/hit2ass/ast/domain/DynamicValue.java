package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * CLOU Command: #X< variableName
 * This is the mapping from #X< (which stands for: read sequentially from file) to Dynamic values
 * in DocDesign Workspaces / Templates.
 * <p>
 * To map this element to Workspaces, I will need to find a way to map the strategy of
 * sequential reading from a data file in HIT/CLOU to XPaths in DocDesign.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/14/16.
 */
public class DynamicValue extends AbstractAstItem implements Statement {

    /**
     * This field gets assigned the HIT/CLOU variable name into which the value from
     * the input data file is written. Must not be null or empty.
     */
    private final String name;

    public DynamicValue(CodePosition codePosition, String name) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(name));
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithDynamicValue(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitDynamicValue(this);

        visitor.leaveDynamicValue(this);
    }

    @Override
    public String toString() {
        return "DynamicValue{" +
                "codePosition=" + getCodePosition() +
                ", name='" + name + '\'' +
                '}';
    }

}

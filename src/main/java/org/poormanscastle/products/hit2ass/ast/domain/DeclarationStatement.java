package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Declares the id and the scope of a variable. CLOU variables are not typed; they
 * can be assigned numeral and text values. Also, they always get initialized to
 * some default value. If no meaningful value can be given com declaration time, it
 * is custom to initialize variables to 0 or "", depending on if they shall be
 * interpreted as numeral or textual values.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 07.04.2016.
 */
public abstract class DeclarationStatement extends AbstractAstItem implements Statement {

    /**
     * The name of the symbol, will be used to refer to the declared variable.
     */
    private final String id;

    /**
     * This expression will be evaluated to determine the initial value of the declared symbol.
     */
    private final Expression expression;

    /**
     * Defines how the variable's value shall be layed out in the document.
     * Can be left empty if not specified in the HIT/CLOU text component.
     */
    private final String formatDefinition;

    public DeclarationStatement(CodePosition codePosition, String id, Expression expression, String formatDefinition) {
        super(codePosition);
        checkNotNull(expression);
        checkArgument(!StringUtils.isBlank(id));
        this.id = id;
        this.expression = expression;
        this.formatDefinition = formatDefinition;
    }

    public DeclarationStatement(String id, Expression expression) {
        this(expression.getCodePosition(), id, expression, "");
    }

    public DeclarationStatement(String id, Expression expression, String formatDefinition) {
        this(expression.getCodePosition(), id, expression, formatDefinition);
    }

    public Expression getExpression() {
        return expression;
    }

    public String getId() {
        return id;
    }

    public String getFormatDefinition() {
        return formatDefinition;
    }

    @Override
    public String toString() {
        return "DeclarationStatement{" +
                "codePosition=" + getCodePosition() +
                ", expression=" + expression +
                ", id='" + id + '\'' +
                ", formatDefinition='" + formatDefinition + '\'' +
                '}';
    }
}

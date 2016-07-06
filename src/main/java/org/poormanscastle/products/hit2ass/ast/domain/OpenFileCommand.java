package org.poormanscastle.products.hit2ass.ast.domain;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * HIT/CLOU text components support file IO, which will not be mapped to DocFamily workspaces, but
 * potentially to other output formats (?). Depending on the target system, this command might well
 * be just ignored.
 *
 * Created by georg.federmann@poormanscastle.com on 13.04.16.
 */
public class OpenFileCommand extends AbstractAstItem implements Statement{

    private final String fileName;

    public OpenFileCommand(CodePosition codePosition, String fileName) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(fileName));
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithOpenFileCommand(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitOpenFileCommand(this);

        visitor.leaveOpenFileCommand(this);
    }

    @Override
    public String toString() {
        return "OpenFileCommand{" +
                "codePosition=" + getCodePosition() +
                ", fileName='" + fileName + '\'' +
                '}';
    }

}

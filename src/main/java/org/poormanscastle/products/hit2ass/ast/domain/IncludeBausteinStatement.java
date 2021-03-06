package org.poormanscastle.products.hit2ass.ast.domain;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents the statement to import another sub CLOU text component
 * into the current text component com the place where this statement occurs.
 * This statement gets hooked into the AST as is. It's the duty of a later
 * phase of AST processing to replace this statement with the actual CLOU
 * text component, after it was parsed into an AST and processed itself, of course.
 * Created by georg.federmann@poormanscastle.com on 12.04.2016.
 */
public class IncludeBausteinStatement extends AbstractAstItem implements Statement {

    /**
     * the path to the CLOU Baustein to be imported into the parent Baustein. This must be a valid path
     * that actually can be resolved in the file system. In particular pathToBaustein cannot be empty or null.
     */
    private final String pathToBaustein;

    /**
     * to be set by the ClouBausteinDependencyResolverVisitor when the information
     * becomes available. This information is parked here for later retrieval
     * by the IRTransformer.
     */
    private String moduleDockName;

    /**
     * to be set by the ClouBausteinDependencyResolverVisitor when the information
     * becomes available. This information is parked here for later retrieval
     * by the IRTransformer.
     */
    private String calledModuleName;

    /**
     * to be set by the ClouBausteinDependencyResolverVisitor when the information
     * becomes available. This information is parked here for later retrieval
     * by the IRTransformer.
     */
    private String calledModuleElementId;

    /**
     * The content will be added com the time when the containing Baustein was parsed
     * into an AST and is being processed in the after-parsing processing before rendering
     * to a DocDesign Workspace takes place.
     */
    private ClouBausteinElement content;

    private boolean localModuleReferences;

    public IncludeBausteinStatement(CodePosition codePosition, String pathToBaustein) {
        super(codePosition);
        checkArgument(!StringUtils.isBlank(pathToBaustein));
        this.pathToBaustein = pathToBaustein;
    }

    public void setLocalModuleReferences(boolean localModuleReferences) {
        this.localModuleReferences = localModuleReferences;
    }

    public boolean isLocalModuleReferences() {
        return localModuleReferences;
    }

    public String getPathToBaustein() {
        return pathToBaustein;
    }

    public ClouBausteinElement getContent() {
        return content;
    }

    public void setContent(ClouBausteinElement content) {
        this.content = content;
    }

    @Override
    public boolean handleProceedWith(AstItemVisitor visitor) {
        return visitor.proceedWithIncludeBausteinStatement(this);
    }

    @Override
    public void accept(AstItemVisitor visitor) {
        visitor.visitIncludeBausteinStatement(this);
        if (content != null && content.handleProceedWith(visitor)) {
            content.accept(visitor);
        }
        visitor.leaveIncludeBausteinStatement(this);
    }

    @Override
    public String toString() {
        return "IncludeBausteinStatement{" +
                "pathToBaustein='" + pathToBaustein + '\'' +
                ", moduleDockName='" + moduleDockName + '\'' +
                ", calledModuleName='" + calledModuleName + '\'' +
                ", calledModuleElementId='" + calledModuleElementId + '\'' +
                ", content=" + content +
                ", localModuleReferences=" + localModuleReferences +
                '}';
    }

    public String getModuleDockName() {
        return moduleDockName;
    }

    public void setModuleDockName(String moduleDockName) {
        this.moduleDockName = moduleDockName;
    }

    public String getCalledModuleName() {
        return calledModuleName;
    }

    public void setCalledModuleName(String calledModuleName) {
        this.calledModuleName = calledModuleName;
    }

    public String getCalledModuleElementId() {
        return calledModuleElementId;
    }

    public void setCalledModuleElementId(String calledModuleElementId) {
        this.calledModuleElementId = calledModuleElementId;
    }
}

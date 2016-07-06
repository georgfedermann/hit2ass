package org.poormanscastle.products.hit2ass.ast.domain;

import java.io.Serializable;

/**
 * An AstItem is part of an abstract syntax tree representing a HIT/CLOU text component.
 * <p/>
 * Additionally, this interface supports traversability for implementations of an AstVisitor,
 * implementing different aspects of the HIT/CLOU AstTree, like semantic analysis,
 * expression evaluation, and translation to DocDesign workspaces.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public interface AstItem extends Serializable {

    /**
     * this method can decide what to do in case a visitor comes knocking, how to point the visitor
     * to child elements in the tree hierarchy, or choose to remain empty.
     *
     * @param visitor a visitor like in visitor pattern
     */
    void accept(AstItemVisitor visitor);

    /**
     * AstItems can have several child elements of various implementations of the same interface, like
     * OpPlus, OpMinus, OpTimes, OpDiv all implementing the Op interface. Implementing one method
     * proceedWith(AstItem) or only proceedWith(Op) to let the visitor decide whether to proceed
     * with the given item or operator implementation will not work, since method overloading in Java
     * only support static polymorphism, i.e. static binding. So, for each implementation of AstItem or
     * Op, one and the same method for the static type will be called. So, the visitor will have to
     * do instanceof checks and type casts and switch logic, which results in ugly code.
     * To avoid all of these, each AstItem implements handleProceedWith(AstVisitor). Now, the implementation
     * of this method will be resolved via dynamic polymorphism, once again delegating the decision logic
     * to dynamic binding and freeing the implementation of instanceof checks, type casts and switch logic.
     *
     * @param visitor visitor like in visitor pattern
     * @return returns {@code true} if the visitor shall proceed with this item, {@code false} otherwise.
     */
    boolean handleProceedWith(AstItemVisitor visitor);

    /**
     * to facilitate enhanced parser error messages during semantic checks etc. an AST item
     * knows the locations of the tokens within the source code from which it was inferred.
     *
     * @return this AstItem's code position
     */
    CodePosition getCodePosition();
}

package org.poormanscastle.products.hit2ass.renderer;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.hit2ass.ast.domain.BinaryOperator;
import org.poormanscastle.products.hit2ass.ast.domain.BinaryOperatorExpression;
import org.poormanscastle.products.hit2ass.ast.domain.Expression;
import org.poormanscastle.products.hit2ass.ast.domain.ExpressionList;
import org.poormanscastle.products.hit2ass.ast.domain.IdExpression;
import org.poormanscastle.products.hit2ass.ast.domain.LastExpressionList;
import org.poormanscastle.products.hit2ass.ast.domain.PairExpressionList;
import org.poormanscastle.products.hit2ass.renderer.domain.Content;
import org.poormanscastle.products.hit2ass.renderer.domain.DynamicContentReference;
import org.poormanscastle.products.hit2ass.renderer.domain.FontWeight;
import org.poormanscastle.products.hit2ass.renderer.domain.ListAddItem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Transforms a list expression into a sequence of DynamicContentReference instances.
 * E.g. in the statement #L newList D someOldList & anotherOldList & { 1, 2, 3, 4, 5 }
 * the list concatenation of two list objects and a statically created list needs to be
 * transformed into a sequence of hit2assext:appendList and hit2assext:addListValue
 * statements. These hit2assext statements are called via DynamicContentReference elements.
 * This type is a visitor that traverses the expression used to initialize the list
 * or to perform a list concatenation. The result is a list of DynamicContentReference
 * elements that can be added to the IRTree right after the creation of the given
 * list, or wherever it seems fit.
 * Created by georg.federmann@poormanscastle.org on 8/4/16.
 */
public class ListExpressionTransformer {

    private final static Logger logger = Logger.getLogger(ListExpressionTransformer.class);

    private final List<Content> contentList = new LinkedList<>();

    public ListExpressionTransformer() {
    }

    public List<Content> getContentList() {
        return Collections.unmodifiableList(contentList);
    }

    public void transformExpression(Expression expression, String targetListName, FontWeight fontWeight) {
        if (expression == null) {
            // Nothing to do here.
        } else if (expression instanceof ExpressionList) {
            // in case of static list declaration as in { 1, 2, 3, 4, 5 } add statements to add the statically declared list items.
            ExpressionList expressionList = (ExpressionList) expression;
            while (expressionList != null) {
                if (expressionList instanceof PairExpressionList) {
                    PairExpressionList pairExpressionList = (PairExpressionList) expressionList;
                    contentList.add(new ListAddItem("AddItem", targetListName, pairExpressionList.getHead().toXPathString()));
                    expressionList = pairExpressionList.getTail();
                } else if (expressionList instanceof LastExpressionList) {
                    LastExpressionList lastExpressionList = (LastExpressionList) expressionList;
                    contentList.add(new ListAddItem("AddItem", targetListName, lastExpressionList.getHead().toXPathString()));
                    expressionList = null;
                }
            }
        } else if (expression instanceof IdExpression) {
            // handling this case: #L newList D existingList. Kind of copy-constructor to replicate newList from existingList.
            IdExpression sourceListExpression = (IdExpression) expression;
            contentList.add(new DynamicContentReference(StringUtils.join(
                    "Append List ", sourceListExpression.getId(), " to ", targetListName),
                    StringUtils.join(" hit2assext:appendList(var:read('renderSessionUuid'), '", sourceListExpression.getId(),
                            "', '", targetListName, "') "), fontWeight));
        } else if (expression instanceof BinaryOperatorExpression) {
            // handling this case: #L newList D oldList1 & oldList2 & { 1, 2, 3, 4, 5 }, recusively of course.
            BinaryOperatorExpression addExpression = (BinaryOperatorExpression) expression;
            checkArgument(addExpression.getOperator() == BinaryOperator.AND, "Only list concatenation operator & is allowed here, not this such: ",
                    addExpression.getOperator().getLabel(), ".");
            transformExpression(addExpression.getLhs(), targetListName, fontWeight);
            transformExpression(addExpression.getRhs(), targetListName, fontWeight);
        }
    }

}

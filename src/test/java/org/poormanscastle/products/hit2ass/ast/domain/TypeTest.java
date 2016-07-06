package org.poormanscastle.products.hit2ass.ast.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.poormanscastle.products.hit2ass.ast.domain.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by georg.federmann@poormanscastle.com on 05.04.2016.
 */
public class TypeTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAreTypesCompatible() throws Exception {
        assertTrue(Type.areTypesCompatible(Type.BOOLEAN, Type.BOOLEAN));
        assertTrue(Type.areTypesCompatible(Type.INT, Type.INT));
        assertTrue(Type.areTypesCompatible(Type.INT, Type.DOUBLE));
        assertTrue(Type.areTypesCompatible(Type.DOUBLE, Type.DOUBLE));
        assertTrue(Type.areTypesCompatible(Type.DOUBLE, Type.INT));
        assertTrue(Type.areTypesCompatible(Type.TEXT, Type.BOOLEAN));
        assertTrue(Type.areTypesCompatible(Type.TEXT, Type.INT));
        assertTrue(Type.areTypesCompatible(Type.TEXT, Type.DOUBLE));
        assertTrue(Type.areTypesCompatible(Type.TEXT, Type.TEXT));

        assertFalse(Type.areTypesCompatible(Type.BOOLEAN, Type.INT));
        assertFalse(Type.areTypesCompatible(Type.BOOLEAN, Type.DOUBLE));
    }

    @Test
    public void testGetInferredType() throws Exception {
        assertEquals(Type.DOUBLE, Type.getInferredType(Type.INT, Type.DOUBLE));
        assertEquals(Type.INT, Type.getInferredType(Type.INT, Type.INT));
        assertEquals(Type.BOOLEAN, Type.getInferredType(Type.BOOLEAN, Type.BOOLEAN));
        assertEquals(Type.TEXT, Type.getInferredType(Type.TEXT, Type.BOOLEAN));
        assertEquals(Type.TEXT, Type.getInferredType(Type.TEXT, Type.INT));
        assertEquals(Type.TEXT, Type.getInferredType(Type.TEXT, Type.DOUBLE));
        assertEquals(Type.TEXT, Type.getInferredType(Type.TEXT, Type.TEXT));
    }

    @Test
    public void testBooleanWithInteger() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("The two types BOOLEAN and INT are incompatible.");
        Type.getInferredType(Type.BOOLEAN, Type.INT);
    }

    @Test
    public void testBooleanWithDouble() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("The two types BOOLEAN and DOUBLE are incompatible.");
        Type.getInferredType(Type.BOOLEAN, Type.DOUBLE);
    }

    @Test
    public void testIsRhsAssignableToLhs() throws Exception {
        assertTrue(Type.isRhsAssignableToLhs(Type.BOOLEAN, Type.BOOLEAN));
        assertFalse(Type.isRhsAssignableToLhs(Type.BOOLEAN, Type.INT));
        assertFalse(Type.isRhsAssignableToLhs(Type.BOOLEAN, Type.DOUBLE));
        assertFalse(Type.isRhsAssignableToLhs(Type.BOOLEAN, Type.TEXT));

        assertFalse(Type.isRhsAssignableToLhs(Type.INT, Type.BOOLEAN));
        assertTrue(Type.isRhsAssignableToLhs(Type.INT, Type.INT));
        assertFalse(Type.isRhsAssignableToLhs(Type.INT, Type.DOUBLE));
        assertFalse(Type.isRhsAssignableToLhs(Type.INT, Type.TEXT));

        assertFalse(Type.isRhsAssignableToLhs(Type.DOUBLE, Type.BOOLEAN));
        assertTrue(Type.isRhsAssignableToLhs(Type.DOUBLE, Type.INT));
        assertTrue(Type.isRhsAssignableToLhs(Type.DOUBLE, Type.DOUBLE));
        assertFalse(Type.isRhsAssignableToLhs(Type.DOUBLE, Type.TEXT));

        assertFalse(Type.isRhsAssignableToLhs(Type.TEXT, Type.BOOLEAN));
        assertFalse(Type.isRhsAssignableToLhs(Type.TEXT, Type.INT));
        assertFalse(Type.isRhsAssignableToLhs(Type.TEXT, Type.DOUBLE));
        assertTrue(Type.isRhsAssignableToLhs(Type.TEXT, Type.TEXT));
    }

}
package dedi.configuration.calculations.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dedi.configuration.calculations.NumericRange;

public class NumericRangeTest {
	private NumericRange range1;
	private NumericRange range2;
	private NumericRange range3;
	private NumericRange range4;
	private NumericRange range5;
	
	
	@Before
	public void setUp() throws Exception {
		range1 = new NumericRange(10, 9);
		range2 = new NumericRange(-1, -5);
		range3 = new NumericRange(5, -5);
		range4 = new NumericRange(1,  2);
		range5 = new NumericRange(1, 1);
	}

	
	@After
	public void tearDown() throws Exception {
		range1 = null;
		range2 = null;
		range3 = null;
		range4 = null;
		range5 = null;
	}

	
	@Test
	public void constructorOfNumericRangeShouldEnsureMinLessThanMax() {
		assertEquals("The minimum of NumericRange(10, 9) should be 9.", 9, range1.getMin(), 0.00);
		assertEquals("The minimum of NumericRange(10, 9) should be 10.", 10, range1.getMax(), 0.00);
		
		assertEquals("The minimum of NumericRange(-1, -5) should be -5.", -5, range2.getMin(), 0.00);
		assertEquals("The maximum of NumericRange(-1, -5) should be -1.", -1, range2.getMax(), 0.00);
		
		assertEquals("The minimum of NumericRange(5, -5) should be -5.", -5, range3.getMin(), 0.00);
		assertEquals("The maximum of NumericRange(5, -5) should be 5.", 5, range3.getMax(), 0.00);
		
		assertEquals("The minimum of NumericRange(1,  2) should be 1.", 1, range4.getMin(), 0.00);
		assertEquals("The maximum of NumericRange(1,  2) should be 2.", 2, range4.getMax(), 0.00);
		
		assertEquals("The minimum of NumericRange(1, 1) should be 1.", 1, range5.getMin(), 0.00);
		assertEquals("The maximum of NumericRange(1, 1) should be 1.", 1, range5.getMax(), 0.00);
	}
	
	
	@Test
	public void testContainsWithAValueThatIsContained() {
		assertTrue("NumericRange(10, 9) should contain the left end point.", range1.contains(9));
		assertTrue("NumericRange(10, 9) should contain the right end point.", range1.contains(10));
		assertTrue("NumericRange(10, 9) should contain all intermediate values, e.g. 9.7.", range1.contains(9.7));
		assertTrue("NumericRange(-1, -5) should contain -2.", range2.contains(-2));
		assertTrue("NumericRange(5, -5) should contain 0.", range3.contains(0));
		assertTrue("NumericRange consisting of a single point should contain that point.", range5.contains(1));
	}
	
	
	@Test
	public void testContainsWithAValueGreaterThenMax() {
		assertFalse("NumericRange(10, 9) should not contain 10.1.", range1.contains(10.1));
	}

	
	@Test
	public void testContainsWithAValueLessThanMin() {
		assertFalse("NumericRange(10, 9) should not contain 8.9.", range1.contains(8.9));
	}
	
	
	@Test
	public void containsShouldReturnTrueGivenAnEmptySet() {
		assertTrue("Null should be contained in any range.", range1.contains(null));
	}
	
	
	@Test
	public void testContainsWithRangeThatIntersectsButIsNotContained() {
		assertFalse("[9, 10] shouldn't contain [10, 10.2].", range1.contains(new NumericRange(10, 10.2)));
		assertFalse("[9, 10] shouldn't contain [9.5, 10.5].", range1.contains(new NumericRange(9.5, 10.5)));
		assertFalse("[9, 10] shouldn't contain [8.5, 11].", range1.contains(new NumericRange(8.5, 11)));
	}
	
	
	@Test
	public void testContainsWithRangeThatIsNotContainedAndDoesNotIntersect() {
		assertFalse("[9, 10] shouldn't contain [7, 8.9].", range1.contains(new NumericRange(7, 8.9)));
	}
	
	
	@Test
	public void testContainsWithRangeThatIsAProperSubset() {
		assertTrue("[9, 10] should contain [9.1, 9.9].", range1.contains(new NumericRange(9.1, 9.9)));
	}
	
	
	@Test
	public void anyRangeShouldContainItself() {
		assertTrue("[9, 10] should contain [9, 10].", range1.contains(new NumericRange(9, 10)));
		assertTrue("{1} should contain {1}", range5.contains(new NumericRange(1, 1)));
	}
	
	
	@Test
	public void anyRangeShouldEqualItself() {
		assertTrue(range1.equals(range1));
	}
	
	
	@Test
	public void anyRangeShouldEqualARangeWithTheSameEndPoints() {
		assertTrue(range1.equals(new NumericRange(9, 10)));
	}
	
	
	@Test
	public void aNonNullRangeShouldNotEqualTheEmptySet() {
		assertFalse(range1.equals(null));
	}
	
	
	@Test
	public void testEqualsWhenNotEqual() {
		assertFalse(range1.equals(new NumericRange(10, 11)));
		assertFalse(range1.equals(new NumericRange(9, 11)));
		assertFalse(range1.equals(new NumericRange(8.9, -1)));
		assertFalse(range1.equals(new NumericRange(9, 9.7)));
	}
	
	
	@Test
	public void intersectionWithEmptySetShouldBeEmpty() {
		assertNull(range1.intersect(null));
	}
	
	
	@Test
	public void intersectionShouldBeNullIfRangesDontIntersect() {
		assertNull(range1.intersect(range2));
	}
	
	
	@Test
	public void testIntersectionWithAProperSubset() {
		NumericRange range = new NumericRange(9.5, 9.7);
		assertEquals(range, range1.intersect(range));
	}
	
	
	@Test
	public void testIntersectionWhenTheyIntersectAtASinglePoint() {
		NumericRange range = new NumericRange(10, 11);
		assertEquals(new NumericRange(10, 10), range1.intersect(range));
	}
	
	
	@Test
	public void testIntersectionOfARangeWithItself() {
		assertEquals(range1, range1.intersect(range1));
	}
	
	
	@Test
	public void testIntersectionWhenTheyOverlap() {
		assertEquals(new NumericRange(9.5, 10), range1.intersect(new NumericRange(9.5, 11.5)));
	}
	
	
	@Test
	public void intersectionShouldBeCommutative() {
		NumericRange range = new NumericRange(9.5, 9.7);
		assertEquals(range, range1.intersect(range));
		assertEquals(range, range.intersect(range1));
		
		assertNull(range1.intersect(range2));
		assertNull(range2.intersect(range1));
	}
}

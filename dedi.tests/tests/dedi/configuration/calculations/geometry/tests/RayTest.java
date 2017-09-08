package dedi.configuration.calculations.geometry.tests;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.vecmath.Vector2d;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dedi.configuration.calculations.NumericRange;
import dedi.configuration.calculations.geometry.Ray;

public class RayTest {
	private Ray hRay; // horizontal ray
	private Ray vRay; // vertical ray
	private Ray sRay; // slanting ray

	
	@Before
	public void setUp() throws Exception {
		hRay = new Ray(new Vector2d(2, 0), new Vector2d(1, 1)); 
		vRay = new Ray(new Vector2d(0, 2), new Vector2d(1, 1));
		sRay = new Ray(new Vector2d(1, 2), new Vector2d(1, 1));
	}

	
	@After
	public void tearDown() throws Exception {
		hRay = null;
		vRay = null;
		sRay = null;
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void whenConstructingRayShouldThrowExceptionWhenDirectionIsTheZeroVector() {
		new Ray(new Vector2d(0, 0), new Vector2d(1, 1)); 
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void whenSettingDirectionShouldThrowExceptionWhenDirectionIsTheZeroVector() {
		hRay.setDirection(new Vector2d(0, 0));
	}

	
	@Test
	public void testSettingDirectionWithValidDirection() {
		hRay.setDirection(new Vector2d(1, 0));
		assertEquals(new Vector2d(1, 0),  hRay.getDirection());
	}
	
	
	@Test
	public void testChangingInitialPt() {
		hRay.setInitialPt(new Vector2d(0, 0));
		assertEquals(new Vector2d(0, 0),  hRay.getInitialPt());
	}
	
	
	@Test
	public void whenGettingPointShouldReturnNullIfParameterLessThanZero() {
		assertNull(hRay.getPt(-1));
	}
	
	
	@Test
	public void whenGettingPointShouldReturnInitialPointWithParameterEqualZero() {
		assertEquals(new Vector2d(1, 1),  hRay.getPt(0));
	}
	
	
	@Test
	public void testGetPtWithValidNonZeroParameter() {
		assertEquals(new Vector2d(5, 1),  hRay.getPt(2));
	}
	
	
	@Test
	public void whenGettingPointAtDistanceShouldReturnNullIfDistanceLessThanZero() {
		assertNull(hRay.getPtAtDistance(-1));
	}
	
	
	@Test
	public void whenGettingPointAtDistanceShouldReturnInitialPointWhenDistanceEqualsZero() {
		assertEquals(new Vector2d(1, 1),  hRay.getPtAtDistance(0));
	}
	
	
	@Test
	public void testGetPtAtDistanceWithValidNonZeroDistance() {
		assertEquals(new Vector2d(3, 1),  hRay.getPtAtDistance(2));
	}
	
	
	@Test
	public void whenGettingParameterRangeShouldReturnNullIfGivenRangeNull() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParameterRange = Ray.class.getDeclaredMethod("getParameterRange", NumericRange.class);
		getParameterRange.setAccessible(true);
		assertNull(getParameterRange.invoke(hRay, (Object) null));
	}
	
	
	@Test
	public void whenGettingParameterRangeShouldReturnNullIfGivenRangeIsNotInZeroToInfinity() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParameterRange = Ray.class.getDeclaredMethod("getParameterRange", double.class, double.class);
		getParameterRange.setAccessible(true);
		assertNull(getParameterRange.invoke(hRay, -3, -1));
	}
	
	
	@Test
	public void testGetParameterRangeWithOneArgumentLessThanZero() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParameterRange = Ray.class.getDeclaredMethod("getParameterRange", double.class, double.class);
		getParameterRange.setAccessible(true);
		assertEquals(new NumericRange(0, 2), getParameterRange.invoke(hRay, 2, -1));
	}
	
	
	@Test
	public void testGetParameterRangeWithBothArgumentsZero() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParameterRange = Ray.class.getDeclaredMethod("getParameterRange", double.class, double.class);
		getParameterRange.setAccessible(true);
		assertEquals(new NumericRange(0, 0), getParameterRange.invoke(hRay, 0, 0));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForHorizontalRayPassingThroughStartingOutsideRectange() {
		assertEquals(new NumericRange(1, 2), hRay.getRectangleIntersectionParameterRange(new Vector2d(3, 5), 2, 4));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForHorizontalRayNotPassingThrough() {
		assertEquals(null, hRay.getRectangleIntersectionParameterRange(new Vector2d(3, 5), 2, 2));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForHorizontalRayPassingThroughStartingInsideRectangle() {
		assertEquals(new NumericRange(0, 2), hRay.getRectangleIntersectionParameterRange(new Vector2d(0, 5), 5, 5));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForVerticalRayPassingThroughStartingOutsideRectange() {
		assertEquals(new NumericRange(0, 2), vRay.getRectangleIntersectionParameterRange(new Vector2d(1, 5), 2, 4));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForVerticalRayNotPassingThrough() {
		assertEquals(null, vRay.getRectangleIntersectionParameterRange(new Vector2d(4, 5), 2, 2));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForVerticalRayPassingThroughStartingInsideRectangle() {
		assertEquals(new NumericRange(0, 2), vRay.getRectangleIntersectionParameterRange(new Vector2d(0, 5), 5, 5));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForSlantingRayPassingThroughStartingOutsideRectange() {
		assertEquals(new NumericRange(0, 1), sRay.getRectangleIntersectionParameterRange(new Vector2d(1, 3), 2, 2));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForSlantingRayNotPassingThrough() {
		assertEquals(null, sRay.getRectangleIntersectionParameterRange(new Vector2d(3, 3), 2, 2));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForSlantingRayPassingThroughStartingInsideRectangle() {
		assertEquals(new NumericRange(0, 1), sRay.getRectangleIntersectionParameterRange(new Vector2d(0, 3), 4, 3));
	}
	
	
	@Test
	public void testGettingRectangleIntersectionForSlantingRayIntersectinAtAPoint() {
		assertEquals(new NumericRange(1, 1), sRay.getRectangleIntersectionParameterRange(new Vector2d(2, 3), 2, 2));
	}
	
	
	@Test
	public void testGettingCircleIntersectionParameterRangeForHorizontalRayPassigThroughStartingInsideCircle() {
		assertEquals(new NumericRange(0, 0.5), hRay.getCircleIntersectionParameterRange(1, new Vector2d(1, 1)));
	}
	
	
	@Test
	public void testGettingCircleIntersectionParameterRangeForHorizontalRayPassigThroughStartingOutsideCircle() {
		assertEquals(new NumericRange(1.5, 0.5), hRay.getCircleIntersectionParameterRange(1, new Vector2d(3, 1)));
	}
	
	
	@Test
	public void testGettingCircleIntersectionParameterRangeForHorizontalRayIntersectingAtAPoint() {
		assertEquals(new NumericRange(0.5, 0.5), hRay.getCircleIntersectionParameterRange(1, new Vector2d(2, 0)));
	}
	
	
	@Test
	public void testGettingCircleIntersectionParameterRangeForHorizontalRayNotPassingThrough() {
		assertEquals(null, hRay.getCircleIntersectionParameterRange(1, new Vector2d(2, -1)));
	}
	
	
	@Test
	public void testGettingCircleIntersectionParameterRangeForSlantingRayPassigThroughStartingInsideCircle() {
		assertEquals(0, sRay.getCircleIntersectionParameterRange(Math.sqrt(5), new Vector2d(1, 1)).getMin(), 0.00);
		assertEquals(1, sRay.getCircleIntersectionParameterRange(Math.sqrt(5), new Vector2d(1, 1)).getMax(), 0.001);
	}
	
	
	@Test
	public void testGettingCircleIntersectionParameterRangeForSlantingRayPassigThroughStartingOutsideCircle() {
		assertEquals(new NumericRange(0, 2), sRay.getCircleIntersectionParameterRange(Math.sqrt(5), new Vector2d(2, 3)));
	}
	
	
	@Test
	public void testGettingCircleIntersectionParameterRangeForSlantingRayIntersectingAtAPoint() {
		Ray ray = new Ray(new Vector2d(1, 2),  new Vector2d(2, 1));
		assertEquals(new NumericRange(1, 1), ray.getCircleIntersectionParameterRange(1, new Vector2d(3-2/Math.sqrt(5), 3 + 1/Math.sqrt(5))));
	}
	
	
	@Test
	public void testGettingCircleIntersectionParameterRangeForSlantingRayNotPassingThrough() {
		assertEquals(null, sRay.getCircleIntersectionParameterRange(1, new Vector2d(2, -1)));
	}
	
	
	@Test
	public void testGettingConicIntersectionParameterRangeWithADegenerateConicThatIsAParabola() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getConicIntersectionParameterRange = 
				Ray.class.getDeclaredMethod("getConicIntersectionParameterRange", double.class, double.class, double.class, double.class, double.class, double.class);
		getConicIntersectionParameterRange.setAccessible(true);
		assertEquals(new NumericRange(0, 0), getConicIntersectionParameterRange.invoke(hRay, 0, 0, 1, -1, 0, 0));
	}
	
	
	@Test
	public void testGettingConicIntersectionParameterRangeWithADegenerateConicThatIsHorizontalLineCoincidentWithTheRay() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		hRay.setInitialPt(new Vector2d(-2, 0));
		Method getConicIntersectionParameterRange = 
				Ray.class.getDeclaredMethod("getConicIntersectionParameterRange", double.class, double.class, double.class, double.class, double.class, double.class);
		getConicIntersectionParameterRange.setAccessible(true);
		assertEquals(new NumericRange(0, Double.POSITIVE_INFINITY), getConicIntersectionParameterRange.invoke(hRay, 0, 0, 1, 0, 0, 0));
	}
	
	
	@Test
	public void testGettingConicIntersectionParameterRangeWithADegenerateConicThatIsHorizontalLineParallelToTheRay() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		hRay.setInitialPt(new Vector2d(-2, 0));
		Method getConicIntersectionParameterRange = 
				Ray.class.getDeclaredMethod("getConicIntersectionParameterRange", double.class, double.class, double.class, double.class, double.class, double.class);
		getConicIntersectionParameterRange.setAccessible(true);
		assertEquals(null, getConicIntersectionParameterRange.invoke(hRay, 0, 0, 1, 0, 0, -2));
	}
}

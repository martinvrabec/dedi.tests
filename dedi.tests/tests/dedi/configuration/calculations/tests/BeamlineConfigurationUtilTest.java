package dedi.configuration.calculations.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import dedi.configuration.calculations.BeamlineConfigurationUtil;

public class BeamlineConfigurationUtilTest {
	@Test
	public void testCalculatingQValueWithZeroDistance() {
		assertEquals("The q value at the centre of the incident beam should be zero.", 0, BeamlineConfigurationUtil.calculateQValue(0, 5, 9e-9), 0.0);
	}
	
	
	@Test
	public void testCalculatingQValueWithValidArguments() {
		assertEquals("The q value for distance of 5, camera length of 5 and wavelength of 4*pi should be sin(pi/8)", 
				     0.3827, BeamlineConfigurationUtil.calculateQValue(5, 5, 4*Math.PI), 0.001);
	}
	
	
	@Test(expected = ArithmeticException.class)
	public void calculateQValueShouldThrowExceptionWhenWavelengthIsZero() {
		BeamlineConfigurationUtil.calculateQValue(4, 5, 0);
	}
	
	
	@Test(expected = ArithmeticException.class)
	public void calculateQValueShouldThrowExceptionWhenCameraLengthIsZero() {
		BeamlineConfigurationUtil.calculateQValue(9, 0, 8);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateQValueShouldThrowExceptionWhenCameraLengthIsNegative() {
		BeamlineConfigurationUtil.calculateQValue(9, -1, 8);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateQValueShouldThrowExceptionWhenWavelengthIsNegative() {
		BeamlineConfigurationUtil.calculateQValue(9, 1, -8);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateQValueShouldThrowExceptionWhenDistanceIsNegative() {
		BeamlineConfigurationUtil.calculateQValue(-9, 1, 8);
	}
	
	

	@Test(expected = IllegalArgumentException.class)
	public void calculateDistanceFromQValueShouldThrowExceptionWhenCameraLengthIsNegative() {
		BeamlineConfigurationUtil.calculateDistanceFromQValue(0, -1, 5);
	
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateDistanceFromQValueShouldThrowExceptionWhenWavelengthIsNegative() {
		BeamlineConfigurationUtil.calculateDistanceFromQValue(0, 1, -8);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateDistanceFromQValueShouldThrowExceptionWhenQValueIsNegative() {
		BeamlineConfigurationUtil.calculateDistanceFromQValue(-1, 1, 8);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateDistanceFromQValueShouldThrowExceptionWhenQValueNotAttainable() {
		BeamlineConfigurationUtil.calculateDistanceFromQValue(4*Math.PI, 1, 1);
	}
	
	
	@Test
	public void testCalculateDistanceFromQValueWithQEqualToZero() {
		assertEquals("The distance should be zero for q = 0.", 0, BeamlineConfigurationUtil.calculateDistanceFromQValue(0, 5, 9e-9), 0.0);
	}
	
	
	@Test
	public void testCalculateDistanceFromQValueWithValidArguments() {
		assertEquals("The distance for q value of 4*pi, camera length of 1 and wavelength 0.5 of  should be tan(asin(0.5)*2).", 
				    1.732, BeamlineConfigurationUtil.calculateDistanceFromQValue(4*Math.PI, 1, 0.5), 0.001);
	}
	
	
	@Test
	public void getPtForQShouldReturnNullIfTheQValueIsNotAttainable() {
		assertNull(BeamlineConfigurationUtil.getPtForQ(4*Math.PI, Math.PI/2, 1, 1, 0, 0));
	}
}

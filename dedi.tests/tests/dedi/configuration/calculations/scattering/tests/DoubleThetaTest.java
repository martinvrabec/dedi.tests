package dedi.configuration.calculations.scattering.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import javax.measure.quantity.Angle;
import javax.measure.quantity.Length;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;

import org.jscience.physics.amount.Amount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dedi.configuration.calculations.scattering.DoubleTheta;
import dedi.configuration.calculations.scattering.InverseLength;
import dedi.configuration.calculations.scattering.Q;

public class DoubleThetaTest {
	private DoubleTheta doubleTheta1;
	private DoubleTheta doubleTheta2;
	private DoubleTheta doubleTheta3;
	private Q q;
	
	@Before
	public void setUp() throws Exception {
		doubleTheta1 = new DoubleTheta(Amount.valueOf(2, SI.METER));
		doubleTheta2 = new DoubleTheta(Amount.valueOf(Math.PI/3, SI.RADIAN), null);
		doubleTheta3 = new DoubleTheta(Amount.valueOf(Math.PI/3, SI.RADIAN), Amount.valueOf(2, SI.METER));
		q = new Q(Math.PI);
	}

	
	@After
	public void tearDown() throws Exception {
		doubleTheta1 = null; 
		doubleTheta2 = null;
		doubleTheta3 = null;
		q = null;
	}

	
	@Test
	public void theBaseUnitOfDoubleThetaShouldBeRadian() {
		assertEquals(SI.RADIAN, DoubleTheta.BASE_UNIT);
	}
	
	
	@Test
	public void wavelengthBaseUnitShouldBeMetres() throws NoSuchFieldException, IllegalAccessException {
		Field field = DoubleTheta.class.getDeclaredField("WAVELENGTH_BASE_UNIT");
		field.setAccessible(true);
		assertEquals(SI.METER, field.get(null));
	}
	
	
	@Test
	public void shouldBePossibleToSetWavelengthToNull() {
		doubleTheta3.setWavelength((Double) null);
		assertNull(doubleTheta3.getWavelength());
		doubleTheta1.setWavelength((Amount<Length>) null);
		assertNull(doubleTheta1.getWavelength());
	}
	
	
	@Test
	public void tryChangingTheWavelength() {
		doubleTheta3.setWavelength(1.4);
		assertEquals(1.4, doubleTheta3.getWavelength().doubleValue(SI.METER), 0.0);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenSettingValueFromQShouldThrowExceptionIfGivenQIsNull() {
		doubleTheta3.setValue((Q) null);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenSettingValueFromQShouldThrowExceptionIfWavelengthIsNull() {
		doubleTheta2.setValue(q);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenSettingValueFromQShouldThrowExceptionIfTheValueOfTheGivenQIsNull() {
		doubleTheta1.setValue(new Q());
		assertNull(doubleTheta1.getValue());
	}
	
	
	@Test
	public void whenSettingValueFromQShouldNotModifyTheGivenQ() {
		Amount<InverseLength> expected = q.getValue();
		doubleTheta1.setValue(q);
		assertSame(expected, q.getValue());  // Check that the value (== the state of) q has not been modified.
	}
	
	
	 @Test
	 public void whenSettingValueFromQShouldReturnNaNIfTheQValueCannotBeAttainedAtThatWavelength() {
		 doubleTheta3.setValue(new Q(100));
		 assertTrue(Double.isNaN(doubleTheta3.getValue().to(NonSI.DEGREE_ANGLE).getEstimatedValue()));
	 }
	
	 
	@Test
	public void testSettingValueFromQConvertsCorrectly() {
		doubleTheta3.setValue(q);
		assertEquals(Math.PI/3, doubleTheta3.getValue().doubleValue(SI.RADIAN), 0.001);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenConvertingToQShouldThrowExceptionIfTheValueIsNull() {
		doubleTheta1.toQ();
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenConvertingToQShouldThrowExceptionIfWavelengthIsNull() {
		doubleTheta2.toQ();
	}
	
	
	@Test
	public void toQShouldNotModifyTheQuantityItself() {
		Amount<Angle> originalValue = doubleTheta3.getValue();
		doubleTheta3.toQ();
		assertSame(originalValue, doubleTheta3.getValue());
	}
	
	
    @Test
    public void testConvertingToQConvertsCorrectly() {
    	assertEquals(Math.PI, doubleTheta3.toQ().getValue().doubleValue(InverseLength.UNIT), 0.001);
    } 
}

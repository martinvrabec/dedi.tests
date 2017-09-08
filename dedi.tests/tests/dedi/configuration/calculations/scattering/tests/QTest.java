package dedi.configuration.calculations.scattering.tests;

import static org.junit.Assert.*;

import javax.measure.unit.SI;

import org.jscience.physics.amount.Amount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dedi.configuration.calculations.scattering.InverseLength;
import dedi.configuration.calculations.scattering.Q;

public class QTest {
	private Q q1;
	private Q q2;
	
	
	@Before
	public void setUp() throws Exception {
		q1 = new Q();
		q2 = new Q(2);
	}

	
	@After
	public void tearDown() throws Exception {
		q1 = null;
		q2 = null; 
	}

	
	@Test
	public void theBaseUnitOfQShouldBeInverseMetre() {
		assertEquals(SI.METER.inverse(), Q.BASE_UNIT);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenSettingValueFromQShouldThrowExceptionIfGivenQIsNull() {
		q1.setValue((Q) null);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenSettingValueFromQShouldThrowExceptionIfTheValueOfTheGivenQIsNull() {
		q1.setValue(new Q());
	}
	
	
	@Test
	public void whenSettingValueFromQShouldNotModifyTheGivenQ() {
		Amount<InverseLength> expected = q2.getValue();
		q1.setValue(q2);
		assertSame(expected, q2.getValue());  // Check that the value (== the state of) q2 has not been modified.
	}
	
	
	@Test
	public void whenSettingValueFromQShouldCreateACopyOfTheValueInTheGivenQ() {
		Amount<InverseLength> value = q2.getValue();
		q1.setValue(q2);
		assertNotSame(q1.getValue(), value);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenConvertingToQShouldThrowExceptionIfTheValueIsNull() {
		q1.toQ();
	}
	
	
	@Test
	public void whenConvertingToQShouldReturnANewQ() {
		assertNotSame(q2, q2.toQ());
	}
	
	
	@Test
	public void toQShouldNotModifyTheQuantityItself() {
		Amount<InverseLength> originalValue = q2.getValue();
		q2.toQ();
		assertSame(originalValue, q2.getValue());
	}
	
	
	@Test
	public void whenConvertingToQShouldReturnTheNewQWithValueSetToACopyOfThisValue() {
		assertNotSame("The value of this Q and the new Q should not be the same object.", q2.getValue(), q2.toQ().getValue());
	}
}

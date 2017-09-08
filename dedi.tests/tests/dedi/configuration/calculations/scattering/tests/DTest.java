package dedi.configuration.calculations.scattering.tests;

import static org.junit.Assert.*;

import javax.measure.quantity.Length;
import javax.measure.unit.SI;

import org.jscience.physics.amount.Amount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dedi.configuration.calculations.scattering.D;
import dedi.configuration.calculations.scattering.InverseLength;
import dedi.configuration.calculations.scattering.Q;

public class DTest {
	private D d1;
	private D d2;
	private Q q;
	
	@Before
	public void setUp() throws Exception {
		d1 = new D();
		d2 = new D(2);
		q = new Q(2);
	}

	
	@After
	public void tearDown() throws Exception {
		d1 = null;
		d2 = null; 
		q = null;
	}

	
	@Test
	public void theBaseUnitOfDShouldBeMetre() {
		assertEquals(SI.METER, D.BASE_UNIT);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenSettingValueFromQShouldThrowExceptionIfGivenQIsNull() {
		d1.setValue((Q) null);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenSettingValueFromQShouldThrowExceptionIfTheValueOfTheGivenQIsNull() {
		d1.setValue(new Q());
	}
	
	
	@Test
	public void whenSettingValueFromQShouldNotModifyTheGivenQ() {
		Amount<InverseLength> expected = q.getValue();
		d1.setValue(q);
		assertSame(expected, q.getValue());  // Check that the value (== the state of) q has not been modified.
	}
	
	
	
	@Test
	public void testSettingValueFromQConvertsCorrectly() {
		d1.setValue(q);
		assertEquals(Math.PI, d1.getValue().doubleValue(SI.METER), 0.001);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenConvertingToQShouldThrowExceptionIfTheValueIsNull() {
		d1.toQ();
	}
	
	
	@Test
	public void toQShouldNotModifyTheQuantityItself() {
		Amount<Length> originalValue = d2.getValue();
		d2.toQ();
		assertSame(originalValue, d2.getValue());
	}
	
	
    @Test
    public void testConvertingToQConvertsCorrectly() {
    	assertEquals(Math.PI, d2.toQ().getValue().doubleValue(InverseLength.UNIT), 0.001);
    }
}

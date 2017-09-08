package dedi.configuration.calculations.scattering.tests;

import static org.junit.Assert.*;

import javax.measure.unit.SI;

import org.jscience.physics.amount.Amount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dedi.configuration.calculations.scattering.InverseLength;
import dedi.configuration.calculations.scattering.Q;
import dedi.configuration.calculations.scattering.S;

public class STest {
	private S s1;
	private S s2;
	private Q q;
	
	@Before
	public void setUp() throws Exception {
		s1 = new S();
		s2 = new S(2);
		q = new Q(2);
	}

	
	@After
	public void tearDown() throws Exception {
		s1 = null;
		s2 = null; 
		q = null;
	}

	
	@Test
	public void theBaseUnitOfSShouldBeInverseMetre() {
		assertEquals(SI.METER.inverse(), S.BASE_UNIT);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenSettingValueFromQShouldThrowExceptionIfGivenQIsNull() {
		s1.setValue((Q) null);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenSettingValueFromQShouldThrowExceptionIfTheValueOfTheGivenQIsNull() {
		s1.setValue(new Q());
	}
	
	
	@Test
	public void whenSettingValueFromQShouldNotModifyTheGivenQ() {
		Amount<InverseLength> expected = q.getValue();
		s1.setValue(q);
		assertSame(expected, q.getValue());  // Check that the value (== the state of) q has not been modified.
	}
	
	
	@Test
	public void testSettingValueFromQConvertsCorrectly() {
		s1.setValue(q);
		assertEquals(1/Math.PI, s1.getValue().doubleValue(InverseLength.UNIT), 0.001);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenConvertingToQShouldThrowExceptionIfTheValueIsNull() {
		s1.toQ();
	}
	
	
	@Test
	public void toQShouldNotModifyTheQuantityItself() {
		Amount<InverseLength> originalValue = s2.getValue();
		s2.toQ();
		assertSame(originalValue, s2.getValue());
	}
	
	
    @Test
    public void testConvertingToQConvertsCorrectly() {
    	assertEquals(4*Math.PI, s2.toQ().getValue().doubleValue(InverseLength.UNIT), 0.001);
    }
}

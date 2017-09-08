package dedi.configuration.calculations.scattering.tests;

import static org.junit.Assert.*;

import javax.measure.unit.SI;

import org.jscience.physics.amount.Amount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dedi.configuration.calculations.scattering.D;
import dedi.configuration.calculations.scattering.InverseLength;
import dedi.configuration.calculations.scattering.Q;
import dedi.configuration.calculations.scattering.S;

public class ScatteringQuantityTest {
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
	public void emptyConstructorShouldSetValueToNull() {
		assertNull((new Q()).getValue());
	}
	
	
	@Test
	public void constructorShouldAcceptNull() {
		assertNull((new Q(null)).getValue());
	}
	
	
	@Test
	public void constructorShouldSetValueToTheGivenValueNotACopy() {
		Amount<InverseLength> value = Amount.valueOf(1, InverseLength.UNIT);
		assertSame((new Q(value)).getValue(), value);
	}
	
	
	@Test(expected = ClassCastException.class)
	public void whenSettingValueShouldThrowExceptionIfTheValueIsNotCompatible() {
		q1.setValue(Amount.valueOf(1, SI.METER));
	}

	
	@Test
	public void shouldBePossibleToSetValueToNull() {
		q1.setValue((Amount<InverseLength>) null);
		assertNull(q1.getValue());
	}
	
	
	@Test
	public void setValueShouldUseTheGivenValueNotCreateACopy() {
		q1.setValue(q2.getValue());
		assertSame(q1.getValue(), q2.getValue());
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenConvertingShouldThrowExceptionIfGivenQuantityIsNull() {
		q2.to(null);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void whenConvertingShouldThrowExceptionIfThisValueIsNull() {
		q1.to(new D());
	}
	
	
	@Test
	public void whenConvertingTheResultShouldBeStoredDirectlyInTheGivenQuantity() {
		D d = new D();
		D result = q2.to(d);
		assertSame(d.getValue(), result.getValue());
	}
	
	
	@Test
	public void testConvertingBetweenQuantities() {
		assertEquals("Q = 2 m^-1 converted to D should be pi metres.", 
				     Math.PI, q2.to(new D()).getValue().doubleValue(D.BASE_UNIT), 0.001);
		assertEquals("Q = 2 m^-1 converted to S should be 1/pi m^-1.", 
				     1/Math.PI, q2.to(new S()).getValue().doubleValue(S.BASE_UNIT), 0.001);
	}
	
	
	@Test
	public void toStringShouldReturnEmptyStringWhenValueIsNull() {
		assertEquals("", q1.toString());
	}
}

package dedi.alltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import dedi.configuration.calculations.geometry.tests.RayTest;
import dedi.configuration.calculations.scattering.tests.DTest;
import dedi.configuration.calculations.scattering.tests.DoubleThetaTest;
import dedi.configuration.calculations.scattering.tests.QTest;
import dedi.configuration.calculations.scattering.tests.STest;
import dedi.configuration.calculations.scattering.tests.ScatteringQuantityTest;
import dedi.configuration.calculations.tests.NumericRangeTest;
import dedi.ui.tests.TextUtilTest;

@RunWith(Suite.class)
@SuiteClasses({
	RayTest.class, QTest.class, DTest.class, STest.class, DoubleThetaTest.class, ScatteringQuantityTest.class, 
	NumericRangeTest.class, TextUtilTest.class})
public class AllTests {

}

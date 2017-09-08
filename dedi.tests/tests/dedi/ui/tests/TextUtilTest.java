package dedi.ui.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import dedi.ui.TextUtil;

public class TextUtilTest {
	 @Test
	    public void testFormattingPositiveValuesInNormalNotation(){
	    	assertEquals("0.12344 formatted to 4 significant figures should be 0.1234.", "0.1234", TextUtil.format(0.12344, 4));
	    }
	
	 
    @Test
    public void testFormattingNegativeValuesInNormalNotation(){
    	assertEquals("-10.01 formatted to 3 significant figures should be -10.0.", "-10.0", TextUtil.format(-10.01));
    }
    
    
    @Test
    public void testFormattingZero(){
    	assertEquals("0 formatted to 4 significant figures should be just 0.0.", "0.0", TextUtil.format(0, 4));
    }
	

    @Test
    public void testFormattingPositiveValuesLeadingToTheUseOfScientificNotation() {
    	assertEquals("123 formatted to 2 significant figures should be 1.2E2.", "1.2E2", TextUtil.format(123, 2));
    	assertEquals("0.001 formatted to 3 significant figures should be 1.00E-3.", "1.00E-3", TextUtil.format(0.001));
    }
    
    @Test
    public void testFormattingNegativeValuesLeadingToTheUseOfScientificNotation() {
    	assertEquals("-123 formatted to 2 significant figures should be -1.2E2.", "-1.2E2", TextUtil.format(-123, 2));
    	assertEquals("-0.001 formatted to 3 significant figures should be -1.00E-3.", "-1.00E-3", TextUtil.format(-0.001));
    }
    
    
    @Test
    public void testRoundingWhenFormattingPositiveValues() {
    	assertEquals("125 formatted to 2 significant figures should be 1.3E2.", "1.3E2", TextUtil.format(125, 2));
    	assertEquals("124 formatted to 2 significant figures should be 1.2E2.", "1.2E2", TextUtil.format(124, 2));
    }
    
    
    @Test
    public void testRoundingWhenFormattingNegativeValues() {
    	assertEquals("-1.25 formatted to 2 significant figures should be -1.3.", "-1.3", TextUtil.format(-1.25, 2));
    	assertEquals("-1.24 formatted to 2 significant figures should be -1.2.", "-1.2", TextUtil.format(-1.24, 2));
    }
    
   
    @Test
    public void testRoundingIsPerformedBeforeFormatting() {
    	assertEquals("The value 0.09999, when formatted to 3 significant figures, should be first rounded to 0.1," +
                     "and then compared with the limits for deciding whether to use scientific notation or not.", "0.100", TextUtil.format(0.09999));
    }
    
    
    
    @Test
    public void formatShouldReturnNaNWhenTheArgumentIsNaN() {
    	assertEquals("NaN", TextUtil.format(Double.NaN));
    }
    
    
    @Test
    public void formatShouldReturnNaNWhenTheArgumentIsInfinity() {
    	assertEquals("NaN", TextUtil.format(Double.POSITIVE_INFINITY));
    	assertEquals("NaN", TextUtil.format(Double.NEGATIVE_INFINITY)); 
    }
    
    
    @Test
    public void equalAsDoublesShouldReturnFalseWhenOneOfTheArgumentsIsNull() {
    	assertFalse("equalAsDoubles should return false if the first argument is null.", TextUtil.equalAsDoubles(null, "1"));
    	assertFalse("equalAsDoubles should return false if the second argument is null.", TextUtil.equalAsDoubles("1", null));
    	assertFalse("equalAsDoubles should return false if both arguments are null.", TextUtil.equalAsDoubles(null, null));
    }
    
    
    @Test
    public void equalAsDoublesShouldReturnFalseWhenOneArgIsNotANumber() {
    	assertFalse("equalAsDoubles should return false if the first argument cannot be parsed as a double.",
    			    TextUtil.equalAsDoubles("abc", "35.0"));
    	assertFalse("equalAsDoubles should return false if the second argument cannot be parsed as a double.",
			    TextUtil.equalAsDoubles("22", "35.0 abc"));
    }
    
    
    @Test
    public void equalAsDoublesShouldReturnFalseWhenOneArgIsNotANumberEvenWhenTheStringsAreEqual() {
    	assertFalse(TextUtil.equalAsDoubles("abc", "abc"));
    }
    
    
    @Test
    public void testEqualAsDoublesWithEqualNumbersFormattedInTheSameWay() {
    	assertTrue(TextUtil.equalAsDoubles("22.2", "22.2"));
    }
    
    
    @Test
    public void testEqualAsDoublesWithEqualNumbersFormattedInADifferentWay() {
    	assertTrue("The strings '22.2' and '22.2000' should be equal as doubles.", TextUtil.equalAsDoubles("22.2", "22.2000"));
    	assertTrue("The strings '500' and '5E2' should be equal as doubles.", TextUtil.equalAsDoubles("500", "5E2"));
    	assertTrue("The strings '500' and '5e2' should be equal as doubles.", TextUtil.equalAsDoubles("500", "5e2"));
    }
    
    
    @Test
    public void equalAsDoublesShouldReturnFalseIfTheNumbersAreNotEqual() {
    	assertFalse("The strings '22.000001' and '22' are not equal as doubles.", TextUtil.equalAsDoubles("22.000001", "22"));
    }
    
    
    @Test
    public void parseDoubleShouldReturnNullIfTheArgumentIsNull() {
    	assertNull(TextUtil.parseDouble(null));
    }
    
    
    @Test
    public void parseDoubleShouldReturnNullIfTheStringCannotBeParsed() {
    	assertNull(TextUtil.parseDouble("22 abc"));
    }
    
    
    @Test
    public void testParseDoubleWithAStringRepresentingANumberInNormalNotation() {
    	assertEquals(22, TextUtil.parseDouble("22.0"), 0);
    }
    
    
    @Test
    public void testParseDoubleWithAStringRepresentingANumberInScientificNotation() {
    	assertEquals(220, TextUtil.parseDouble("2.2E2"), 0);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.*;

/**
 *
 * @author niltonkummer
 */
public class DateValidatorJUnitTest {
    
    private DateValidator dateValidator;
    
    public DateValidatorJUnitTest() {
    }
    
    @Before
	public void init() {
		dateValidator = new DateValidator();
	}
 
	@Test
	public void testDateIsNull() {
		assertFalse(dateValidator.isValidDate(null, "dd/MM/yyyy"));
	}
 
	@Test
	public void testDayIsInvalid() {
		assertFalse(dateValidator.isValidDate("32/02/2012", "dd/MM/yyyy"));
	}
 
	@Test
	public void testMonthIsInvalid() {
		assertFalse(dateValidator.isValidDate("31/20/2012", "dd/MM/yyyy"));
	}
 
	@Test
	public void testYearIsInvalid() {
		assertFalse(dateValidator.isValidDate("31/20/19991", "dd/MM/yyyy"));
	}
 
	@Test
	public void testDateFormatIsInvalid() {
		assertFalse(dateValidator.isValidDate("2012/02/20", "dd/MM/yyyy"));
	}
 
	@Test
	public void testDateFeb29_2012() {
		assertTrue(dateValidator.isValidDate("29/02/2012", "dd/MM/yyyy"));
	}
 
	@Test
	public void testDateFeb29_2011() {
		assertFalse(dateValidator.isValidDate("29/02/2011", "dd/MM/yyyy"));
	}
 
	@Test
	public void testDateFeb28() {
		assertTrue(dateValidator.isValidDate("28/02/2011", "dd/MM/yyyy"));
	}
 
	@Test
	public void testDateIsValid_1() {
		assertTrue(dateValidator.isValidDate("31/01/2012", "dd/MM/yyyy"));
	}
 
	@Test
	public void testDateIsValid_2() {
		assertTrue(dateValidator.isValidDate("30/04/2012", "dd/MM/yyyy"));
	}
 
	@Test
	public void testDateIsValid_3() {
		assertTrue(dateValidator.isValidDate("31/05/2012", "dd/MM/yyyy"));
	}

    @Test
	public void testHourIsInvalid() {
		assertFalse(dateValidator.isValidDate("25:59", "HH:mm"));
	}

    @Test
	public void testMinuteIsInvalid() {
		assertFalse(dateValidator.isValidDate("23:70", "HH:mm"));
	}
    
    @Test
	public void testHourMinuteIsValid() {
		assertTrue(dateValidator.isValidDate("23:59", "HH:mm"));
	}
}

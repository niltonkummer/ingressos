/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.*;

/**
 *
 * @author niltonkummer
 */
public class DateValidatorJUnitTest {
    
    private Date d;
    
    public DateValidatorJUnitTest() {
    }
    
    @Before
	public void init() {
		d = new Date();
	}
 
	@Test
	public void testDateIsNull() {
		
        assertFalse(DateUtil.isValidDate(null, "dd/MM/yyyy",d));
	}
 
	@Test
	public void testDayIsInvalid() {
		assertFalse(DateUtil.isValidDate("32/02/2012", "dd/MM/yyyy",d));
	}
 
	@Test
	public void testMonthIsInvalid() {
		assertFalse(DateUtil.isValidDate("31/20/2012", "dd/MM/yyyy",d));
	}
 
	@Test
	public void testYearIsInvalid() {
		assertFalse(DateUtil.isValidDate("31/20/19991", "dd/MM/yyyy",d));
	}
 
	@Test
	public void testDateFormatIsInvalid() {
		assertFalse(DateUtil.isValidDate("2012/02/20", "dd/MM/yyyy",d));
	}
 
	@Test
	public void testDateFeb29_2012() {
		assertTrue(DateUtil.isValidDate("29/02/2012", "dd/MM/yyyy",d));
	}
 
	@Test
	public void testDateFeb29_2011() {
		assertFalse(DateUtil.isValidDate("29/02/2011", "dd/MM/yyyy",d));
	}
 
	@Test
	public void testDateFeb28() {
		assertTrue(DateUtil.isValidDate("28/02/2011", "dd/MM/yyyy",d));
	}
 
	@Test
	public void testDateIsValid_1() {
		assertTrue(DateUtil.isValidDate("31/01/2012", "dd/MM/yyyy",d));
	}
 
	@Test
	public void testDateIsValid_2() {
		assertTrue(DateUtil.isValidDate("30/04/2012", "dd/MM/yyyy",d));
	}
 
	@Test
	public void testDateIsValid_3() {
		assertTrue(DateUtil.isValidDate("31/05/2012", "dd/MM/yyyy",d));
	}

    @Test
	public void testHourIsInvalid() {
		assertFalse(DateUtil.isValidDate("25:59", "HH:mm",d));
	}

    @Test
	public void testMinuteIsInvalid() {
		assertFalse(DateUtil.isValidDate("23:70", "HH:mm",d));
	}
    
    @Test
	public void testHourMinuteIsValid() {
		assertTrue(DateUtil.isValidDate("23:59", "HH:mm",d));
	}
}

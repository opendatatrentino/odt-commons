package eu.trentorise.opendata.commons.test;


import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import eu.trentorise.opendata.commons.PeriodOfTime;
import eu.trentorise.opendata.commons.PrecisePeriodOfTime;

public class TimeTest {
    
    @Test
    public void testPeriodOfTime(){
	
	assertEquals("a" + PeriodOfTime.SEP + "b", 
		PeriodOfTime.of("a", "b").getRawString());
	PeriodOfTime.of("a", "");
	PeriodOfTime.of("", "b");
	PeriodOfTime.of("2007-04-05T14:30", "2007-04-05T14:30");
	
    }

    @Test
    public void testPrecisePeriodOfTime(){	
	
	// PrecisePeriodOfTime.of("2010", "2015");
	PrecisePeriodOfTime.of("2007-04-05T14:30:00", "2007-04-05T15:30:00");
	
	try {
	    PrecisePeriodOfTime.of("2007-04-05T15:30:00", "2007-04-05T14:30:00");	    
	    Assert.fail("Shouldn't arrive here!");
	} catch (IllegalStateException ex){
	    
	}
	
	assertEquals("?/?", PrecisePeriodOfTime.of().toString());	
	assertEquals("/", PrecisePeriodOfTime.of("", "").toString());	
	
    }

}

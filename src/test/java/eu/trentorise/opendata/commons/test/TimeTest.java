package eu.trentorise.opendata.commons.test;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;


import org.junit.Test;

import eu.trentorise.opendata.commons.PeriodOfTime;

/**
 * @since 1.1
 */
public class TimeTest {

    @Test
    public void testPeriodOfTime() {
        
        assertEquals("2010" + PeriodOfTime.SEP + "2015", PeriodOfTime.of("2010", "2015")
                                                                     .getRawString());
        PeriodOfTime.of("2007-04-05T14:30:00", "2007-04-05T15:30:00");

        PeriodOfTime.of("2010-10", "2015-11");
        
        PeriodOfTime.of("the year 2015");

        try {
            PeriodOfTime.of("2007-04-05T15:30:00", "2007-04-05T14:30:00");
            Assert.fail("Shouldn't be able to create interval with end happining before start!");
        } catch (IllegalStateException ex) {

        }

        assertEquals("?/?", PeriodOfTime.of()
                                        .toFormattedString());
        assertEquals("/", PeriodOfTime.of("", "")
                                      .toFormattedString());

    }

}

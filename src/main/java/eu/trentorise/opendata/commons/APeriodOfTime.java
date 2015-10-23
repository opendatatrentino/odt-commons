package eu.trentorise.opendata.commons;

import org.immutables.value.Value;

/**
 * A time interval, which may be open, closed, unknown or even have unparseable
 * dates expressed in human language (sic).
 *
 * Implements dct:PeriodOfTime, adapting it to possibly dirty data.
 * 
 * @see APrecisePeriodOfTime
 * @since 1.1
 */
@Value.Immutable
@SimpleStyle
public abstract class APeriodOfTime {

    public static String SEP = "@traceprov@";

    /**
     * The start date and time. Date format is unspecified, although preferably
     * it should be in ISO 8061 format. This field MAY be the result of some
     * automated processing. An empty value does not necessarily implies we have
     * an open interval.
     */
    @Value.Default
    public String getStartDate() {
	return "";
    }

    /**
     * The end date and time. Date format is unspecified, although preferably it
     * should be in ISO 8061 format. This field MAY be the result of some
     * automated processing. An empty value does not necessarily implies we have
     * an open interval.
     * 
     */
    @Value.Default
    public String getEndDate() {
	return "";
    }

    /**
     *
     * The original period of time. If it was already a String, it is reported
     * here as is, otherwise this value will be the join of the serialized form
     * of the two parts, joined by {@link #SEP} separator
     */
    @Value.Default
    public String getRawString() {
	return "";
    }
    
    public static PeriodOfTime of(String rawStartDate, String rawEndDate) {
	return PeriodOfTime.of( rawStartDate, 
				rawEndDate, 
				joinRawDates(rawStartDate, rawEndDate));
    }

    public static String joinRawDates(String startDate, String endDate) {
	return startDate + SEP + endDate;
    }

}

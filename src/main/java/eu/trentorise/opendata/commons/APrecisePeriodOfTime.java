package eu.trentorise.opendata.commons;

import java.util.Date;

import javax.annotation.Nullable;
import org.immutables.value.Value;

import com.google.common.base.Preconditions;

/**
 * A time interval, which may be open, closed, completely or partially unknown.
 *
 * Implements dct:PeriodOfTime, adapting it to clean data.
 * 
 * @see APeriodOfTime
 * @since 1.1
 */
@Value.Immutable
@SimpleStyle
public abstract class APrecisePeriodOfTime {

    /**
     * The start date and time. Date format is ISO8061. An empty value indicates
     * the interval is open, so there's no start. A value holding a question
     * mark '?' means it is not known if exists a start date at all.
     */
    @Value.Default
    public String getStartDate() {
	return "?";
    }

    /**
     * The end date and time. Date format is ISO8061. An empty value indicates
     * the interval is open, so there's no end. A value holding a question mark
     * '?' means it is not known if exists an end date at all.
     */
    @Value.Default
    public String getEndDate() {
	return "?";
    }

    /**
     * Checks provided string is either a date in ISO8061 format, or empty or a
     * question mark. If so returns a trimmed version of it, otherwise throws
     * exception.
     * 
     * @throws IllegalArgumentException
     */
    private static String checkIso8061(@Nullable String s) {
	String newDate = Preconditions.checkNotNull(s).trim();
	if (!(newDate.isEmpty() || newDate.equals('?'))) {

	    try {
		OdtUtils.parseIso8061(newDate);
	    } catch (OdtParseException ex) {
		throw new IllegalArgumentException(ex);
	    }

	}
	return newDate;
    }

    /**
     * Checks provided string is empty, a question mark, or a date in ISO8061
     * format. If so returns a trimmed version of it, otherwise throws
     * exception.
     * 
     * @param start
     *            true if the date represents a start date.
     * @return the parsed date. In special cases, returns null if string is
     *         empty, {}@link Long#MIN_VALUE} or {@link Long#MAX_VALUE} if date
     *         is '?'.
     * @throws IllegalArgumentException
     */
    @Nullable
    private static Date parseDate(@Nullable String s, boolean start) {
	String newDateString = Preconditions.checkNotNull(s).trim();

	if (newDateString.equals("?")) {
	    if (start) {
		return new Date(Long.MIN_VALUE);

	    } else {
		return new Date(Long.MAX_VALUE);
	    }
	}

	if (newDateString.isEmpty()) {
	    return null;
	}

	return OdtUtils.parseIso8061(newDateString);

    }

    public static PrecisePeriodOfTime of(PeriodOfTime periodOfTime) {
	return PrecisePeriodOfTime.of(periodOfTime.getStartDate(), periodOfTime.getEndDate());
    }

    /**
     * Returns true if date is not null and its milliseconds are not extramal
     * ones (Long.MIN_VALUE or Long.MAX_VALUE)
     * 
     */
    public static boolean isRealDate(Date date, boolean start) {
	long val = start ? Long.MIN_VALUE : Long.MAX_VALUE;
	return date != null && date.getTime() != val;
    }

    @Value.Check
    protected void check() {
	Date start = parseDate(getStartDate(), true);
	Date end = parseDate(getEndDate(), false);
	if (isRealDate(start, true) && isRealDate(end, false)){
	    Preconditions.checkState(end.getTime() - start.getTime() >= 0,
		    "Start date %s is greater than end date %s! ", start, end);
	}
    }

    /**
     * 
     * An interval of time in string format. It may represents, either: <br/>
     * <ul>
     * <li>A closed interval where string formatted following
     * <a href="https://en.wikipedia.org/wiki/ISO_8601#Time_intervals">ISO 8601
     * Date and Time interval</a> string format i.e.
     * "2007-03-01T13:00:00Z/2008-05-11T15:30:00Z". Note ISO 8601 only allows
     * <i>closed intervals</i></li>
     * <li>An open interval where one of the two dates is represented as the
     * empty string. Interval shall either begin with '/' or end with '/'. The
     * date string will be formatted following
     * <a href="http://www.w3.org/TR/NOTE-datetime">ISO 8601 Date and Time
     * format</a> NOTE: open intervals are <i> not </i> part of ISO 8601
     * standard.</li>
     * <li>An interval of which the start or end is known, but it is not known
     * whether interval is open or closed. One of the two dates will be
     * represented as the question mark {@code ?}, so interval shall either
     * begin with '?/' or end with '/?'. The other date will be formatted
     * following <a href="http://www.w3.org/TR/NOTE-datetime">ISO 8601 Date and
     * Time format</a> NOTE: open intervals are <i> not </i> part of ISO 8601
     * standard.</li>
     * <li>An unknown interval, represented by the string ?/? Notice the empty
     * string is not allowed.</li>
     * </ul>
     */
    @Override
    public String toString() {
	return getStartDate() + "/" + getEndDate();
    }
}

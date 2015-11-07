package eu.trentorise.opendata.commons;

import java.util.Date;

import javax.annotation.Nullable;
import org.immutables.value.Value;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;

/**
 * A time interval, which may be bounded, unbounded, partially or completely
 * unknown. Allows storing automatically parsed dates, and also the original
 * strings from which they originate.
 *
 * <p>
 * This class implements {@code dct:PeriodOfTime} in the {@code DCAT AP 1.1}
 * flavor, adapting it to possibly dirty data. This abstract class is used to
 * generate the immutable {@link PeriodOfTime}.
 * </p>
 * 
 * <p>
 * <h4>Available fields</h4>
 * <p>
 * Automatically parsed dates are stored in {@link #getStartDate() startDate}
 * and {@link #getEndDate() endDate} fields
 * </p>
 * <p>
 * Original unclean/unparseable data can be stored in {@link #getRawString()
 * rawString} field.
 * </p>
 * </p>
 * </p>
 * <p>
 * <h4>Date format</h4> Dates are stored in an extended ISO 8061 format, where:
 * <ul>
 * <li>If a start or end date is known, it is stored as ISO 8061 format with a
 * level of precision faithful to the original format (so if you have day
 * precision avoid specifying seconds).</li>
 * <li>If the interval is known to be unbounded with certainty, one or both the
 * dates are stored as the empty string {@code ""}.</li>
 * <li>If the interval might be unbounded, one of the dates is stored as a
 * question mark {@code '?'}.</li>
 * <li>If the interval is completely unknown, both dates will be represented as
 * a question mark {@code '?'}.</li>
 * </ul>
 * </p>
 * 
 * @see APeriodOfTime
 * @since 1.1
 */
@Value.Immutable
@SimpleStyle
@Beta
abstract class APeriodOfTime {

    /**
     * Separator between two dates in {@link #getRawString() rawString} field
     */
    public static final String SEP = "@joined@";

    /**
     * The start date and time. This field MAY be the result of some automated
     * processing, but its value is assumed to be faithful to the original.
     * Original date precision should be preserved (so i.e. if it is a day,
     * don't specify seconds).
     * <ul>
     * <li>If the date is known, it is stored in ISO8061 format. The start of
     * the period should be understood as the start of the date, hour, minute
     * etc. given (e.g. starting at midnight at the beginning of the day if the
     * value is a day),</li>
     * <li>An empty value indicates the interval is unbounded, so there's no
     * start.</li>
     * <li>A value holding a question mark '?' means it is not known if exists a
     * start date at all.</li>
     * </ul>
     * 
     * @see #getRawString() getRawString() field for the original value.
     * 
     */
    @Value.Default
    public String getStartDate() {
        return "?";
    }

    /**
     * The end date and time. This field MAY be the result of some automated
     * processing, but its value is assumed to be faithful to the original.
     * Original date precision should be preserved (so i.e. if it is a day,
     * don't specify seconds).
     * <ul>
     * <li>If the date is known, it is stored in ISO8061 format. the end of the
     * period should be understood as the end of the date, hour, minute etc.
     * given (e.g. ending at midnight at the end of the day if the value is a
     * day),</li>
     * <li>An empty value indicates the interval is unbounded, so there's no
     * end.</li>
     * <li>A value holding a question mark '?' means it is not known if exists
     * an end date at all.</li>
     * </ul>
     * 
     * @see #getRawString() getRawString() field for the original value.
     */
    @Value.Default
    public String getEndDate() {
        return "?";
    }

    /**
     * 
     * The original period of time. If it was already a String, it is reported
     * here as is, otherwise this value will be made by joining the serialized
     * form of the two parts, separated by {@link #SEP} separator.
     */
    @Value.Default
    public String getRawString() {
        return "";
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
        String newDateString = Preconditions.checkNotNull(s)
                                            .trim();

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

        return TodUtils.parseIso8061(newDateString);

    }

    /**
     * Returns true if date is not null and its milliseconds are not extremal
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
        if (isRealDate(start, true) && isRealDate(end, false)) {
            Preconditions.checkState(end.getTime() - start.getTime() >= 0,
                    "Start date %s is greater than end date %s! ", start, end);
        }
    }

    /**
     * 
     * An interval of time in string format. It may represents, either: <br/>
     * <ul>
     * <li>A bounded interval where dates are formatted following
     * <a href="https://en.wikipedia.org/wiki/ISO_8601#Time_intervals">ISO 8601
     * Date and Time interval</a> string format i.e.
     * {@code "2007-03-01T13:00:00Z/2008-05-11T15:30:00Z"}. NOTE: ISO 8601 only
     * allows <i>bounded intervals</i></li>
     * <li>An unbounded interval where one of the two dates is represented as
     * the empty string. Interval shall either begin with '/' or end with '/'.
     * The existing date string will be formatted following
     * <a href="http://www.w3.org/TR/NOTE-datetime">ISO 8601 Date and Time
     * format</a>. NOTE: unbounded intervals are <i> not </i> part of ISO 8601
     * standard.</li>
     * <li>An interval with a known start or end, but it is not known whether
     * interval is unbounded or bounded. To represent this, one of the two dates
     * will be represented as the question mark {@code '?'}, so interval shall
     * either begin with "?/" or end with "/?". The other date will be formatted
     * following <a href="http://www.w3.org/TR/NOTE-datetime">ISO 8601 Date and
     * Time format</a>. NOTE: partially unknown intervals are <i> not </i> part
     * of ISO 8601 standard.</li>
     * <li>An unknown interval, represented by the string {@code "?/?"} Notice
     * the empty string is not allowed. NOTE: unknown intervals are
     * <i> not </i> part of ISO 8601 standard. </li>
     * </ul>
     * 
     * @see #getRawString()
     */
    public String toFormattedString() {
        return getStartDate() + "/" + getEndDate();
    }

    public static String joinRawDates(String startDate, String endDate) {
        return startDate + SEP + endDate;
    }

    /**
     * Factory method that just fills rawString field. {@link #getStartDate
     * startDate} and {@link #getEndDate endDate} are set to {@code '?'}
     * 
     * @param rawString
     *            a temporal interval with unknown formatting
     */
    public static PeriodOfTime of(String rawString) {
        return PeriodOfTime.of("?", "?", rawString);
    }

    /**
     * Factory method that fills all fields. {@link #getRawString() rawString}
     * field is filled with the two provided dates joined with string
     * {@link #SEP}. For parameter meaning, see {@link #toFormattedString()}.
     * 
     * @param startDate
     *            an ISO8061 date, a question mark '?', or the empty string.
     * @param endDate
     *            an ISO8061 date, a question mark '?', or the empty string.
     */
    public static PeriodOfTime of(String startDate, String endDate) {
        return PeriodOfTime.of(startDate, endDate, joinRawDates(startDate, endDate));
    }
}

/* 
 * Copyright 2015 Trento Rise  (trentorise.eu) 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.trentorise.opendata.commons;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.Nullable;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * Utility funtions shared by Open Data in Trentino projects.
 *
 * @author David Leoni <david.leoni@unitn.it>
 */
public final class TodUtils {

    /**
     * Tolerance for probabilities
     */
    public static final double TOLERANCE = 0.001;

    /**
     * Used for unparseable dates and other stuff.
     */
    public static final String UNPARSEABLE = "unparseable:";

    private static final Logger LOG = Logger.getLogger(TodUtils.class.getName());

    /**
     * Tod Commons build properties path.
     */
    public static final String BUILD_PROPERTIES_PATH = "tod.commons.build.properties";

    private TodUtils() {
    }

    /**
     * Converts a language code to Java Locale. On null input returns
     * {@link Locale#ROOT}
     * 
     * Notice Java 7 introduced {@link Locale#forLanguageTag(String)}, but that
     * method throws null pointer exception on null string, which unfortunately
     * can happen quite often, so we use this method instead.
     * 
     * @see Locale#forLanguageTag(String)
     * @see #localeToLanguageTag(Locale) localeToLanguageTag(Locale) for the inverse operation
     */
    public static Locale languageTagToLocale(@Nullable String languageTag) {

        if (languageTag == null) {
            LOG.warning("Found null locale, returning Locale.ROOT");
            return Locale.ROOT;
        }
        return Locale.forLanguageTag(languageTag);
    }

    /**
     * Converts a Java locale to a String. On null input returns the empty
     * string (which corresponds to Java {@link Locale#ROOT})
     *
     * @see #languageTagToLocale(java.lang.String) #languageTagToLocale(java.lang.String) for the inverse operation
     */
    public static String localeToLanguageTag(@Nullable Locale locale) {
        if (locale == null) {
            LOG.warning("Found null locale, returning empty string (which corresponds to Locale.ROOT)");
            return "";
        }
        return locale.getLanguage();
    }

    /**
     * Returns a new url with a slash added at the end of provided url. If
     * provided url already ends with a slash it just returns it.
     *
     * @param url
     */
    public static String addSlash(String url) {        
        checkNotNull(url, "invalid url!");
        String trimmedUrl = url.trim();
        if (trimmedUrl.endsWith("/")) {
            return trimmedUrl;
        } else {
            return trimmedUrl + "/";
        }
    }

    /**
     * Returns the provided url with all trailing slash at the end removed.
     */
    public static String removeTrailingSlash(String url) {
        checkNotNull(url, "invalid url!");
        String tempUrl = url.trim();
        while (tempUrl.endsWith("/")) {
            tempUrl = tempUrl.substring(0, tempUrl.length() - 1);
        }
        return tempUrl;
    }

    /**
     * Checks if provided URL is to be considered 'dirty'. Method may use some
     * heuristics to detect oddities, like i.e. the string "null" inside the
     * url.
     *
     * @deprecated Moved to
     *             {@link eu.trentorise.opendata.commons.validation.Preconditions#checkNotDirtyUrl(java.lang.String, java.lang.Object) } @
     *             param url the URL to check
     * @param prependedErrorMessage
     *            the exception message to use if the check fails; will be
     *            converted to a string using String.valueOf(Object) and
     *            prepended to more specific error messages.
     *
     * @throws IllegalArgumentException
     *             if provided URL fails validation.
     *
     * @return the non-dirty URL that was validated
     *
     */
    public static String checkNotDirtyUrl(@Nullable String url, @Nullable Object prependedErrorMessage) {
        checkNotEmpty(url, prependedErrorMessage);

        if (url.equalsIgnoreCase("null")) {
            throw new IllegalArgumentException(String.valueOf(prependedErrorMessage)
                    + " -- Reason: Found URL with string \"" + url + "\" as content!");
        }

        // todo delete this is a too radical checker...
        if (url.toLowerCase()
               .endsWith("/null")) {
            throw new IllegalArgumentException(
                    String.valueOf(prependedErrorMessage) + " -- Reason: Found URL ending with /\"null\": " + url);
        }

        return url;
    }

    /**
     *
     * Checks if provided string is non null and non empty.
     *
     * @deprecated Moved to
     *             {@link eu.trentorise.opendata.commons.validation.Preconditions#checkNotEmpty(java.lang.String, java.lang.Object) } @
     *             param prependedErrorMessage the exception message to use if
     *             the check fails; will be converted to a string using
     *             String.valueOf(Object) and prepended to more specific error
     *             messages.
     *
     * @throws IllegalArgumentException
     *             if provided string fails validation
     *
     * @return the non-empty string that was validated
     */
    public static String checkNotEmpty(String string, @Nullable Object prependedErrorMessage) {
        checkArgument(string != null, "%s -- Reason: Found null string.", prependedErrorMessage);
        if (string.length() == 0) {
            throw new IllegalArgumentException(
                    String.valueOf(prependedErrorMessage) + " -- Reason: Found empty string.");
        }
        return string;
    }

    /**
     *
     * Checks if provided collection is non null and non empty .
     *
     * @deprecated Moved to
     *             {@link eu.trentorise.opendata.commons.validation.Preconditions#checkNotEmpty(java.lang.Iterable, java.lang.Object) }
     *
     * @param prependedErrorMessage
     *            the exception message to use if the check fails; will be
     *            converted to a string using String.valueOf(Object) and
     *            prepended to more specific error messages.
     *
     * @throws IllegalArgumentException
     *             if provided collection fails validation
     *
     * @return a non-null non-empty collection
     */
    public static <T> Collection<T> checkNotEmpty(@Nullable Collection<T> coll,
            @Nullable Object prependedErrorMessage) {
        checkArgument(coll != null, "%s -- Reason: Found null collection.", prependedErrorMessage);
        if (coll.isEmpty()) {
            throw new IllegalArgumentException(
                    String.valueOf(prependedErrorMessage) + " -- Reason: Found empty collection.");
        }
        return coll;
    }

    /**
     * Returns true if provided string is non null and non empty .
     */
    public static boolean isNotEmpty(@Nullable String string) {
        return string != null && string.length() != 0;
    }

    /**
     * Parses an URL having a numeric ID after the provided prefix, i.e. for
     * prefix 'http://entitypedia.org/concepts/' and url
     * http://entitypedia.org/concepts/14324 returns 14324
     *
     * @deprecated this shouldn't be here.....
     *
     * @throws IllegalArgumentException
     *             on invalid URL
     */
    public static long parseNumericalId(String prefix, String url) {

        checkNotNull(prefix, "prefix can't be null!");
        checkNotEmpty(url, "Invalid url!");

        String s;
        if (prefix.length() > 0) {
            int pos = url.indexOf(prefix);
            if (pos != 0) {
                throw new IllegalArgumentException("Invalid URL for prefix " + prefix + ": " + url);
            }
            s = url.substring(prefix.length());
        } else {
            s = url;
        }
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid URL for prefix " + prefix + ": " + url, ex);
        }

    }

    /**
     *
     * Substitutes each {@code %s} in {@code template} with an argument. These
     * are matched by position: the first {@code %s} gets {@code args[0]}, etc.
     * If there are more arguments than placeholders, the unmatched arguments
     * will be appended to the end of the formatted message in square braces.
     * <br/>
     * <br/>
     * (Copied from Guava's
     * {@link com.google.common.base.Preconditions#format(java.lang.String, java.lang.Object...) }
     * )
     *
     * @param template
     *            a non-null string containing 0 or more {@code %s}
     *            placeholders.
     * @param args
     *            the arguments to be substituted into the message template.
     *            Arguments are converted to strings using
     *            {@link String#valueOf(Object)}. Arguments can be null.
     *
     * @since 1.1
     */
    public static String format(String template, @Nullable Object... args) {
        if (template == null) {
            LOG.warning("Found null template while formatting, converting it to \"null\"");
        }
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }

    /**
     * Extracts parameters from given url. Works also with multiple params with
     * same name.
     *
     * @return map of param name : [args]
     * @throws IllegalArgumentException
     * @since 1.1
     */
    public static Multimap<String, String> parseUrlParams(String url) {
        URL u;
        try {
            u = new URL(url);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException("Ill formed url!", ex);
        }
        Multimap<String, String> queryPairs = LinkedListMultimap.create();
        final String[] pairs = u.getQuery()
                                .split("&");

        try {
            for (String pair : pairs) {
                final int idx = pair.indexOf("=");

                final String key;

                key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;

                final String value = idx > 0 && pair.length() > idx + 1
                        ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : "";
                queryPairs.put(key, value);
            }
            return queryPairs;
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Encoding not supported!", ex);
        }
    }

    /**
     * Returns a copy of provided map with {@code newObject} set under the given
     * key. If key already exists replaces its value in the returned object. Not
     * efficient, but sometimes we need it.
     *
     * @param newObject
     *            Must be an immutable object.
     * @since 1.1
     */
    public static <K, V> ImmutableMap<K, V> putKey(Map<K, V> map, K key, V newObject) {
        ImmutableMap.Builder<K, V> mapb = ImmutableMap.builder();

        for (K k : map.keySet()) {
            if (!k.equals(key)) {
                mapb.put(k, map.get(k));
            }
        }
        mapb.put(key, newObject);
        return mapb.build();
    }

    private static final FastDateFormat ISO_YEAR_FORMAT = FastDateFormat.getInstance("yyyy");

    private static final FastDateFormat ISO_YEAR_MONTH_FORMAT = FastDateFormat.getInstance("yyyy-MM");

    /**
     * @deprecated experimental, try to avoid using it for now
     * @since 1.1
     * @throws TodParseException
     */
    // todo this parser is horrible
    public static Date parseIso8061(String s) {

        try {
            return DateFormatUtils.ISO_DATE_TIME_ZONE_FORMAT.parse(s);
        } catch (ParseException ex) {
        }

        try {
            return DateFormatUtils.ISO_DATETIME_FORMAT.parse(s);
        } catch (ParseException ex) {
        }

        try {
            return DateFormatUtils.ISO_DATE_TIME_ZONE_FORMAT.parse(s);
        } catch (ParseException ex) {
        }

        try {
            return DateFormatUtils.ISO_DATE_FORMAT.parse(s);
        } catch (ParseException ex) {
        }

        try {
            return ISO_YEAR_MONTH_FORMAT.parse(s);
        } catch (ParseException ex) {
        }

        try {
            return ISO_YEAR_FORMAT.parse(s);
        } catch (ParseException ex) {
        }

        // todo week dates, ordinal dates

        throw new TodParseException("Couldn't parse date as ISO8061. Unparseable date was:" + s);
    }
    
}

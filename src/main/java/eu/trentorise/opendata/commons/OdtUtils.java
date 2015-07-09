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
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/**
 * Utility funtions shared by Open Data in Trentino projects.
 *
 * @author David Leoni <david.leoni@unitn.it>
 */
public final class OdtUtils {

    /**
     * Tolerance for probabilities
     */
    public static final double TOLERANCE = 0.001;

    private static final Logger LOG = Logger.getLogger(OdtUtils.class.getName());

    /**
     * Odt Commons build properties path.
     */
    public static final String BUILD_PROPERTIES_PATH = "odt.commons.build.properties";

    private OdtUtils() {
    }

    /**
     * Java 7 has Locale.forLanguageTag(format), this is the substitute for Java
     * 6 <br/>
     *
     * @deprecated Now library targets Java >= 7, so use Locale.forLanguageTag(format) instead.
     * 
     * Copied from apache.commons.lang, with the change it returns Locale.ROOT
     * for null input <br/>
     * -------------------------------------------------------------------------
     * <p>
     * Converts a String to a Locale.</p>
     *
     * <p>
     * This method takes the string format of a locale and creates the locale
     * object from it.</p>
     *
     * <pre>
     *   LocaleUtils.toLocale("en")         = new Locale("en", "")
     *   LocaleUtils.toLocale("en_GB")      = new Locale("en", "GB")
     *   LocaleUtils.toLocale("en_GB_xxx")  = new Locale("en", "GB", "xxx")   (#)
     * </pre>
     *
     * <p>
     * (#) The behaviour of the JDK variant constructor changed between JDK1.3
     * and JDK1.4. In JDK1.3, the constructor upper cases the variant, in
     * JDK1.4, it doesn't. Thus, the result from getVariant() may vary depending
     * on your JDK.</p>
     *
     * <p>
     * This method validates the input strictly. The language code must be
     * lowercase. The country code must be uppercase. The separator must be an
     * underscore. The length must be correct.
     * </p>
     *
     * @param str the locale String to convert, null returns null
     * @return a Locale, Locale.ROOT if null input
     * @throws IllegalArgumentException if the string is an invalid format
     */
    public static Locale languageTagToLocale(String str) {

        if (str == null) {
            LOG.warning("Found null locale, returning Locale.ROOT");
            return Locale.ROOT;
        }
        int len = str.length();
        if (len != 2 && len != 5 && len < 7) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        char ch0 = str.charAt(0);
        char ch1 = str.charAt(1);
        if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (len == 2) {
            return new Locale(str, "");
        } else {
            if (str.charAt(2) != '_') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            char ch3 = str.charAt(3);
            if (ch3 == '_') {
                return new Locale(str.substring(0, 2), "", str.substring(4));
            }
            char ch4 = str.charAt(4);
            if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            if (len == 5) {
                return new Locale(str.substring(0, 2), str.substring(3, 5));
            } else {
                if (str.charAt(5) != '_') {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                return new Locale(str.substring(0, 2), str.substring(3, 5), str.substring(6));
            }
        }
    }

    /**
     * Converts a Java locale to a String.
     *
     * @see #languageTagToLocale(java.lang.String) fo the inverse operation
     * @deprecated Now library targets Java >= 7, so use Locale.toLanguageTag() instead.
     */
    public static String localeToLanguageTag(Locale locale) {
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
        if (url.endsWith("/")) {
            return url;
        } else {
            return url + "/";
        }
    }

    /**
     * Returns the provided url with all trailing slash at the end removed.
     */
    public static String removeTrailingSlash(String url) {
        checkNotNull(url, "invalid url!");
        String tempUrl = url;
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
     * @deprecated Moved to {@link eu.trentorise.opendata.commons.validation.Preconditions#checkNotDirtyUrl(java.lang.String, java.lang.Object) }
     * @param url the URL to check
     * @param prependedErrorMessage the exception message to use if the check
     * fails; will be converted to a string using String.valueOf(Object) and
     * prepended to more specific error messages.
     *
     * @throws IllegalArgumentException if provided URL fails validation.
     *
     * @return the non-dirty URL that was validated
     *
     */
    public static String checkNotDirtyUrl(@Nullable String url, @Nullable Object prependedErrorMessage) {
        checkNotEmpty(url, prependedErrorMessage);

        if (url.equalsIgnoreCase("null")) {
            throw new IllegalArgumentException(String.valueOf(prependedErrorMessage) + " -- Reason: Found URL with string \"" + url + "\" as content!");
        }

        // todo delete this is a too radical checker...
        if (url.toLowerCase().endsWith("/null")) {
            throw new IllegalArgumentException(String.valueOf(prependedErrorMessage) + " -- Reason: Found URL ending with /\"null\": " + url);
        }

        return url;
    }

    /**
     *
     * Checks if provided string is non null and non empty.
     *
     * @deprecated Moved to {@link eu.trentorise.opendata.commons.validation.Preconditions#checkNotEmpty(java.lang.String, java.lang.Object)  }
     * @param prependedErrorMessage the exception message to use if the check
     * fails; will be converted to a string using String.valueOf(Object) and
     * prepended to more specific error messages.
     *
     * @throws IllegalArgumentException if provided string fails validation
     *
     * @return the non-empty string that was validated
     */
    public static String checkNotEmpty(String string, @Nullable Object prependedErrorMessage) {
        checkArgument(string != null, "%s -- Reason: Found null string.", prependedErrorMessage);
        if (string.length() == 0) {
            throw new IllegalArgumentException(String.valueOf(prependedErrorMessage) + " -- Reason: Found empty string.");
        }
        return string;
    }

    
    
    
     /**
     *
     * Checks if provided collection is non null and non empty . 
     *
     * @deprecated Moved to {@link eu.trentorise.opendata.commons.validation.Preconditions#checkNotEmpty(java.lang.Iterable, java.lang.Object)   }

     * @param prependedErrorMessage the exception message to use if the check
     * fails; will be converted to a string using String.valueOf(Object) and
     * prepended to more specific error messages.
     *
     * @throws IllegalArgumentException if provided collection fails validation
     * 
     * @return a non-null non-empty collection
     */
    public static <T> Collection<T> checkNotEmpty(@Nullable Collection<T> coll, @Nullable Object prependedErrorMessage) {
        checkArgument(coll != null, "%s -- Reason: Found null collection.", prependedErrorMessage);
        if (coll.isEmpty()) {
            throw new IllegalArgumentException(String.valueOf(prependedErrorMessage) + " -- Reason: Found empty collection.");
        }
        return coll;
    }

    /**     
     * @deprecated use Guava {@link com.google.common.base.Strings#isNullOrEmpty(java.lang.String) } instead
     * Returns true if provided string is non null and non empty .
     */
    public static boolean isNotEmpty(@Nullable String string) {
        return string != null
                && string.length() != 0;
    }

    /**
     * Parses an URL having a numeric ID after the provided prefix, i.e. for
     * prefix 'http://entitypedia.org/concepts/' and url
     * http://entitypedia.org/concepts/14324 returns 14324
     *
     * @throws IllegalArgumentException on invalid URL
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
        }
        catch (NumberFormatException ex) {
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
     * (Copied from Guava's {@link com.google.common.base.Preconditions#format(java.lang.String, java.lang.Object...)
     * })
     *
     * @param template a non-null string containing 0 or more {@code %s}
     * placeholders.
     * @param args the arguments to be substituted into the message template.
     * Arguments are converted to strings using {@link String#valueOf(Object)}.
     * Arguments can be null.
     *
     * @since 1.1
     */
    public static String format(String template, @Nullable Object... args) {
        if (template == null){
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

}

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

import static com.google.common.base.Preconditions.checkNotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/**
 * Utility funtions shared by Open Data in Trentino projects.
 *
 * @author David Leoni
 */
public class OdtUtils {

    private static final Logger LOGGER = Logger.getLogger(OdtUtils.class.getName());

    public static final String BUILD_PROPERTIES_PATH = "odt.commons.build.properties";

    /**
     * Java 7 has Locale.forLanguageTag(format), this is the substitute for Java
     * 6 <br/>
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
            LOGGER.warning("Found null locale, returning Locale.ROOT");
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
     *
     * @see #languageTagToLocale(java.lang.String) fo the inverse operation
     *
     */
    public static String localeToLanguageTag(Locale locale) {
        if (locale == null) {
            LOGGER.warning("Found null locale, returning empty string (which corresponds to Locale.ROOT)");
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
    public String addSlash(String url) {
        checkNonNull(url, "url");
        if (url.endsWith("/")) {
            return url;
        } else {
            return url + "/";
        }
    }

    /**
     * Removes all trailing slash at the end of the provided url.
     */
    public static String removeTrailingSlash(String url) {
        checkNonNull(url, "url");
        String tempUrl = url;
        while (tempUrl.endsWith("/")) {
            tempUrl = tempUrl.substring(0, tempUrl.length() - 1);
        }
        return tempUrl;
    }

    
    /**
     * Checks if provided URL is to be considered 'dirty'. Method may use some
     * heristics to detect i.e. the string "null" inside the url.
     *
     * @param URL the URL to check
     * @throws IllegalArgumentException if provided URL fails validation.
     * @return the non-dirty URL that was validated
     * 
     */
    public static String checkNotDirtyUrl(String URL) {
        if (URL == null) {
            throw new IllegalArgumentException("Found null URL!");
        }
        if (URL.length() == 0) {
            throw new IllegalArgumentException("Found empty URL!");
        }

        if (URL.toLowerCase().equals("null")) {
            throw new IllegalArgumentException("Found URL with string \"" + URL + "\" as content!");
        }
        
        // todo delete this is a too radical checker...
        if (URL.toLowerCase().endsWith("/null")) {
            throw new IllegalArgumentException("Found URL ending with /\"null\": " + URL);
        }
        
        return URL;
    }
    
    /**
     *
     * Checks if provided string is non null and non empty . If not, throws
     * NullPointerException or IllegalArgumentException
     *
     * @param errorMessage the exception message to use if the check fails; will
     * be converted to a string using String.valueOf(Object)
     * 
     * @return the non-empty string that was validated
     */
    public static String checkNotEmpty(String string, @Nullable String errorMessage) {
        checkNotNull(string, errorMessage);
        if (string.length() == 0) {
            throw new EmptyException(String.valueOf(errorMessage));
        }
        return string;
    }

    /**
     * @deprecated use {@link #checkNotEmpty} instead Checks if provided string
     * is non null and non empty . If not, throws IllegalArgumentException
     */
    public static void checkNonEmpty(String string, String stringName) {
        checkNotNull(string);
        if (string.length() == 0) {
            throw new IllegalArgumentException("Parameter " + stringName + " has zero length!");
        }
    }

    /**
     * @deprecated Use
     *  com.​google.​common.​base.​Preconditions#checkNotNull instead
     * Checks if provided object is non null. If not, throws
     * IllegalArgumentException
     */
    public static void checkNonNull(Object obj, String objName) {
        if (obj == null) {
            throw new IllegalArgumentException("Parameter " + objName + " can't be null!");
        }
    }

    /**
     * Checks if provided string is non null and non empty .
     */
    public static boolean isNotEmpty(String string) {
        return string == null
                || string.length() == 0;
    }

    /**
     * @deprecated use {@link #isNotEmpty(java.lang.String) } instead. Checks if
     * provided string is non null and non empty .
     */
    public static boolean isNonEmpty(String string) {
        return string == null
                || string.length() == 0;
    }

    /**
     * Parses file at {@link #BUILD_PROPERTIES_PATH} of the jar holding the
     * provided class.
     *
     * @throws eu.​trentorise.​opendata.​traceprov.​TraceProvException if file
     * is not found.
     */
    public static BuildInfo readBuildInfo(Class clazz) {
        InputStream stream = clazz.getResourceAsStream("/" + BUILD_PROPERTIES_PATH);
        Properties props = new Properties();
        if (stream == null) {
            throw new NotFoundException("Couldn't find " + BUILD_PROPERTIES_PATH + " file in resources of package containing class " + clazz.getSimpleName() + "  !!");
        } else {
            try {
                props.load(stream);
            }
            catch (IOException ex) {
                throw new OdtException("Couldn't load " + BUILD_PROPERTIES_PATH + " file in resources of package containing class " + clazz.getSimpleName() + "  !!", ex);
            }
        }
        return BuildInfo.builder()
                .withBuildJdk(props.getProperty("build-jdk", ""))
                .withBuiltBy(props.getProperty("built-by", ""))
                .withCreatedBy(props.getProperty("created-by", ""))
                .withGitSha(props.getProperty("git-sha", ""))
                .withScmUrl(props.getProperty("scm-url", ""))
                .withTimestamp(props.getProperty("timestamp", ""))
                .withVersion(props.getProperty("version", ""))
                .build();
    }

}

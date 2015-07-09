/*
 * Copyright 2015 Trento Rise.
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
package eu.trentorise.opendata.commons.validation;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.Iterables;
import eu.trentorise.opendata.commons.OdtUtils;
import javax.annotation.Nullable;

/**
 * Static convenience methods for OdtCommons that help a method or constructor
 * check whether it was invoked correctly (whether its <i>preconditions</i> have
 * been met). Takes inspiration from {@link com.google.common.base.Preconditions}.
 *
 * @since 1.1
 */
public class Preconditions {

    /**
     * Checks if provided URL is to be considered 'dirty'. Method may use some
     * heuristics to detect oddities, like i.e. the string "null" inside the
     * url.
     *
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
     * Checks if provided string is non null and non empty.
     *
     * @param errorMessageTemplate a template for the exception message should
     * the check fail. The message is formed by replacing each {@code %s}
     * placeholder in the template with an argument. These are matched by
     * position - the first {@code %s} gets {@code
     *     errorMessageArgs[0]}, etc. Unmatched arguments will be appended to the
     * formatted message in square braces. Unmatched placeholders will be left
     * as-is.
     * @param errorMessageArgs the arguments to be substituted into the message
     * template. Arguments are converted to strings using
     * {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException if {@code expression} is false
     * @throws NullPointerException if the check fails and either
     * {@code errorMessageTemplate} or {@code errorMessageArgs} is null (don't
     * let this happen)
     *
     *
     * @throws IllegalArgumentException if provided string fails validation
     *
     * @return the non-empty string that was validated
     */
    public static String checkNotEmpty(
            String string,
            @Nullable String errorMessageTemplate,
            @Nullable Object... errorMessageArgs) {
        String formattedMessage = OdtUtils.format(errorMessageTemplate, errorMessageArgs);
        checkArgument(string != null, "%s -- Reason: Found null string.", formattedMessage);
        if (string.length() == 0) {
            throw new IllegalArgumentException(formattedMessage + " -- Reason: Found empty string.");
        }
        return string;
    }

    /**
     * Checks the provided score is within valid bounds
     *
     * @param score must be between -{@link #TOLERANCE} ≤ score ≤ 1 + {@link
     * #TOLERANCE}
     *
     * @param prependedErrorMessage the exception message to use if the check
     * fails; will be converted to a string using String.valueOf(Object) and
     * prepended to more specific error messages.
     *
     * @throws IllegalArgumentException on invalid score
     * @return the validated score in the range [0.0, 1.0]
     * @since 1.1
     */
    public static double checkScore(double score, @Nullable Object prependedErrorMessage) {

        if (score < 0.0) {
            if (score > -OdtUtils.TOLERANCE) {
                return 0.0;
            } else {
                throw new IllegalArgumentException("Score must be greater or equal than zero, found instead: " + score);
            }
        }

        if (score >= 1.0) {
            if (score < 1.0 + OdtUtils.TOLERANCE) {
                return 1.0;
            } else {
                throw new IllegalArgumentException("Score must be less than or equal than 1.0, found instead: " + score);
            }
        }

        return score;

    }

    /**
     *
     * Checks if provided iterable is non null and non empty .
     *
     * @param prependedErrorMessage the exception message to use if the check
     * fails; will be converted to a string using String.valueOf(Object) and
     * prepended to more specific error messages.
     *
     * @throws IllegalArgumentException if provided collection fails validation
     *
     * @return a non-null non-empty iterable
     */
    public static <T> Iterable<T> checkNotEmpty(@Nullable Iterable<T> iterable, @Nullable Object prependedErrorMessage) {
        checkArgument(iterable != null, "%s -- Reason: Found null iterable.", prependedErrorMessage);
        if (Iterables.isEmpty(iterable)) {
            throw new IllegalArgumentException(String.valueOf(prependedErrorMessage) + " -- Reason: Found empty collection.");
        }
        return iterable;
    }

    /**
     *
     * Checks if provided array is non null and non empty .
     *
     * @param prependedErrorMessage the exception message to use if the check
     * fails; will be converted to a string using String.valueOf(Object) and
     * prepended to more specific error messages.
     *
     * @throws IllegalArgumentException if provided array fails validation
     *
     * @return a non-null non-empty array
     */
    public static <T> T[] checkNotEmpty(@Nullable T[] array, @Nullable Object prependedErrorMessage) {
        checkArgument(array != null, "%s -- Reason: Found null array.", prependedErrorMessage);
        if (array.length == 0) {
            throw new IllegalArgumentException(String.valueOf(prependedErrorMessage) + " -- Reason: Found empty array.");
        }
        return array;
    }

    /**
     *
     * Checks if provided iterable is non null and non empty .
     *
     * @param errorMessageTemplate a template for the exception message should
     * the check fail. The message is formed by replacing each {@code %s}
     * placeholder in the template with an argument. These are matched by
     * position - the first {@code %s} gets {@code
     *     errorMessageArgs[0]}, etc. Unmatched arguments will be appended to the
     * formatted message in square braces. Unmatched placeholders will be left
     * as-is.
     * @param errorMessageArgs the arguments to be substituted into the message
     * template. Arguments are converted to strings using
     * {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException if {@code iterable} is empty or null
     * @throws NullPointerException if the check fails and either
     * {@code errorMessageTemplate} or {@code errorMessageArgs} is null (don't
     * let this happen)
     *
     * @return a non-null non-empty iterable
     */
    public static <T> Iterable<T> checkNotEmpty(@Nullable Iterable<T> iterable,
            @Nullable String errorMessageTemplate,
            @Nullable Object... errorMessageArgs) {
        String formattedMessage = OdtUtils.format(errorMessageTemplate, errorMessageArgs);

        checkArgument(iterable != null, "%s -- Reason: Found null iterable.", formattedMessage);
        if (Iterables.isEmpty(iterable)) {
            throw new IllegalArgumentException(formattedMessage + " -- Reason: Found empty iterable.");
        }
        return iterable;
    }

    /**
     *
     * Checks if provided array is non null and non empty .
     *
     * @param errorMessageTemplate a template for the exception message should
     * the check fail. The message is formed by replacing each {@code %s}
     * placeholder in the template with an argument. These are matched by
     * position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     * Unmatched arguments will be appended to the formatted message in square
     * braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message
     * template. Arguments are converted to strings using
     * {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException if {@code array} is empty or null
     * @throws NullPointerException if the check fails and either
     * {@code errorMessageTemplate} or {@code errorMessageArgs} is null (don't
     * let this happen)
     *
     * @return a non-null non-empty array
     */
    public static <T> T[] checkNotEmpty(@Nullable T[] array,
            @Nullable String errorMessageTemplate,
            @Nullable Object... errorMessageArgs) {
        String formattedMessage = OdtUtils.format(errorMessageTemplate, errorMessageArgs);

        checkArgument(array != null, "%s -- Reason: Found null iterable.", formattedMessage);
        if (array.length == 0) {
            throw new IllegalArgumentException(formattedMessage + " -- Reason: Found empty array.");
        }
        return array;
    }

}

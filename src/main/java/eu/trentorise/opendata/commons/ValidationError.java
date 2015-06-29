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
package eu.trentorise.opendata.commons;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Represents an error occurred during validation of an object. Position of the
 * error is specified via
 * <a href="https://github.com/jayway/JsonPath" target="_blank">a JsonPath
 * expression</a> <br/><br/>
 * Definition of error codes and their format is entire responsability of the
 * library users.
 *
 * @author David Leoni
 */
@Immutable
public class ValidationError implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static ValidationError INSTANCE = new ValidationError();

    private String jsonPath;
    private String reason;
    private List reasonArgs;
    @Nullable
    private Object errorCode;

    private ValidationError(){
        this.jsonPath = "$";
        this.reason = "";
        this.errorCode = null;
        this.reasonArgs = ImmutableList.of();
    }
    
    ValidationError(
            String jsonPath,
            Object errorCode,
            String reason,
            Object... reasonArgs) {
        checkNotNull(jsonPath);
        checkArgument(jsonPath.length() > 0 && jsonPath.charAt(0) == '$', "jsonPath expression must start with a $");
        this.jsonPath = jsonPath;
        this.errorCode = errorCode;
        this.reason = OdtUtils.simpleFormat(reason, reasonArgs);        
        this.reasonArgs = ImmutableList.copyOf(reasonArgs);
    }

    /**
     * Returns the arguments of the error message.
     */
    public List getReasonArgs() {
        return reasonArgs;
    }

    /**
     * A reference to one or more elements with errors expressed as a JsonPath.
     * See
     * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
     * syntax</a> and
     * <a href="https://groups.google.com/forum/#!topic/jsonpath/5G3bf2wx4zM" target="_blank">
     * this link for regexes </a>
     *
     * @author David Leoni
     */
    public String getJsonPath() {
        return jsonPath;
    }

    /**
     * The optional error code associated to the error.
     */
    @Nullable
    public Object getErrorCode() {
        return errorCode;
    }

    /**
     * Human readable error reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns an empty error
     */
    public static ValidationError of() {
        return INSTANCE;
    }

    /**
     * Creates a ValidationError.
     *
     * @param reasonTemplate a template for the error message. The message is
     * formed by replacing each %s placeholder in the template with an argument.
     * These are matched by position - the first %s gets errorMessageArgs[0],
     * etc. Unmatched arguments will be appended to the formatted message in
     * square braces. Unmatched placeholders will be left as-is.
     * @jsonPath a path as specified by
     * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
     * syntax</a>
     * @param reasonArgs the arguments to be substituted into the message
     * template. Arguments are converted to strings using
     * String.valueOf(Object).
     */
    public static ValidationError of(String jsonPath, String reasonTemplate, Object... reasonArgs) {
        return new ValidationError(jsonPath, null, reasonTemplate, reasonArgs);
    }

    /**
     * Creates a ValidationError specifying an error code.
     *
     * @param reasonTemplate a template for the error message. The message is
     * formed by replacing each %s placeholder in the template with an argument.
     * These are matched by position - the first %s gets errorMessageArgs[0],
     * etc. Unmatched arguments will be appended to the formatted message in
     * square braces. Unmatched placeholders will be left as-is.
     * @jsonPath a path as specified by
     * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
     * syntax</a>
     * @param errorCode the error code.
     * @param reasonArgs the arguments to be substituted into the message
     * template. Arguments are converted to strings using
     * String.valueOf(Object).
     */
    public static ValidationError of(String jsonPath, Object errorCode, String reasonTemplate, Object... reasonArgs) {
        checkNotNull(errorCode);
        return new ValidationError(jsonPath, errorCode, reasonTemplate, reasonArgs);
    }

}

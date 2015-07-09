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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

/**
 * Standard implementation of {@link AValidationError}
 *
 * @author David Leoni
 */
@Immutable
@ParametersAreNonnullByDefault
public final class ValidationError extends AValidationError {

    private static final Logger LOG = Logger.getLogger(ValidationError.class.getName());

    private final static ValidationError INSTANCE = new ValidationError();

    private ValidationError() {
        super();
    }

    private ValidationError(Ref ref, ErrorLevel errorLevel, int errorCode, String reason, Object... reasonArgs) {
        super(ref, errorLevel, errorCode, reason, reasonArgs);
    }

    /**
     * Returns an empty error
     */
    public static ValidationError of() {
        return INSTANCE;
    }

    
    /**
     * Creates a ValidationError specifying an error code.
     *
     * @param ref a reference to the error.
     * @param errorLevel the level of the error
     * @param errorCode the error code. If unknown pass 0.
     * @param reason the error reason template, with %s as placeholders for
     * {@code reasonArgs}. Internally, {@code reason} will be stored as is,
     * without substitution.
     * @param reasonArgs the arguments to be substituted to the {@code reason}
     * placeholder
     */
    public static ValidationError of(Ref ref, ErrorLevel errorLevel, int errorCode, String reason, Object... reasonArgs) {
        return new ValidationError(ref, errorLevel, errorCode, reason, reasonArgs);
    }

    /**
     * Creates a ValidationError specifying an error code.
     *
     * @param jsonPath a path as specified by
     * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
     * syntax</a>.
     * @param errorLevel the level of the error
     * @param errorCode the error code. If unknown pass zero.
     * @param reason the error reason template, with %s as placeholders for
     * {@code reasonArgs}. Internally, {@code reason} will be stored as is,
     * without substitution.
     * @param reasonArgs the arguments to be substituted to the {@code reason}
     * placeholder
     */
    public static ValidationError of(String jsonPath, ErrorLevel errorLevel, int errorCode, String reason, Object... reasonArgs) {
        Ref ref;
        if (jsonPath == null || jsonPath.trim().length() == 0) {
            LOG.log(Level.WARNING, "Found null or empty jsonPath while creating {0}, setting it to '*'", ValidationError.class.getSimpleName());
            ref = Ref.of();
        } else {
            ref = Ref.of(jsonPath);
        }
        return new ValidationError(ref, errorLevel, errorCode, reason, reasonArgs);
    }

   @Override
    public String toString() {
        return "ValidationError{" + "ref=" + getRef() + ", errorCode=" + getErrorCode() + ", errorLevel=" + getErrorLevel() + ", reason=" + getReason() + ", reasonArgs=" + getReasonArgs() + '}';
    }    
    
}

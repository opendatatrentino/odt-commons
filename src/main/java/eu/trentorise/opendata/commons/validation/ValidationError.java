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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
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

    private ValidationError(Ref ref, ErrorLevel errorLevel, int errorCode, String reason, Map<String, ?> reasonArgs) {
        super(ref, errorLevel, errorCode, reason, reasonArgs);
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
     * @param ref a reference to the error.
     * @param errorLevel the level of the error
     * @param errorCode the error code. If unknown pass 0.
     * @param reason the error reason template, see
     * {@link AValidationError#getReason()} for placeholders format. Internally,
     * {@code reason} will be stored as is, without substitution.
     * @param reasonArgs the arguments to be substituted to the {@code reason}
     * placeholder
     */
    public static ValidationError of(Ref ref, ErrorLevel errorLevel, int errorCode, String reason, Map<String, ?> reasonArgs) {
        return new ValidationError(ref, errorLevel, errorCode, reason, reasonArgs);
    }

    
    /**
     * Creates a ValidationError.
     *
     * @param ref a reference to the error.
     * @param errorLevel the level of the error
     * @param errorCode the error code. If unknown pass 0.
     * @param reason the error reason string, with no placeholders.
     */
    public static ValidationError of(Ref ref, ErrorLevel errorLevel, int errorCode, String reason) {
        return new ValidationError(ref, errorLevel, errorCode, reason, ImmutableMap.<String, Object>of());
    }    
    
    /**
     * Creates a ValidationError.
     *
     * @param ref a reference to the error.
     * @param errorLevel the level of the error
     * @param errorCode the error code. If unknown pass 0.
     * @param reason the error reason template, see
     * {@link AValidationError#getReason()} for placeholders format. Internally,
     * {@code reason} will be stored as is, without substitution.
     */
    public static ValidationError of(Ref ref, ErrorLevel errorLevel, int errorCode, String reason, String reasonArgName1, Object reasonArg1) {
        return new ValidationError(ref, errorLevel, errorCode, reason, ImmutableMap.of(reasonArgName1, reasonArg1));
    }
    

    /**
     * Creates a ValidationError.
     *
     * @param ref a reference to the error.
     * @param errorLevel the level of the error
     * @param errorCode the error code. If unknown pass 0.
     * @param reason the error reason template, see
     * {@link AValidationError#getReason()} for placeholders format. Internally,
     * {@code reason} will be stored as is, without substitution.
     */
    public static ValidationError of(Ref ref, ErrorLevel errorLevel, int errorCode, String reason, 
            String reasonArgName1, Object reasonArg1,
            String argName2, Object reasonArg2) {
        return new ValidationError(ref, errorLevel, errorCode, reason, ImmutableMap.of(reasonArgName1, reasonArg1, reasonArgName1, reasonArg2));
    }    

    @Override
    public String toString() {
        return "ValidationError{" + "ref=" + getRef() + ", errorCode=" + getErrorCode() + ", errorLevel=" + getErrorLevel() + ", reason=" + getReason() + ", reasonArgs=" + getReasonArgs() + '}';
    }

}

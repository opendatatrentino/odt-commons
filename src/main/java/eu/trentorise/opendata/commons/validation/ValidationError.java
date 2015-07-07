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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

/**
 * Represents an error occurred during validation of an object. Position of the
 * error is specified via
 * <a href="https://github.com/jayway/JsonPath" target="_blank">a JsonPath
 * expression</a> <br/><br/>
 * Definition of error codes and their format is entire responsability of the
 * library users.
 *
 * TODO Do we include an error level? (i.e. SEVERE, WARNING, MINOR, ...)
 * 
 * @author David Leoni
 */
@Immutable
@ParametersAreNonnullByDefault
public class ValidationError implements IValidationError {

    private static final Logger LOG = Logger.getLogger(ValidationError.class.getName());

    private static final long serialVersionUID = 1L;

    private final static ValidationError INSTANCE = new ValidationError();

    private Ref ref;
    @Nullable
    private Object errorCode;
    private List reason;

    ValidationError() {
        this.ref = Ref.of();
        this.errorCode = null;
        this.reason = ImmutableList.of();
    }

    ValidationError(
            Ref ref,
            Object errorCode,
            Object... reason) {
        if (ref == null) {
            LOG.log(Level.WARNING, "Found null ref while creating validation error, setting it to Ref.of('$')");
            this.ref = Ref.of();
        } else {
            this.ref = ref;
        }
        this.errorCode = errorCode;
        this.reason = Collections.unmodifiableList(Arrays.asList(reason)); // ImmutableList would complain about nulls...
    }

    @Override
    public List getReason() {
        return reason;
    }

    @Override
    public Ref getRef() {
        return ref;
    }

    @Nullable
    @Override
    public Object getErrorCode() {
        return errorCode;
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
     * @param jsonPath a path as specified by
     * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
     * syntax</a>.
     * @param errorCode the error code. If unknown pass null.
     * @param reason the error reason, as a sequence of objects to be displayed
     * to the user in the style of Javascript's <br/>
     * console.log("Expected ", x, "Found ", y )
     */
    public static ValidationError of(Ref ref, @Nullable Object errorCode, Object... reason) {
        return new ValidationError(ref, errorCode, reason);
    }

    /**
     * Creates a ValidationError specifying an error code.
     *
     * @param jsonPath a path as specified by
     * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
     * syntax</a>.
     * @param errorCode the error code. If unknown pass null.
     * @param reason the error reason, as a sequence of objects to be displayed
     * to the user in the style of Javascript's <br/>
     * console.log("Expected ", x, "Found ", y )
     */
    public static ValidationError of(String jsonPath, @Nullable Object errorCode, Object... reason) {
        Ref ref;
        if (jsonPath == null || jsonPath.trim().length() == 0){
            LOG.log(Level.WARNING, "Found null or empty jsonPath while creating {0}, setting it to '*'", ValidationError.class.getSimpleName());
            ref = Ref.of();
        } else {
            ref = Ref.of(jsonPath);
        }
        return new ValidationError(ref, errorCode, reason);
    }

    @Override
    public String toString() {
        return "ValidationError{" + "ref=" + ref + ", errorCode=" + errorCode + ", reason=" + reason + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.ref);
        hash = 31 * hash + Objects.hashCode(this.errorCode);
        hash = 31 * hash + Objects.hashCode(this.reason);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValidationError other = (ValidationError) obj;
        if (!Objects.equals(this.ref, other.ref)) {
            return false;
        }
        if (!Objects.equals(this.errorCode, other.errorCode)) {
            return false;
        }
        if (!Objects.equals(this.reason, other.reason)) {
            return false;
        }
        return true;
    }

    
    
}

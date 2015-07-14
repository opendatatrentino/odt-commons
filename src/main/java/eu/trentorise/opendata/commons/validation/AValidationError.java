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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

/**
 * Represents an error occurred during validation of an object. Position of the
 * error is specified via
 * <a href="https://github.com/jayway/JsonPath" target="_blank">a JsonPath
 * expression</a> <br/><br/>
 * Definition of error codes is entire responsability of the library users. You
 * can extend this class to create your own validation errors.
 *
 * @author David Leoni
 */
@Immutable
@ParametersAreNonnullByDefault
public abstract class AValidationError {

    private static final Logger LOG = Logger.getLogger(AValidationError.class.getName());

    private static final long serialVersionUID = 1L;

    private Ref ref;
    private int errorCode;
    private ErrorLevel errorLevel;
    private String reason;
    private ImmutableMap<String, ?> reasonArgs;

    protected AValidationError() {
        this.ref = Ref.of();
        this.errorCode = 0;
        this.errorLevel = ErrorLevel.SEVERE;
        this.reason = "";
        this.reasonArgs = ImmutableMap.of();
    }

    protected AValidationError(
            Ref ref,
            ErrorLevel errorLevel,
            int errorCode,
            String reason,
            Map<String, ?> reasonArgs) {

        if (ref == null) {
            LOG.log(Level.WARNING, "Found null ref while creating validation error, setting it to Ref.of()");
            this.ref = Ref.of();
        } else {
            this.ref = ref;
        }
        if (ref == null) {
            LOG.log(Level.WARNING, "Found null error level while creating validation error, setting it to ErrorLevel.SEVERE");
            this.errorLevel = ErrorLevel.SEVERE;
        } else {
            this.errorLevel = errorLevel;
        }
        this.errorCode = errorCode;
        this.errorLevel = errorLevel;
        this.reason = reason;
        this.reasonArgs = ImmutableMap.copyOf(reasonArgs);
    }

    /**
     * The error reason template, formatted according to
     * <a href="http://icu-project.org/apiref/icu4j/com/ibm/icu/text/MessageFormat.html" target="_blank">
     * ICU4J MessageFormat</a>. Only use the styles {@code SelectFormat} and
     * {@code PluralFormat}. Placeholders can be either numeric (i.e. {2}) or
     * names {i.e. someName}). If you want substituted placeholders in Java add ICU4J
     * dependency and use com.ibm.icu.text.MessageFormat.format(). In Javascript
     * you can use
     * <a href="https://github.com/SlexAxton/messageformat.js/" target="_blank">MessageFormat.js</a>
     * instead.
     *
     */
    public final String getReason() {
        return reason;
    }

    /**
     * The arguments to be substituted to the {@code reason} placeholder
     */
    public final ImmutableMap<String, ?> getReasonArgs() {
        return reasonArgs;
    }

    /**
     * A reference to logical and physical position of the error.
     */
    public final Ref getRef() {
        return ref;
    }

    /**
     * The optional error code associated to the error.
     */
    public final int getErrorCode() {
        return errorCode;
    }

    /**
     * The level of the validation error.
     */
    public final ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.ref);
        hash = 37 * hash + this.errorCode;
        hash = 37 * hash + Objects.hashCode(this.errorLevel);
        hash = 37 * hash + Objects.hashCode(this.reason);
        hash = 37 * hash + Objects.hashCode(this.reasonArgs);
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
        final AValidationError other = (AValidationError) obj;
        if (!Objects.equals(this.ref, other.ref)) {
            return false;
        }
        if (this.errorCode != other.errorCode) {
            return false;
        }
        if (this.errorLevel != other.errorLevel) {
            return false;
        }
        if (!Objects.equals(this.reason, other.reason)) {
            return false;
        }
        if (!Objects.equals(this.reasonArgs, other.reasonArgs)) {
            return false;
        }
        return true;
    }

}

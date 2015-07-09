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
import eu.trentorise.opendata.commons.OdtUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    private List reasonArgs;

    protected AValidationError() {
        this.ref = Ref.of();
        this.errorCode = 0;
        this.errorLevel = ErrorLevel.SEVERE;
        this.reason = "";
        this.reasonArgs = ImmutableList.of();
    }

    protected AValidationError(
            Ref ref,
            ErrorLevel errorLevel,
            int errorCode,
            String reason,
            Object... reasonArgs) {

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
        this.reasonArgs = Collections.unmodifiableList(Arrays.asList(reasonArgs)); // ImmutableList would complain about nulls...
    }

    /**
     * The error reason template, with %s as placeholders for
     * {@code reasonArgs}. Returned string does not substitute placeholders. If
     * you want substituted placeholders use {@link #formattedReason() }
     * instead.
     *
     */
    public final String getReason() {
        return reason;
    }

    /**
     * The arguments to be substituted to the {@code reason} placeholder
     */
    public final List getReasonArgs() {
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

    /**
     * Returns the formatted reason.
     */
    public String formatReason() {
        return OdtUtils.format(reason, reasonArgs.toArray());
    }

}

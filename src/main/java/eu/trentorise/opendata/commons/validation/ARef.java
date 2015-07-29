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

import com.google.common.base.Preconditions;
import eu.trentorise.opendata.commons.SimpleStyle;
import java.io.Serializable;
import org.immutables.value.Value;

/**
 * A reference to an element in a file. The reference is both logical (i.e. the
 * path to a json node a.b.c) and physical (the row and column number (13,48) in
 * the json file ).
 *
 * @author David Leoni
 */
@Value.Immutable
@SimpleStyle
abstract class ARef implements Serializable {

    /**
     * An identifier (possibly an IRI) for the original document. In case the
     * data was obtained through a query the id could be the endpoint address
     * with the query parameters.
     */
    @Value.Default
    public String getDocumentId() {
        return "";
    }

    /**
     * The row index in the physical file (starting from 0), in case the file of
     * reference is in text format. In case paramter is not set -1 is returned.
     */
    @Value.Default
    public long getPhysicalRow() {
        return -1;
    }

    /**
     * The column index in the physical file (starting from 0), in case the file
     * of reference is in text format. In case paramter is not set -1 is
     * returned.
     */
    @Value.Default
    public long getPhysicalColumn() {
        return -1;
    }

    /**
     * A reference to one or more elements expressed as a JsonPath. See
     * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
     * syntax</a>
     *
     */
    @Value.Default
    public String getJsonPath() {
        return "*";
    }

    @Value.Check
    protected void check() {
        Preconditions.checkState(getJsonPath().trim().length() > 0, "Found empty jsonpath!");
        Preconditions.checkState(getPhysicalRow() >= -1, "physical row should be grater or equal to -1, found instead %s ", getPhysicalRow());
        Preconditions.checkState(getPhysicalColumn() >= -1, "physical column should be grater or equal to -1, found instead %s ", getPhysicalColumn());
    }

    /**
     * Creates a reference in jsonPath format.
     *
     * @param jsonPath A reference to one or more elements expressed as a
     * JsonPath. See
     * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
     * syntax</a>
     *
     */
    public static Ref of(String jsonPath) {
        return Ref.of("", -1, -1, jsonPath);
    }

    /**
     * Returns singleton with '*' as jsonpath.
     */
    public static Ref of() {
        return Ref.of("", -1, -1, "$");
    }

}

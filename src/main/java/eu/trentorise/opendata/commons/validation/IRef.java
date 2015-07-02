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

import java.io.Serializable;
import javax.annotation.concurrent.Immutable;
import org.immutables.value.Value;

/**
 * An immutable reference to one or more elements of tree-like object. Reference
 * is expressed using JsonPath syntax. See
 * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
 * syntax</a> and
 * <a href="https://groups.google.com/forum/#!topic/jsonpath/5G3bf2wx4zM" target="_blank">
 * this link for regexes </a>
 *
 * @author David Leoni
 */
@Immutable
public interface IRef extends Serializable {

    /**
     * A reference to one or more elements expressed as a JsonPath. See
     * <a href="https://github.com/jayway/JsonPath" target="_blank">JSONPath
     * syntax</a>
     *
     */
    public String getJsonPath();

    /**
     * An identifier (possibly an IRI) for the original document.
     *
     */
    public String getDocumentId();

    /**
     * The row index in the physical file (starting from 0), in case the file of
     * reference is in text format. In case paramter is not set -1 is returned.
     */
    public int getPhysicalRow();

    /**
     * The column index in the physical file (starting from 0), in case the file
     * of reference is in text format. In case paramter is not set -1 is
     * returned.
     */
    public int getPhysicalColumn();
}

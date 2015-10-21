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

import eu.trentorise.opendata.commons.BuilderStylePublic;
import eu.trentorise.opendata.commons.internal.joda.time.field.OffsetDateTimeField;

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
@BuilderStylePublic
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
     * reference is in text format. In case parameter is not set -1 is returned.
     */
    @Value.Default
    public long getPhysicalRow() {
	return -1;
    }

    /**
     * The column index in the physical file (starting from 0), in case the file
     * of reference is in text format. In case parameter is not set -1 is
     * returned.
     */
    @Value.Default
    public long getPhysicalColumn() {
	return -1;
    }

    /**
     * A reference to one or more elements inside the document with id
     * {@link #getDocumentId() documentId}, expressed as a
     * {@link eu.trentorise.opendata.traceprov.path.TracePath TracePath}. By
     * default it is the empty string (which we assume selects everything, along
     * with '*').
     *
     */
    @Value.Default
    public String getTracePath() {
	return "";
    }

    @Value.Check
    protected void check() {
	// todo need decent TracePath checking
	Preconditions.checkState(getPhysicalRow() >= -1,
		"physical row should be grater or equal to -1, found instead %s ", getPhysicalRow());
	Preconditions.checkState(getPhysicalColumn() >= -1,
		"physical column should be grater or equal to -1, found instead %s ", getPhysicalColumn());
    }

    /**
     * Creates a reference out of a {@link #ATracePath TracePath} format. The
     * reference is supposed to point to an unknown document.
     *
     * @param jsonPath
     *            A reference to one or more elements expressed as a JsonPath.
     *            See
     *            <a href="https://github.com/jayway/JsonPath" target="_blank">
     *            JSONPath syntax</a>
     *
     * @see #ofDocumentId(String)
     */
    public static Ref ofPath(String tracePath) {
	return Ref.builder().setTracePath(tracePath).build();
    }

    /**
     * Creates a reference to a document.
     *
     * @param documentId
     *            an identifier for a document (possibly an IRI).
     * 
     * @see #ofPath(String)
     */
    public static Ref ofDocumentId(String documentId) {
	return Ref.builder().setDocumentId(documentId).build();
    }

    /**
     * Builds an uri for the reference like this: {@link #getDocumentId()
     * documentId}#{@link #getTracePath() tracePath};
     * 
     * If any of the two components is empty, the '#' is omitted
     * 
     * @throws IllegalStateException
     *             if both documentid and tracepath are empty.
     */
    public String uri() {
	if (getDocumentId().isEmpty() && (getTracePath().isEmpty())) {
	    throw new IllegalStateException("Can't create an empty uri!");
	}
	if (getDocumentId().isEmpty()) {
	    return getTracePath();
	} else if (getTracePath().isEmpty() || getTracePath().equals("*")) {
	    return getDocumentId();
	} else {
	    return getDocumentId() + "#" + getTracePath();
	}
    }
}

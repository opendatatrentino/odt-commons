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
import java.util.List;
import javax.annotation.Nullable;

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
public interface IValidationError extends Serializable {

    /**
     * A reference to logical and physical position of the error.
     */
    public Ref getRef();
    
    /**
     * The optional error code associated to the error.
     */
    @Nullable
    public Object getErrorCode();

    /**
     * Returns the reason, as a sequence of objects to be displayed to the user
     * in the style of Javascript's console.log("Expected ", x, " Found ", y )
     */
    public List getReason();

}

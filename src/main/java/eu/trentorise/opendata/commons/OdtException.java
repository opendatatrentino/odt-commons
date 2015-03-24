/*
 * Copyright 2015 Trento Rise  (trentorise.eu) 
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

/**
 * Generic Odt Commons runtime exception
 *
 * @author David Leoni
 */
public class OdtException extends RuntimeException {

    private OdtException() {
        super();
    }

    /**
     * Creates the exception with the provided message.
     */
    public OdtException(String msg) {
        super(msg);
    }

    /**
     * Creates the exception with the provided message and throwable.
     */
    public OdtException(String msg, Throwable tr) {
        super(msg, tr);
    }

}

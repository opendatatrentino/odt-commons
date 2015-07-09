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
package eu.trentorise.opendata.commons.test;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import eu.trentorise.opendata.commons.validation.AValidationError;
import eu.trentorise.opendata.commons.validation.ErrorLevel;
import eu.trentorise.opendata.commons.validation.ValidationError;

/**
 *
 * @author David Leoni
 */
public class ValidationTest {

    private static class MyValidationError extends AValidationError {

        private String myField;

        private MyValidationError() {
            super();
        }

        private MyValidationError(String myField) {
            super();
            checkNotNull(myField);
            this.myField = myField;
        }

        public String getMyField() {
            return myField;
        }

        public static MyValidationError of() {
            return new MyValidationError();
        }

        public static MyValidationError of(String myField) {
            return new MyValidationError(myField);
        }

        @Override
        public String toString() {
            return "MyValidationError{" + "myField=" + myField + "," + super.toString() + '}';
        }

    }

    @Test
    public void testValidation() {

        assertEquals("*", ValidationError.of().getRef().getJsonPath());
        assertEquals("$", ValidationError.of("$", ErrorLevel.INFO, 0, "").getRef().getJsonPath());
        assertEquals("*", ValidationError.of((String) null, ErrorLevel.INFO, 0, "").getRef().getJsonPath());
        assertEquals("*", ValidationError.of("", null, 0, "").getRef().getJsonPath());

        assertEquals("abc", ValidationError.of("$", ErrorLevel.INFO, 0, "a%sc", "b").formatReason());
        assertEquals(ImmutableList.of("b"), ValidationError.of("$", ErrorLevel.INFO, 0, "a", "b").getReasonArgs());

    }
}

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
import com.google.common.collect.ImmutableMap;
import com.ibm.icu.text.MessageFormat;
import org.junit.Test;
import eu.trentorise.opendata.commons.validation.AValidationError;
import eu.trentorise.opendata.commons.validation.ErrorLevel;
import eu.trentorise.opendata.commons.validation.Ref;
import eu.trentorise.opendata.commons.validation.ValidationError;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

        assertEquals("*", ValidationError.of().getRef().getTracePath());
        
        ValidationError.of(Ref.of(), ErrorLevel.SEVERE, "0", "", ImmutableMap.of("", ""));
        
        assertEquals("$", ValidationError.of(Ref.of("$"), ErrorLevel.INFO, "0", "").getRef().getTracePath());
        assertEquals("*", ValidationError.of((Ref) null, ErrorLevel.INFO, "0", "").getRef().getTracePath());        
        
        ValidationError v1 = ValidationError.of(Ref.of(), ErrorLevel.INFO, "0", "a{0}c", "0", "b");
        
        // this is unbelievable, if I don't put (Map) the compiler picks the wrong method with varargs Object... , 
        // but passing a HashMap the compiler works as expected!!!!
        assertEquals("ab", MessageFormat.format("a{x}",  (Map) ImmutableMap.of("x", "b"))); 
        HashMap hm = new HashMap();
        hm.put("x","b");
        assertEquals("ab", MessageFormat.format("a{x}", hm));

        HashMap<String,Object> hm2 = new HashMap();
        hm2.put("0","b");
        assertEquals("ab", MessageFormat.format("a{0}", hm2));
                
        assertEquals(ImmutableMap.of("b","c"), ValidationError.of(Ref.of(), ErrorLevel.INFO, "0", "a", "b", "c").getReasonArgs());

    }
    
    @Test
    @SuppressWarnings({"IncompatibleEquals", "ObjectEqualsNull"})
    public void testEquals(){
        assertFalse(ValidationError.of().equals(""));
        assertFalse(ValidationError.of().equals(null));
        assertEquals(ValidationError.of(Ref.of("a"), ErrorLevel.SEVERE, "1", "a{x}", "x",3).hashCode(), 
                   ValidationError.of(Ref.of("a"), ErrorLevel.SEVERE, "1", "a{x}", "x",3).hashCode());                
        
    }    
}

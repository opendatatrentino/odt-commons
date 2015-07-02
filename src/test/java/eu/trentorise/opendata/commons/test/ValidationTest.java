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
import eu.trentorise.opendata.commons.validation.IRef;
import eu.trentorise.opendata.commons.validation.ValidationError;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import eu.trentorise.opendata.commons.validation.IValidationError;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author David Leoni
 */
public class ValidationTest {
    
    private static class MyValidationError implements IValidationError {
        
        private String myField;
        
        private ValidationError validationError;

        private MyValidationError(){
            this.validationError = ValidationError.of();
        }
        
        private MyValidationError(String myField){
            checkNotNull(myField);
            this.validationError = ValidationError.of();
            this.myField = myField;
        }
        
        @Override
        public IRef getRef() {
            return validationError.getRef();
        }

        @Override
        public Object getErrorCode() {
            return validationError.getErrorCode();
        }

        @Override
        public List getReason() {
            return validationError.getReason();
        }

        public String getMyField() {
            return myField;
        }
                        
        
        public static MyValidationError of(){            
            return new MyValidationError();
        }
        
        public static MyValidationError of(String myField){            
            return new MyValidationError(myField);
        }
        

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 89 * hash + Objects.hashCode(this.myField);
            hash = 89 * hash + Objects.hashCode(this.validationError);
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
            final MyValidationError other = (MyValidationError) obj;
            if (!Objects.equals(this.myField, other.myField)) {
                return false;
            }
            if (!Objects.equals(this.validationError, other.validationError)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "MyValidationError{" + "myField=" + myField + ", validationError=" + validationError + '}';
        }
    
        
        
    }
    
    
    @Test
    public void testValidation(){
        assertEquals("*", ValidationError.of().getRef().getJsonPath());
        assertEquals("$", ValidationError.of("$",null, "").getRef().getJsonPath());
        assertEquals("*", ValidationError.of((String) null,null,"").getRef().getJsonPath());
        assertEquals("*", ValidationError.of("",null,"").getRef().getJsonPath());       
    }
}

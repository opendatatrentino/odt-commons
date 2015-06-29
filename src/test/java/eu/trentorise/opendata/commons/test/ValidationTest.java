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

import eu.trentorise.opendata.commons.ValidationError;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author David Leoni
 */
public class ValidationTest {
    
    @Test
    public void testValidation(){
        assertEquals("$", ValidationError.of().getJsonPath());
        assertEquals("$", ValidationError.of("$","").getJsonPath());

        try {
            ValidationError.of("","");
            Assert.fail();
        } catch (IllegalArgumentException ex){
            
        }
    }
}

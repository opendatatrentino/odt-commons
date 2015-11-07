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

import com.google.common.collect.ImmutableList;
import eu.trentorise.opendata.commons.TodConfig;
import eu.trentorise.opendata.commons.TodUtils;
import eu.trentorise.opendata.commons.validation.Preconditions;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author David Leoni
 * @since 1.1
 */
public class PreconditionsTest {
    
    @BeforeClass
    public static  void setUpClass() {        
        TodConfig.init(TodUtilsTest.class);
    }  
    

    @Test
    public void testChecker() {
        try {
            TodUtils.checkNotEmpty((String) null, "my string");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {

        }
        try {
            TodUtils.checkNotEmpty("", "my string");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {

        }

        try {
            TodUtils.checkNotEmpty(ImmutableList.of(), "my string");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {

        }

        TodUtils.checkNotEmpty(ImmutableList.of("a"), "my string");

        try {
            // not that ints wouldn't be picked by the method. See http://stackoverflow.com/questions/5405673/java-varags-method-param-list-vs-array            
            Preconditions.checkNotEmpty(new Integer[]{}, "my string");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {

        }

        try {
            // not that ints wouldn't be picked by the method. See http://stackoverflow.com/questions/5405673/java-varags-method-param-list-vs-array            
            Preconditions.checkNotEmpty(new Integer[]{}, "a%sc", "b");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("abc"));
        }

        try {
            // not that ints wouldn't be picked by the method. See http://stackoverflow.com/questions/5405673/java-varags-method-param-list-vs-array            
            Preconditions.checkNotEmpty((Integer[]) null, "a%sc", "b");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("abc"));
        }

        Preconditions.checkNotEmpty(new Integer[]{1}, "my string");

        try {
            Preconditions.checkNotEmpty("", "a%s", "bc");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("abc"));
        }

        Preconditions.checkNotEmpty("a", "a%s", "bc");

    }
}

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
package eu.trentorise.opendata.commons.test;

import com.google.common.collect.Multimap;
import eu.trentorise.opendata.commons.Dict;
import eu.trentorise.opendata.commons.LocalizedString;
import java.util.Arrays;
import java.util.Locale;
import org.immutables.value.internal.google.common.collect.ImmutableList;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author David Leoni
 */
public class MultilingualStringsTest {

    @BeforeClass
    public static void setUpClass() {        
        OdtTestConfig.of().loadLogConfig();
    }    

    @Test
    public void testDict_0() {
        Dict dict = Dict.of();
        assertTrue(dict.locales().isEmpty());        
    }
    
    
      @Test
    public void testDict_1() {        
        Dict dict = Dict.of("a");
        assertEquals(dict.string(Locale.ROOT), "a");        
    }
               
    @Test
    public void testDict() {
        assertTrue(Dict.of().isEmpty());
        assertTrue(Dict.of("").isEmpty());
        assertFalse(Dict.of("hello").isEmpty());

        assertEquals(ImmutableList.of("a", "a", "b"),
                     Dict.builder().put("a").put("a","b").build().strings(Locale.ROOT));
        
        Dict dict = Dict.builder()
                .put(Locale.FRENCH, "A")
                .put(Locale.ITALIAN, "b")
                .put(Locale.ENGLISH, "c")
                .build();

        assertTrue(dict.contains("a"));
        assertTrue(dict.contains("B"));

        assertEquals(LocalizedString.of(Locale.ENGLISH, "c"),
                     dict.anyString(Locale.CHINESE));
        
        assertEquals(LocalizedString.of(), Dict.of().anyString(Locale.ITALIAN));

        assertEquals(Dict.of("b", "b"),
                     Dict.builder().put(Dict.of("b")).put(Dict.of("b")).build());

                        
        assertEquals(Dict.builder().put(Dict.of(Locale.GERMAN, "b")).put(Dict.of(Locale.ITALIAN, "a")).build(),
                     Dict.builder().put(Dict.of(Locale.ITALIAN, "a")).put(Dict.of(Locale.GERMAN, "b")).build()
        );                
        
    }
    
    @Test
    public void testNonEmpty(){
        Dict dict = Dict.builder().put(Locale.FRENCH, "", "a").build();
        assertEquals("a", dict.nonEmptyString(Locale.FRENCH));        
    }
    
    @Test
    public void testWith(){
        assertEquals(Dict.of("a").with("b"), Dict.of("a","b"));
        assertNotEquals(Dict.of("b").with("a"), Dict.of("a","b"));
        assertEquals(Dict.of("a").with(Locale.ITALIAN, "b"), Dict.of(Locale.ITALIAN, "b").with("a"));
        assertNotEquals(Dict.of(Locale.ENGLISH, "a").with(Locale.ITALIAN, "b"), Dict.of(Locale.ITALIAN, "b").with("a"));
    }
    @Test
    public void testNullHostility(){
        
        try {
            Dict.of((String) null);
            Assert.fail("Should not arrive here!");
        } catch(NullPointerException ex){
            
        }
               
        try {
            Dict.of((LocalizedString) null);
            Assert.fail("Should not arrive here!");
        } catch(NullPointerException ex){
            
        }
        
        try {
            Dict.of((Multimap) null);
            Assert.fail("Should not arrive here!");
        } catch(NullPointerException ex){
            
        }
        
        try {
            Dict.of((String) null);
            Assert.fail("Should not arrive here!");
        } catch(NullPointerException ex){
            
        }
        
        try {            
            Dict.of(Locale.ITALIAN, Arrays.asList((String) null));
            Assert.fail("Should not arrive here!");
        } catch(NullPointerException ex){
            
        }

        
        try {
            Dict.of().with((String) null);
            Assert.fail("Should not arrive here!");
        } catch(NullPointerException ex){
            
        }

        try {
            Dict.of().with((Locale) null);
            Assert.fail("Should not arrive here!");
        } catch(NullPointerException ex){
            
        }
        
        try {
            Dict.of().with(Locale.FRENCH, "", null);
            Assert.fail("Should not arrive here!");
        } catch(NullPointerException ex){
            
        }  
        
        try {
            Dict.builder().put((String) null);
            Assert.fail("Should not arrive here!");
        } catch(NullPointerException ex){
            
        }        
        
    }
    
    
    @Test
    public void example1(){
        
        // Generally, there are only factory methods starting with 'of' and no 'new' constructor:                
        LocalizedString myLocalizedString = LocalizedString.of(Locale.ITALIAN, "ciao");
        
        // Default language is always Locale.ROOT:
        LocalizedString localizedString = LocalizedString.of("string with unknwon language");
        
        assert Locale.ROOT.equals(localizedString.getLocale());
        
        // we are null hostile:
        try {
            LocalizedString.of(null);
        } catch(NullPointerException ex){
            
        }
        
        // factory method for a Dict:
        Dict.of(Locale.ENGLISH, "hello", "my friend");
        
        // Dict builder:
        Dict myDict = Dict.builder().put(Locale.ENGLISH, "hello")
                                    .put(Locale.ENGLISH, "hello again")
                                    .put(Locale.ITALIAN, "ciao")
                                    .build();
        
        assert "hello".equals(myDict.string(Locale.ENGLISH));
        assert "hello again".equals(myDict.strings(Locale.ENGLISH).get(1)); 
        assert "ciao".equals(myDict.string(Locale.ITALIAN)); 
                
    }
    
}

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


import com.google.common.collect.ImmutableList;
import eu.trentorise.opendata.commons.BuildInfo;
import eu.trentorise.opendata.commons.OdtConfig;
import eu.trentorise.opendata.commons.OdtUtils;
import java.util.Locale;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author David Leoni
 */
public class OdtUtilsTest {
    
    @BeforeClass
    public static  void setUpClass() {        
        OdtConfig.init(OdtUtilsTest.class);
    }  
    

    @Test
    public void testLanguageTag(){
        // we want gracious null handling
        assertEquals(Locale.ROOT, OdtUtils.languageTagToLocale(null));
        assertEquals("", OdtUtils.localeToLanguageTag(null));
        assertEquals(Locale.ITALIAN, OdtUtils.languageTagToLocale(OdtUtils.localeToLanguageTag(Locale.ITALIAN)));
    }
    
    @Test
    public void testBuildInfo(){
        BuildInfo buildInfo = OdtConfig.of(OdtConfig.class).getBuildInfo();
        assertTrue(buildInfo.getScmUrl().length() > 0);
        assertTrue(buildInfo.getVersion().length() > 0);
                
    }

    
    @Test
    public void testChecker(){
        try {
            OdtUtils.checkNotEmpty((String) null, "my string");
            Assert.fail();
        } catch (IllegalArgumentException ex){
            
        }
        try {
            OdtUtils.checkNotEmpty("", "my string");
            Assert.fail();
        } catch (IllegalArgumentException ex){
            
        }
        
        try {
            OdtUtils.checkNotEmpty(ImmutableList.of(), "my string");
            Assert.fail();
        } catch (IllegalArgumentException ex){
            
        }
        
        OdtUtils.checkNotEmpty(ImmutableList.of("a"), "my string");
     
        try {
            OdtUtils.checkNotEmpty("", "a%s", "bc");
            Assert.fail();
        } catch (IllegalArgumentException ex){
            assertTrue(ex.getMessage().contains("abc"));
        }
        
        OdtUtils.checkNotEmpty("a", "a%s","bc");
    }
    
    @Test
    public void testIdParser(){
        
        assertEquals(1, OdtUtils.parseNumericalId("", "1"));
        assertEquals(1, OdtUtils.parseNumericalId("a", "a1"));

        try {
            OdtUtils.parseNumericalId("", "");
            Assert.fail();
        } catch (IllegalArgumentException ex){
            
        }
        
        try {
            OdtUtils.parseNumericalId("a", "123");
            Assert.fail();
        } catch (IllegalArgumentException ex){
            
        }
        
        
        try {
            OdtUtils.parseNumericalId("a", "ab");
            Assert.fail();
        } catch (IllegalArgumentException ex){
            
        }
        
        try {
            OdtUtils.parseNumericalId("a", "bb");
            Assert.fail();
        } catch (IllegalArgumentException ex){
            
        }        
        
    }
    
    @Test
    public void addRemoveSlash(){
        
        assertEquals("a/", OdtUtils.addSlash("a"));
        assertEquals("a/", OdtUtils.addSlash("a/"));
        assertEquals("a", OdtUtils.removeTrailingSlash("a/"));
        assertEquals("a", OdtUtils.removeTrailingSlash("a//"));
        
        assertEquals("a", OdtUtils.removeTrailingSlash(OdtUtils.addSlash("a")));
        assertEquals("a", OdtUtils.removeTrailingSlash(OdtUtils.addSlash("a/")));
    }
    
    
    
    @Test
    public void testCheckNotDirtyUrl(){
        
        try {
            OdtUtils.checkNotDirtyUrl(null, "");
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalArgumentException ex){
            
        }
        
        try {
            OdtUtils.checkNotDirtyUrl("", "");
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalArgumentException ex){
            
        }
        
        try {
            OdtUtils.checkNotDirtyUrl("null", "");
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalArgumentException ex){
            
        }     
        
        try {
            OdtUtils.checkNotDirtyUrl("adfasdf/null", "");
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalArgumentException ex){
            
        }        
        
        assertEquals("a", OdtUtils.checkNotDirtyUrl("a", "msg"));
    }
    
    @Test
    public void testIsNotEmpty(){
        assertFalse(OdtUtils.isNotEmpty(null));
        assertFalse(OdtUtils.isNotEmpty(""));
        assertTrue(OdtUtils.isNotEmpty("a"));
    }
    
    
}

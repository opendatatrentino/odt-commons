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

import eu.trentorise.opendata.commons.TodConfig;
import eu.trentorise.opendata.commons.SemVersion;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author David Leoni
 */
public class VersionTest {
    
    @BeforeClass
    public static void setUpClass() {        
        TodConfig.init(VersionTest.class);
    }  
        
    @Test
    public void testVersion(){
        
        SemVersion sv1 = SemVersion.of("1.2.3");
        assertEquals(1, sv1.getMajor());
        assertEquals(2, sv1.getMinor());
        assertEquals(3, sv1.getPatch());
        assertEquals("", sv1.getPreReleaseVersion());
        assertEquals(sv1, SemVersion.of(sv1.toString()));
        
        SemVersion sv2 = SemVersion.of("1.2.3-bla");
        assertEquals(1, sv2.getMajor());
        assertEquals(2, sv2.getMinor());
        assertEquals(3, sv2.getPatch());
        assertEquals("bla", sv2.getPreReleaseVersion());
        assertEquals(sv2, SemVersion.of(sv2.toString()));
        
        SemVersion sv3 = SemVersion.of("1.2.3-");
        assertEquals(1, sv3.getMajor());
        assertEquals(2, sv3.getMinor());
        assertEquals(3, sv3.getPatch());
        assertEquals("", sv3.getPreReleaseVersion());
        assertEquals(sv3, SemVersion.of(sv3.toString()));        
        
        try {
            SemVersion.of("");
            Assert.fail("shouldn't arrive here!");
        } catch (Exception ex){
            
        }
    }
}

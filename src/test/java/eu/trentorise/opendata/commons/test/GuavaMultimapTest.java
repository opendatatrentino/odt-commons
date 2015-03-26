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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import eu.trentorise.opendata.commons.OdtConfig;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 * 
 * @author David Leoni
 */
public class GuavaMultimapTest {
    
    @BeforeClass
    public static  void setUpClass() {        
         OdtConfig.init(GuavaMultimapTest.class);
    } 
            
    @Test
    public void testListMultimap(){
         Multimap map = LinkedListMultimap.create();
         map.put("a", 1);
         map.put("a", 2);
         map.putAll("b",ImmutableList.of(1,2));
         map.putAll("b",ImmutableList.of(3,4));
         assertEquals(ImmutableList.of(1,2), 
                      map.get("a"));
         assertEquals(ImmutableList.of(1,2,3,4), 
                      map.get("b"));
         
    }
    
    @Test
    public void testSetMultimap(){
         Multimap map = LinkedHashMultimap.create();
         map.put("a", 1);
         map.put("a", 1);
         assertEquals(ImmutableSet.of(1), 
                      map.get("a"));
    }    
    
    @Test
    public void testImmutableMultimapPut(){
         ImmutableListMultimap.Builder mapb = ImmutableListMultimap.builder();
         mapb.put("a", 1);
         mapb.put("a", 1);
         ImmutableListMultimap map = mapb.build();
         assertEquals(ImmutableList.of(1,1),
                      map.get("a"));
    }    
    
    
    @Test
    public void testImmutableMultimapPutAll(){
         ImmutableListMultimap.Builder mapb = ImmutableListMultimap.builder();
         mapb.putAll("a", 1, 2);
         mapb.putAll("a", 1, 2);         
         ImmutableListMultimap map = mapb.build();
         assertEquals(ImmutableList.of(1,2,1,2), 
                      map.get("a"));
    }           
}

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

import eu.trentorise.opendata.commons.BuildInfo;
import eu.trentorise.opendata.commons.TodConfig;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author David Leoni
 */
public class TodConfigTest {
    
    @BeforeClass
    public static  void setUpClass() {        
        TodConfig.init(TodConfigTest.class);
    }

    @Test
    public void testConfig() {
        BuildInfo buildInfo = TodConfig.of(TodConfigTest.class).getBuildInfo();
        assertTrue(buildInfo.getScmUrl().length() > 0);
        assertTrue(buildInfo.getVersion().length() > 0);
        assertTrue(TodConfig.isLoggingConfigured());
        assertTrue(TodConfig.of(this.getClass()).getBuildInfo().getScmUrl().length() > 0);                
    }
    
    

}

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

import eu.trentorise.opendata.commons.internal.joda.time.DateTime;
import eu.trentorise.opendata.commons.internal.joda.time.tz.Provider;
import eu.trentorise.opendata.commons.internal.joda.time.tz.ZoneInfoProvider;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author David Leoni
 */
public class JodaTest {

    private static final Logger LOG = Logger.getLogger(JodaTest.class.getName());

    /**
     * If this fails it means in file DateTimeZone there is a hardcoded path to
     * wrong directory holding the tzdata, or tzdata is just missing in the
     * build. When copying code I manually changed the path to 'tzdata' and put tzdata under
     * src/main/resources
     *
     * @throws IOException
     */
    @Test
    public void testTzdata() throws IOException {
        Provider provider = new ZoneInfoProvider("tzdata");
    }

    @Test
    public void test() throws IOException {
        LOG.log(Level.FINE, "The time is..{0}", DateTime.now());

        //return validateProvider(provider);
    }
}

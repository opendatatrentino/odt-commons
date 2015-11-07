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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import eu.trentorise.opendata.commons.BuildInfo;
import eu.trentorise.opendata.commons.TodConfig;
import eu.trentorise.opendata.commons.TodUtils;
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
public class TodUtilsTest {

    @BeforeClass
    public static void setUpClass() {
        TodConfig.init(TodUtilsTest.class);
    }

    @Test
    public void testLanguageTag() {
        // we want gracious null handling
        assertEquals(Locale.ROOT, TodUtils.languageTagToLocale(null));
        assertEquals("", TodUtils.localeToLanguageTag(null));
        assertEquals(Locale.ITALIAN, TodUtils.languageTagToLocale(TodUtils.localeToLanguageTag(Locale.ITALIAN)));
        try {
            Locale.forLanguageTag(null);
        } catch (NullPointerException ex) {

        }
    }

    @Test
    public void testBuildInfo() {
        BuildInfo buildInfo = TodConfig.of(TodConfig.class)
                                       .getBuildInfo();
        assertTrue(buildInfo.getScmUrl()
                            .length() > 0);
        assertTrue(buildInfo.getVersion()
                            .length() > 0);

    }

    @Test
    public void testIdParser() {

        assertEquals(1, TodUtils.parseNumericalId("", "1"));
        assertEquals(1, TodUtils.parseNumericalId("a", "a1"));

        try {
            TodUtils.parseNumericalId("", "");
            Assert.fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            TodUtils.parseNumericalId("a", "123");
            Assert.fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            TodUtils.parseNumericalId("a", "ab");
            Assert.fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            TodUtils.parseNumericalId("a", "bb");
            Assert.fail();
        } catch (IllegalArgumentException ex) {

        }

    }

    @Test
    public void addRemoveSlash() {

        assertEquals("a/", TodUtils.addSlash("a"));
        assertEquals("a/", TodUtils.addSlash("a/"));
        assertEquals("a", TodUtils.removeTrailingSlash("a/"));
        assertEquals("a", TodUtils.removeTrailingSlash("a//"));

        assertEquals("a", TodUtils.removeTrailingSlash(TodUtils.addSlash("a")));
        assertEquals("a", TodUtils.removeTrailingSlash(TodUtils.addSlash("a/")));
    }

    @Test
    public void testCheckNotDirtyUrl() {

        try {
            TodUtils.checkNotDirtyUrl(null, "");
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalArgumentException ex) {

        }

        try {
            TodUtils.checkNotDirtyUrl("", "");
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalArgumentException ex) {

        }

        try {
            TodUtils.checkNotDirtyUrl("null", "");
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalArgumentException ex) {

        }

        try {
            TodUtils.checkNotDirtyUrl("adfasdf/null", "");
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalArgumentException ex) {

        }

        assertEquals("a", TodUtils.checkNotDirtyUrl("a", "msg"));
    }

    @Test
    public void testIsNotEmpty() {
        assertFalse(TodUtils.isNotEmpty(null));
        assertFalse(TodUtils.isNotEmpty(""));
        assertTrue(TodUtils.isNotEmpty("a"));
    }

    @Test
    public void testParseUrlParams() {
        Multimap<String, String> m = TodUtils.parseUrlParams("http://blabla.com/?a=1&b=2&b=3");

        assertEquals(2, m.keySet()
                         .size());
        assertEquals(ImmutableList.of("1"), m.get("a"));
        assertEquals(ImmutableList.of("2", "3"), m.get("b"));
    }

    @Test
    public void testParseUrlParamsWrongUrl() {
        try {
            TodUtils.parseUrlParams("bla");
        } catch (IllegalArgumentException ex) {

        }
    }

    @Test
    public void testParseUrlParamsEmptyParam() {
        Multimap<String, String> m = TodUtils.parseUrlParams("http://blabla.com/?a=&b=2");
        assertEquals(2, m.keySet()
                         .size());
        assertEquals(ImmutableList.of(""), m.get("a"));
    }

    @Test
    public void testPutKey() {
        ImmutableMap<String, Integer> m = ImmutableMap.of("a", 1);

        ImmutableMap<String, Integer> newM1 = TodUtils.putKey(m, "a", 2);
        assertEquals(new Integer(2), newM1.get("a"));
        
        ImmutableMap<String, Integer> newM2 = TodUtils.putKey(m, "b", 2);
        assertEquals(new Integer(1), newM2.get("a"));
        assertEquals(new Integer(2), newM2.get("b"));
        
    }

}

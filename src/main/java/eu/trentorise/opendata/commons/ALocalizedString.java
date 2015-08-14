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
package eu.trentorise.opendata.commons;

import java.io.Serializable;
import java.util.Locale;
import org.immutables.value.Value;

/**
 * A string with associated a locale. This class is used to generate the
 * immutable {@link eu.trentorise.opendata.commons.LocalizedString}.
 *
 * @author David Leoni
 */
@Value.Immutable
@SimpleStyle
abstract class ALocalizedString implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Returns the locale of the string. Default locale is {@link Locale#ROOT}
     * @deprecated use {#loc()} instead. (This one is kept for java bean compatibility)
     */
    @Value.Default    
    public Locale getLocale() {
        return Locale.ROOT;
    }

    /**
     * Returns the string, which is never null.
     * @deprecated use {#str()} instead. (This one is kept for java bean compatibility)
     */
    @Value.Default    
    public String getString() {
        return "";
    }

    /**
     * Returns the string, which is never null.
     * @since 1.1
     */
    public String str(){
        return getString();
    }

    /**
     * Returns the locale. Default locale is {@link Locale#ROOT}
     * @since 1.1
     */    
    public Locale loc(){
        return getLocale();
    }
    
    /**
     * Returns a LocalizedString with default locale {@link Locale#ROOT}
     *
     * @param string a non-null string.
     */
    public static LocalizedString of(String string) {
        return LocalizedString.of(Locale.ROOT, string);
    }

}

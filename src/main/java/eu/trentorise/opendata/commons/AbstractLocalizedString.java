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
 *
 * @author David Leoni
 */
@Value.Immutable
@SimpleStyle
abstract class AbstractLocalizedString implements Serializable {

    private static final long serialVersionUID = 1L;    
    
    /**
     * Default locale is {@link Locale#ROOT}
     */
    @Value.Default
    @Value.Parameter
    public Locale getLocale() {
        return Locale.ROOT;
    }

    @Value.Default
    @Value.Parameter
    public String getString() {
        return "";
    }

    public static LocalizedString of(String string) {
        return LocalizedString.of(Locale.ROOT, string);
    }

}

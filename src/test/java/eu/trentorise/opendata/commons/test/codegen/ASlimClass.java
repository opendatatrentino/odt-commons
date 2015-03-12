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
package eu.trentorise.opendata.commons.test.codegen;

import eu.trentorise.opendata.commons.SimpleStyle;
import org.immutables.value.Value;

/**
 * Simple immutable classes without builder will have public generated classes even if their
 * abstract class has package visibility.
 *
 * @author David Leoni
 */
@Value.Immutable
@SimpleStyle
abstract class ASlimClass {

    @Value.Parameter
    @Value.Default
    public int getProp1(){
        return 0;
    };

    @Value.Parameter
    @Value.Default
    public String getProp2() {
        return "";
    }
;
}

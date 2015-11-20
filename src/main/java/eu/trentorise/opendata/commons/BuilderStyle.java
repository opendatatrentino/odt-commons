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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.immutables.value.Value;

/**
 * Annotated abstract class (let's suppose it's named {@code AMyClass}) will be
 * used as template for generating a corresponding immutable class named
 * {@code MyClass}, along with a builder to create instances of it.
 * 
 * <p>
 * This annotation will configure
 * <a href="http://immutables.github.io/">Immutables</a> to:
 * <ul>
 * <li>expect the annotated class to have bean style getters</li>
 * <li>generate an empty object retrievable with a method of the form
 * {@code MyClass.of()}.</li>
 * <li>generate a builder with bean-style setters.</li>
 * <li>use the same visibility in the immutable implementation as the abstract
 * one. See {@link BuilderStylePublic} to force public visibility.</li>
 * </ul>
 * </p>
 * <p>
 * <b>NOTE:</b> Annotated abstract class name <strong>MUST</strong> begin with
 * 'A' or 'Abstract'
 * </p>
 * 
 * @author David Leoni
 * @see SimpleStyle
 * @see BuilderStylePublic
 */
@Value.Style(get = { "is*", "get*" }, init = "set*", typeAbstract = { "Abstract*",
        "A*" }, typeImmutable = "", defaults = @Value.Immutable(singleton = true) )
@Target({ ElementType.TYPE, ElementType.PACKAGE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.SOURCE)
public @interface BuilderStyle {
}

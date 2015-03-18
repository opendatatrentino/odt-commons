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
package eu.trentorise.opendata.commons;

import java.io.Serializable;
import org.immutables.value.Value;

/**
 * Simple class to model a semantic version, see
 * <a href="http://semver.org">semver.org</a>
 *
 * @author David Leoni
 */
@Value.Immutable
@SimpleStyle
public abstract class ASemVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value.Parameter
    @Value.Default
    public int getMajor() {
        return 0;
    }

    @Value.Parameter
    @Value.Default
    public int getMinor() {
        return 0;
    }

    @Value.Parameter
    @Value.Default
    public int getPatch() {
        return 0;
    }

    /** 
     * Returns the prerelease version, (i.e. alphanumerical string such as SNAPSHOT, RC1, alpha, ....)
     */
    @Value.Parameter
    @Value.Default
    public String getPreReleaseVersion() {
        return "";
    }

    /**
     * Parses a string of the form x.y.z or x.y.z-p, with x,y,z being numbers
     * and p any string.
     *
     * @throws IllegalArgumentException on parsing error.
     */
    public static SemVersion of(String string) {

        String[] tokens = string.split("\\.");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Couldn't find three numbers separated by dots in version string " + string);
        }
        int x;
        int y;
        int z;
        String p;
        x = Integer.parseInt(tokens[0]);
        y = Integer.parseInt(tokens[1]);
        int minusIndex = tokens[2].indexOf('-');
        String zString;

        if (minusIndex == -1) {
            zString = tokens[2];
            p = "";
        } else {
            zString = tokens[2].substring(0, minusIndex);
            if (minusIndex == tokens[2].length() - 1) {
                p = "";
            } else {
                p = tokens[2].substring(minusIndex + 1);
            }

        }

        z = Integer.parseInt(zString);

        return SemVersion.of(x, y, z, p);
    }

    /**
     * Returns a parseable string represention of the semantic version, like
     * i.e. 1.2.3-SNAPSHOT
     */
    @Override
    public String toString() {
        String ret = "" + getMajor() + "." + getMinor() + "." + getPatch();
        if (getPreReleaseVersion().length() > 0) {
            return ret + "-" + getPreReleaseVersion();
        } else {
            return ret;
        }
    }
}

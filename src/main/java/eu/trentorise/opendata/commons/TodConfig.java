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

import static com.google.common.base.Preconditions.checkNotNull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.annotation.Nullable;

import exceptions.TodException;
import exceptions.TodNotFoundException;

/**
 * Class for configuration and JUL logging initalization.
 *
 * @author David Leoni
 */
public final class TodConfig {

    public static final String LOG_PROPERTIES_PATH = "tod.commons.logging.properties";

    public static final String LOG_PROPERTIES_CONF_PATH = "conf/" + LOG_PROPERTIES_PATH;

    public static final String BUILD_PROPERTIES_PATH = "tod.commons.build.properties";

    private static final Logger LOG = Logger.getLogger(TodConfig.class.getName());

    private static final Map<Class, TodConfig> INSTANCES = new HashMap();

    private static boolean loggingConfigured = false;

    private Class referenceClass;

    @Nullable
    private BuildInfo buildInfo;

    private TodConfig() {
        this.referenceClass = this.getClass();
    }

    /**
     * Creates a configuration using the provided class as reference class for
     * locating resources.
     *
     * @param clazz
     */
    private TodConfig(Class clazz) {
        this.referenceClass = clazz;        
    }

    /**
     *
     * Parses file at {@link #BUILD_PROPERTIES_PATH} of the jar holding the
     * provided class. In case it is not available, returns
     * {@link BuildInfo#of()}.
     *
     */
    public BuildInfo getBuildInfo() {
        if (buildInfo == null) {
            try {
                InputStream stream = referenceClass.getResourceAsStream("/" + BUILD_PROPERTIES_PATH);
                Properties props = new Properties();
                if (stream == null) {
                    throw new TodNotFoundException("Couldn't find " + BUILD_PROPERTIES_PATH + " file in resources of package containing class " + referenceClass.getSimpleName() + "  !!");
                } else {
                    try {
                        props.load(stream);
                    }
                    catch (IOException ex) {
                        throw new TodException("Couldn't load " + BUILD_PROPERTIES_PATH + " file in resources of package containing class " + referenceClass.getSimpleName() + "  !!", ex);
                    }
                }
                buildInfo = BuildInfo.builder()
                        .setBuildJdk(props.getProperty("build-jdk", ""))
                        .setBuiltBy(props.getProperty("built-by", ""))
                        .setCreatedBy(props.getProperty("created-by", ""))
                        .setGitSha(props.getProperty("git-sha", ""))
                        .setScmUrl(props.getProperty("scm-url", ""))
                        .setTimestamp(props.getProperty("timestamp", ""))
                        .setVersion(props.getProperty("version", ""))
                        .build();
            }
            catch (Exception ex) {
                LOG.log(Level.SEVERE, "COULD NOT LOAD BUILD INFORMATION! DEFAULTING TO EMPTY BUILD INFO!", ex);
                buildInfo = BuildInfo.of();
            }
        }
        return buildInfo;
    }

    /**
     * Configure logging by reading properties in
     * {@link #LOG_PROPERTIES_CONF_PATH} file first, and, if not found, in
     * {@link #LOG_PROPERTIES_PATH} file in package resources relative to
     * provided {@code referenceClass}. Successive calls to this method will do
     * nothing and log a message at FINEST level. <br/>
     * <br/>
     * If you're using the library in your application and you have your own
     * logger system, don't call this method and route JUL to your logger API
     * instead.
     *
     * @param referenceClass the class where to look for logging properties
     * resource.
     */
    public static void loadLogConfig(Class referenceClass) {
        if (loggingConfigured) {
            LOG.finest("Trying to reload twice logger properties!");
        } else {
            System.out.print(referenceClass.getSimpleName() + ": searching logging config in " + LOG_PROPERTIES_CONF_PATH + ":");
            InputStream inputStream = null;
            String path;
            String configured = "";

            try {
                inputStream = new FileInputStream(LOG_PROPERTIES_CONF_PATH);
                System.out.println("  found.");
                configured = referenceClass.getSimpleName() + ": logging configured.";
            }
            catch (Exception ex) {
                System.out.println("  not found.");
            }

            try {
                if (inputStream == null) {
                    System.out.println(referenceClass.getSimpleName() + ": searching logging config in default " + LOG_PROPERTIES_PATH + " from resources... ");
                    URL url = referenceClass.getResource("/" + LOG_PROPERTIES_PATH);
                    if (url == null) {
                        System.out.println();
                        throw new IOException("ERROR! COULDN'T FIND ANY LOG CONFIGURATION FILE NAMED " + LOG_PROPERTIES_PATH + " from reference class " + referenceClass.getName());
                    }
                    inputStream = referenceClass.getResourceAsStream("/" + LOG_PROPERTIES_PATH);
                    path = url.toURI().getPath();
                    configured = TodConfig.class.getSimpleName() + ": configured logging with " + path;
                }
                LogManager.getLogManager().readConfiguration(inputStream);

                // IMPORTANT!!!! Due to a JDK bug, we need to create another useless logger to refresh actually ALL loggers (sic) . See https://www.java.net/forum/topic/jdk/java-se-snapshots-project-feedback/jdk-70-doesnt-refresh-handler-specific-logger                
                Logger loggerWorkaround = Logger.getLogger(referenceClass.getName() + ".workaround");

                loggingConfigured = true;

                System.out.println(configured);

            }
            catch (Exception e) {
                System.out.println("ERROR - COULDN'T LOAD LOGGING PROPERTIES!");
                e.printStackTrace();
            }

        }
    }

    public static boolean isLoggingConfigured() {
        return loggingConfigured;
    }

    /**
     * Inits TodConfig by loading the log config using the provided class as
     * reference for locating it. For more info on search paths see
     * {@link #loadLogConfig(java.lang.Class)}.
     *
     * @return the TodConfig instance for method chaining.
     */
    public static TodConfig init(Class clazz) {
        TodConfig ret = TodConfig.of(clazz);
        TodConfig.loadLogConfig(clazz);
        return ret;
    }

    /**
     * Returns an TodConfig using reference class to locate config resources.
     */
    public static synchronized TodConfig of(Class clazz) {
        checkNotNull(clazz);
        if (!INSTANCES.containsKey(clazz)) {
            INSTANCES.put(clazz, new TodConfig(clazz));
        }
        return INSTANCES.get(clazz);
    }

    public Class getReferenceClass() {
        return referenceClass;
    }

}

package com.sakadel.salon.utility;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ParseProperties {
    private static final Logger LOGGER = Logger.getLogger(ParseProperties.class);

    private static ParseProperties instance = null;

    private Properties properties;

    private ParseProperties() {
        LOGGER.info("Initializing MappingProperties class");

        properties = new Properties();
        String propertiesFileName = "mapping.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

        try {

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                LOGGER.error("Mapping properties file not found on the classpath");
            }

        }
        catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static synchronized ParseProperties getInstance() {
        if(instance == null) {
            instance = new ParseProperties();
        }

        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}

package com.sakadel.salon.utility;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that parse properties with sql requests
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class ParseSqlProperties {
    private static final Logger LOGGER = Logger.getLogger(ParseSqlProperties.class);

    private static ParseSqlProperties instance = null;

    private Properties properties;
    private static String propertiesFileName = "sql.properties";

    private ParseSqlProperties() {
        LOGGER.info("Initializing MysqlQueryProperties class");

        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

        try {

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                LOGGER.error("Mysql queries property file not found on the classpath");
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        try {
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            LOGGER.error("Can't close input stream");
        }
    }

    public static synchronized ParseSqlProperties getInstance() {
        if (instance == null) {
            instance = new ParseSqlProperties();
        }

        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}

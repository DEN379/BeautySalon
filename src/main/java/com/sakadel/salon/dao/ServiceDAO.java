package com.sakadel.salon.dao;

import com.sakadel.salon.entity.Role;
import com.sakadel.salon.entity.Service;
import com.sakadel.salon.entity.User;
import com.sakadel.salon.entity.UserBuilder;
import com.sakadel.salon.utility.ParseSqlProperties;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Arrays;

public class ServiceDAO {
    private static final Logger LOGGER = Logger.getLogger(ServiceDAO.class);
    private static ServiceDAO INSTANCE;
    private static Connection connection;


    private static String createQuery;
    private static String findByIdQuery;

    private  ServiceDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/beauty_salon?user=root&password=den379");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Can't connect to the Data Base", e);
        }

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        createQuery = properties.getProperty("createService");
        findByIdQuery = properties.getProperty("findServiceById");
    }

    public static ServiceDAO getInstance(){
        if(INSTANCE == null){
            return new ServiceDAO();
        }
        return INSTANCE;
    }


    public Service createService(Service service){
        LOGGER.info("Creating service");

        try (PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, service.getName());
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation service failed");
            } else {
                LOGGER.info("Successful creation service");
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        service.setId(resultSet.getLong(1));
                    } else {
                        LOGGER.error("Failed to create user, no ID found.");
                    }
                }
            }
            LOGGER.info("Service added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base" + Arrays.toString(e.getStackTrace()));
        }
        return service;
    }

    public Service findService(Long id) {
        LOGGER.info("Getting service by id " + id);
        Service service = null;
        //try(Connection connection = connectionPool.getConnection()) {
        try(PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            service = getService(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return service;
    }


    private Service getService(ResultSet resultSet) {
        Service service = null;

        try {
            if(resultSet.next()) {
                service = new Service(
                        resultSet.getLong("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return service;
    }


}
package com.sakadel.salon.dao;

import com.sakadel.salon.entity.ServiceMaster;
import com.sakadel.salon.service.ServiceMasterService;
import com.sakadel.salon.utility.ParseSqlProperties;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Arrays;

public class ServiceMasterDAO {
    private static final Logger LOGGER = Logger.getLogger(ServiceMasterDAO.class);
    private static ServiceMasterDAO INSTANCE;
    private static Connection connection;


    private static String createQuery;
    private static String findByIdQuery;

    private  ServiceMasterDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/beauty_salon?user=root&password=den379");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Can't connect to the Data Base", e);
        }

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        createQuery = properties.getProperty("createServiceMaster");
        findByIdQuery = properties.getProperty("findServiceMasterById");
    }

    public static ServiceMasterDAO getInstance(){
        if(INSTANCE == null){
            return new ServiceMasterDAO();
        }
        return INSTANCE;
    }

    public ServiceMaster createServiceMaster(ServiceMaster masterService){
        LOGGER.info("Creating service");

        try (PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, masterService.getMaster_id());
            statement.setLong(2, masterService.getService_id());
            statement.setBigDecimal(3, masterService.getPrice());
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation service-master failed");
            } else {
                LOGGER.info("Successful creation service-master");
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        masterService.setId(resultSet.getLong(1));
                    } else {
                        LOGGER.error("Failed to create service-master, no ID found.");
                    }
                }
            }
            LOGGER.info("Service-master added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base" + Arrays.toString(e.getStackTrace()));
        }
        return masterService;
    }

    public ServiceMaster findServiceMaster(Long id) {
        LOGGER.info("Getting service-master by id " + id);
        ServiceMaster serviceMaster = null;
        //try(Connection connection = connectionPool.getConnection()) {
        try(PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            serviceMaster = getService(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return serviceMaster;
    }

    private ServiceMaster getService(ResultSet resultSet) {
        ServiceMaster serviceMaster = null;

        try {
            if(resultSet.next()) {
                serviceMaster = new ServiceMaster(
                        resultSet.getLong("id"),
                        resultSet.getLong("master_id"),
                        resultSet.getLong("service_id"),
                        resultSet.getBigDecimal("price"));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return serviceMaster;
    }
}
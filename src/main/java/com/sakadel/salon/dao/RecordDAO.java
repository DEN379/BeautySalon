package com.sakadel.salon.dao;

import com.sakadel.salon.entity.*;
import com.sakadel.salon.utility.ParseSqlProperties;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordDAO {
    private static final Logger LOGGER = Logger.getLogger(RecordDAO.class);
    private static RecordDAO INSTANCE;
    private static Connection connection;


    private static String createQuery;
    private static String findByIdQuery;
    private static String findAllQuery;
    private static String updateQuery;

    private  RecordDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/beauty_salon?user=root&password=den379");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Can't connect to the Data Base", e);
        }

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        createQuery = properties.getProperty("createRecord");
        findByIdQuery = properties.getProperty("findRecordById");
        findAllQuery = properties.getProperty("findRecords");
        updateQuery = properties.getProperty("updateStatus");
    }

    public static RecordDAO getInstance(){
        if(INSTANCE == null){
            return new RecordDAO();
        }
        return INSTANCE;
    }

    public Record createRecord(Record record){
        LOGGER.info("Creating record");

        try (PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, record.getUser_id());
            statement.setLong(2, record.getMaster_has_service_id());
            statement.setLong(3, record.getStatus_id());
            statement.setLong(4, record.getStatus_id());
            statement.setTime(5, record.getTime());
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation record failed");
            } else {
                LOGGER.info("Successful creation record");
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        record.setId(resultSet.getLong(1));
                    } else {
                        LOGGER.error("Failed to create record, no ID found.");
                    }
                }
            }
            LOGGER.info("Record added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base" + Arrays.toString(e.getStackTrace()));
        }
        return record;
    }

    public List<Record> findAllRecords() {
        LOGGER.info("Getting all records");
        List<Record> listRecords = new ArrayList<>();


        //try(Connection connection = connectionPool.getConnection()) {
        try(PreparedStatement statement = connection.prepareStatement(findAllQuery)){
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Record record = new Record();
                record.setId(result.getLong("id"));
                record.setUser_id(result.getLong("user_id"));
                record.setMaster_has_service_id(result.getLong("master_has_service_id"));
                record.setStatus_id(result.getLong("status_id"));
                record.setTime(result.getTime("time"));

                listRecords.add(record);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listRecords;
    }


    public Boolean updateStatus(Long id, Status status) {
        LOGGER.info("Update status " + id + " to " + status.value());
        //try(Connection connection = connectionPool.getConnection()) {
        try(PreparedStatement statement = connection.prepareStatement(updateQuery)){
            statement.setLong(1, status.ordinal()+1);
            statement.setLong(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

        return true;
    }

    public Record findRecord(Long id) {
        LOGGER.info("Getting service-master by id " + id);
        Record record = null;
        //try(Connection connection = connectionPool.getConnection()) {
        try(PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            record = getRecord(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return record;
    }

    private Record getRecord(ResultSet resultSet) {
        Record record = null;

        try {
            if(resultSet.next()) {
                record = new Record(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("master_has_service_id"),
                        resultSet.getLong("status_id"),
                        resultSet.getTime("time"));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return record;
    }
}

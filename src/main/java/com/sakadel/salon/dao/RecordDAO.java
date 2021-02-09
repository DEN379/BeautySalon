package com.sakadel.salon.dao;

import com.sakadel.salon.entity.Record;
import com.sakadel.salon.entity.ServiceMaster;
import com.sakadel.salon.utility.ParseSqlProperties;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Arrays;

public class RecordDAO {
    private static final Logger LOGGER = Logger.getLogger(RecordDAO.class);
    private static RecordDAO INSTANCE;
    private static Connection connection;


    private static String createQuery;
    private static String findByIdQuery;

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

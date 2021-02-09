package com.sakadel.salon.dao;

import com.sakadel.salon.entity.Master;
import com.sakadel.salon.entity.Role;
import com.sakadel.salon.entity.User;
import com.sakadel.salon.utility.ParseSqlProperties;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Arrays;

public class MasterDAO {
    private static final Logger LOGGER = Logger.getLogger(MasterDAO.class);
    private static MasterDAO INSTANCE;
    private static Connection connection;


    private static String updateQuery;
    private static String findByIdQuery;

    private  MasterDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/beauty_salon?user=root&password=den379");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Can't connect to the Data Base", e);
        }

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        updateQuery = properties.getProperty("createMaster");
        findByIdQuery = properties.getProperty("findMasterById");
    }

    public static MasterDAO getInstance(){
        if(INSTANCE == null){
            return new MasterDAO();
        }
        return INSTANCE;
    }

    public Master setMaster(Long id){
        LOGGER.info("Creating master");
        Master master = new Master();
        try (PreparedStatement statement = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, Role.MASTER.value());
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation master failed");
            } else {
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        master.setId(resultSet.getLong(1));
                        //master.setUser(user);
                        master.setUser_id(id);
                        master.setMark(0);
                    } else {
                        LOGGER.error("Failed to create user, no ID found.");
                    }
                }
                LOGGER.info("Successful creation master");
            }
            LOGGER.info("Master added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base" + Arrays.toString(e.getStackTrace()));
        }
        return master;
    }

    public Master findMasterById(Long id){
        LOGGER.info("Getting master by id " + id);
        Master master = null;
        //try(Connection connection = connectionPool.getConnection()) {
        try(PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            master = getMaster(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return master;
    }

    private Master getMaster(ResultSet resultSet) {
        Master master = null;

        try {
            if(resultSet.next()) {
                master = new Master();
                master.setId(resultSet.getLong("id"));
                master.setUser_id(resultSet.getLong("user_id"));
                master.setMark(resultSet.getDouble("mark"));

            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return master;
    }

}
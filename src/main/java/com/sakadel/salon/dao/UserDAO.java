package com.sakadel.salon.dao;

import java.sql.*;
import java.util.Arrays;

import com.sakadel.salon.entity.Role;
import com.sakadel.salon.entity.User;
import com.sakadel.salon.entity.UserBuilder;
import com.sakadel.salon.utility.ParseSqlProperties;
import org.apache.log4j.Logger;

public class UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);
    private static UserDAO INSTANCE;
    private static Connection connection;

    private static String createQuery;
    private static String updateQuery;
    private static String deleteQuery;
    private static String findByIdQuery;
    private static String findByEmailQuery;
    private static String findByEmailAndPasswordQuery;

    private  UserDAO(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/beauty_salon?user=root&password=den379");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Can't connect to the Data Base", e);
        }

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        createQuery = properties.getProperty("createUser");
        updateQuery = properties.getProperty("updateUserById");
        deleteQuery = properties.getProperty("deleteUserById");
        findByIdQuery = properties.getProperty("findUserById");
        findByEmailQuery = properties.getProperty("findUserByEmail");
        findByEmailAndPasswordQuery = properties.getProperty("findUserByEmailAndPassword");
    }

    public static UserDAO getInstance(){
        if(INSTANCE == null){
            return new UserDAO();
        }
        return INSTANCE;
    }

    public User createUser(User user){
        LOGGER.info("Creating user");
        String sql = "INSERT INTO `user` (first_name, last_name, email, password, role_id) VALUES (?,?,?,?,?);";

        try (PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, Role.CLIENT.ordinal()+1);
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation user failed");
            } else {
                LOGGER.info("Successful creation user");
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        user.setId(resultSet.getLong(1));
                    } else {
                        LOGGER.error("Failed to create user, no ID found.");
                    }
                }
            }
            LOGGER.info("User added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base" + Arrays.toString(e.getStackTrace()));
        }
        return user;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        LOGGER.info("Getting user with email " + email);
        User user = null;
        //String findByEmailAndPasswordQuery = "SELECT * FROM `user` WHERE email = ? AND password = ?";
        //try(Connection connection = connectionPool.getConnection()) {
        try (PreparedStatement statement = connection.prepareStatement(findByEmailAndPasswordQuery)){
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            user = getUser(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }

    public User findUserByEmail(String email) {
        LOGGER.info("Getting user with email " + email);
        User user = null;
        //String findByEmailQuery = "SELECT * FROM `user` WHERE email = ?";
        //try(Connection connection = connectionPool.getConnection()) {
           try(PreparedStatement statement = connection.prepareStatement(findByEmailQuery)){
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            user = getUser(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }
    public User findUserById(Long id) {
        LOGGER.info("Getting user with id " + id);
        User user = null;
        //String findByIdQuery = "SELECT * FROM `user` WHERE id = ?";
        //try(Connection connection = connectionPool.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            user = getUser(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }

    private User getUser(ResultSet resultSet) {
        User user = null;

        try {
            if(resultSet.next()) {
                user = new UserBuilder().setId(resultSet.getLong("id"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setLastName(resultSet.getString("last_name"))
                        .setEmail(resultSet.getString("email"))
                        .setPassword(resultSet.getString("password"))
                        .setUserType(Role.values()[resultSet.getInt("role_id") - 1])
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return user;
    }

}

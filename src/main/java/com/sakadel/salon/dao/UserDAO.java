package com.sakadel.salon.dao;

import java.sql.*;
import com.sakadel.salon.entity.User;
import org.apache.log4j.Logger;

public class UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);
    private static UserDAO INSTANCE;
    private static Connection connection;

    private  UserDAO(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/beauty_salon?user=root&password=den379");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Can't connect to the Data Base", e);
        }
    }

    public static UserDAO getInstance(){
        if(INSTANCE == null){
            return new UserDAO();
        }
        return INSTANCE;
    }

    public void registerUser(User user){
        LOGGER.info("Creating user");
        String sql = "INSERT INTO `user` (first_name, last_name, email, password, role_id) VALUES (?,?,?,?,?);";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getRole());
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation user failed");
            } else {
                LOGGER.info("Successful creation user");
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        user.setId(resultSet.getInt(1));
                    } else {
                        LOGGER.error("Failed to create user, no ID found.");
                    }
                }
            }
            LOGGER.info("User added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base");
        }
    }

}

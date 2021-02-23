package com.sakadel.salon.service;

import com.sakadel.salon.dao.UserDAO;
import com.sakadel.salon.entity.Role;
import com.sakadel.salon.entity.User;
import org.apache.log4j.Logger;

import java.util.List;

public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    private UserDAO userDao;

    public UserService(UserDAO userDao) {
        LOGGER.info("Initializing UserServiceImpl");

        this.userDao = userDao;
    }

    public List<User> findAllUsers(int offset, int limit) {
        LOGGER.info("Find all users");

        return userDao.findAllUsers(offset,limit);

    }

    public boolean checkEmailAvailability(String email) {
        LOGGER.info("Checking availability of email");

        if(email == null) {
            return false;
        }

        User user = userDao.findUserByEmail(email);
        return user == null;
    }

    public boolean registerUser(User user) {
        LOGGER.info("New user registration");

        return user != null && userDao.createUser(user).getId() != null;

    }

    public User getUserByCredentials(String email, String password) {
        LOGGER.info("Getting user by credentials");

        if(email == null || password == null) {
            return null;
        }

        return userDao.findUserByEmailAndPassword(email, password);
    }

    public User findUserByEmail(String email) {
        LOGGER.info("Finding user by email " + email);

        if(email == null) {
            return null;
        }

        return userDao.findUserByEmail(email);
    }

    public User findUserById(Long id) {
        LOGGER.info("Finding user by id " + id);

        if(id == null) {
            return null;
        }

        return userDao.findUserById(id);
    }

    public boolean updateRole(Long id, Role role){
        LOGGER.info("Updating role "+id+" to "+ role.value());
        return userDao.updateRole(id, role);
    }

    public int getCountUsers(){
        LOGGER.info("Getting count of users");

        return userDao.getCountUsers();
    }
//    public Page<User> getPageByUserType(Integer page, Integer size, Role role) {
//        LOGGER.info("Getting page number " + page + ", of size " + size + ", for user type " + role.name());
//
//        if(page == null || size == null || page < 1 || size < 1) {
//            return null;
//        }
//
//        List<User> items =  userDao.findPageByUserType(userType, (page - 1) * size, size);
//        return new Page<>(items, page, size);
//    }


}

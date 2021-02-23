package com.sakadel.salon.dao.User;

import com.sakadel.salon.model.Role;
import com.sakadel.salon.model.User;

import java.util.List;

public interface IUserDAO {
    User createUser(User user);

    List<User> findAllUsers(int offset, int limit);

    User findUserByEmailAndPassword(String email, String password);

    Boolean updateRole(Long id, Role role);

    User findUserByEmail(String email);

    User findUserById(Long id);

    int getCountUsers();

}

package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.UserDAO;
import com.sakadel.salon.model.User;
import com.sakadel.salon.service.UserService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Class that get user info
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class UsersItemPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UsersItemPageCommand.class);

    private UserService user;
    private UserDAO userDAO;

    private static String page;
    private static String pageRe;

    public UsersItemPageCommand() {
        LOGGER.info("Initializing UsersItemPageCommand");

        userDAO = UserDAO.getInstance();
        user = new UserService(userDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("usersItemPage");
        pageRe = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        if (request.getParameter("id") == null) return pageRe;
        long id = Integer.parseInt(request.getParameter("id"));
        User usr = user.findUserById(id);
        if (usr == null || usr.getFirstName() == null) return pageRe;
        request.setAttribute("user", usr);

        return page;
    }
}
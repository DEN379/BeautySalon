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
import java.util.List;


/**
 * Class that get all users with pagination
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class UsersPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UsersPageCommand.class);
    private UserService user;
    private UserDAO userDAO;

    private static String page;


    public UsersPageCommand() {
        LOGGER.info("Initializing UsersPageCommand");

        userDAO = UserDAO.getInstance();
        user = new UserService(userDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing UsersPageCommand");

        int pageNumb = 1;
        if (request.getParameter("page") != null)
            pageNumb = Integer.parseInt(request.getParameter("page"));
        int count = user.getCountUsers();
        LOGGER.info("Count users => " + count);
        int limit = 2;
        int numberPages = (int) Math.ceil((float) count / limit);

        List<User> list = user.findAllUsers((pageNumb - 1) * limit, limit);

        request.setAttribute("users", list);

        StringBuilder sb = new StringBuilder();

        sb.append("<ul type=\"none\" class=\"pagination\">\n");
        for (int i = 0; i < numberPages; i++) {

            sb.append(
                    "<li class=\"page-item\"><a class=\"page-link\" href=\"").append(request.getContextPath())
                    .append("/admin/users?page=").append(i + 1).append("\" title=\"На страницу номер ").append(i + 1).append("\">")
                    .append(i + 1).append("</a></li>\n");
        }
        sb.append("   </ul>");

        request.setAttribute("pages", sb.toString());

        return page;
    }
}

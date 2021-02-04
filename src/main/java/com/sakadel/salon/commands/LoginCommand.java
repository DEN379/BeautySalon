package com.sakadel.salon.commands;

import com.sakadel.salon.dao.UserDAO;
import com.sakadel.salon.entity.User;
import com.sakadel.salon.service.UserService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ServletCommand{

    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);
    private static UserDAO dao;
    private static UserService userService;

    private static String loginPage;
    private static String mainPage;

    public LoginCommand() {
        userService = new UserService(UserDAO.getInstance());
        dao = UserDAO.getInstance();
        ParsePathProperties properties = ParsePathProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing command");

        String resultPage = loginPage;

        if (request.getParameter("email") != null && request.getParameter("password") != null) {
            User user = userService.getUserByCredentials
            //User user = dao.findUserByEmailAndPassword
                    (request.getParameter("email"),
                    request.getParameter("password"));

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("email", user.getEmail());
                session.setAttribute("username", user.getFirstName() + " " + user.getLastName());
                session.setAttribute("authenticated", true);
                session.setAttribute("role", user.getRole().name());

//                request.setAttribute("categories", categoryService.findAll());
//                request.setAttribute("latestMagazines", magazineService.findLatestAdded(6));

                resultPage = mainPage;
            }
            else {
                request.setAttribute("loginSuccess", false);
            }
        }

        return resultPage;
    }
}

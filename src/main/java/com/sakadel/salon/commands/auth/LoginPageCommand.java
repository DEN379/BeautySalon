package com.sakadel.salon.commands.auth;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Class that get login page
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class LoginPageCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(LoginPageCommand.class);

    private static String loginPage;
    private static String mainPage;

    public LoginPageCommand() {
        LOGGER.info("Initializing LoginPageCommand");

        ParsePathProperties properties = ParsePathProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing LoginPageCommand");

        String resultPage = loginPage;

        if (request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            resultPage = mainPage;
        } else if (request.getParameter("email") == null && request.getParameter("password") == null) {
            LOGGER.info("Returning login page");
            return resultPage;
        }

        return resultPage;
    }
}

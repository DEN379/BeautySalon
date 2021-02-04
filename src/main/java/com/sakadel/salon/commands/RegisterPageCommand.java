package com.sakadel.salon.commands;

import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(RegisterPageCommand.class);

    private static String registerPage;
    private static String mainPage;

    public RegisterPageCommand(){
        LOGGER.info("Initializing GetRegisterPageCommand");

        ParsePathProperties properties = ParsePathProperties.getInstance();
        registerPage = properties.getProperty("registerPage");
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing command");

        String resultPage = registerPage;

        if(request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            resultPage = mainPage;
        }
        else if(request.getParameter("fname") == null && request.getParameter("lname") == null &&
                request.getParameter("email") == null && request.getParameter("password") == null &&
                request.getParameter("address") == null) {
            LOGGER.info("Returning registration page");
            return resultPage;
        }

        return resultPage;
    }
}

package com.sakadel.salon.commands;

import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPageCommand implements ServletCommand{
        private static final Logger LOGGER = Logger.getLogger(LoginPageCommand.class);

        private static String loginPage;
        private static String mainPage;

        public LoginPageCommand(){
            LOGGER.info("Initializing GetLoginPageCommand");

            ParsePathProperties properties = ParsePathProperties.getInstance();
            loginPage = properties.getProperty("loginPage");
            mainPage = properties.getProperty("mainPage");
        }

        public String execute(HttpServletRequest request, HttpServletResponse response) {
            LOGGER.info("Executing command");

            String resultPage = loginPage;

            if(request.getSession().getAttribute("authenticated") != null &&
                    request.getSession().getAttribute("authenticated").equals(true)) {
                resultPage = mainPage;
            }
            else if(request.getParameter("email") == null && request.getParameter("password") == null) {
                LOGGER.info("Returning login page");
                return resultPage;
            }

            return resultPage;
        }
    }

package com.sakadel.salon.commands;

import com.sakadel.salon.dao.MasterDAO;
import com.sakadel.salon.dao.ServiceDAO;
import com.sakadel.salon.dao.UserDAO;
import com.sakadel.salon.entity.Role;
import com.sakadel.salon.entity.User;
import com.sakadel.salon.entity.UserBuilder;
import com.sakadel.salon.service.MasterService;
import com.sakadel.salon.service.ServiceService;
import com.sakadel.salon.service.UserService;
import com.sakadel.salon.utility.ParsePathProperties;
import com.sakadel.salon.utility.ParseSqlProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);
    private static UserDAO dao;
    private static UserService userService;

    private static String registerPage;
    private static String mainPage;

    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;

    public RegisterCommand() {
        userService = new UserService(UserDAO.getInstance());
        dao = UserDAO.getInstance();
        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        ParsePathProperties properties = ParsePathProperties.getInstance();
        registerPage = properties.getProperty("registerPage");
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing command");

        String resultPage = registerPage;

        if(request.getParameter("fname") != null && request.getParameter("lname") != null &&
                request.getParameter("email") != null && request.getParameter("password") != null
                 && userService.checkEmailAvailability(request.getParameter("email"))
        ){

            LOGGER.info("New user registration");

            User user = new UserBuilder().setFirstName(request.getParameter("fname"))
                    .setLastName(request.getParameter("lname"))
                    .setEmail(request.getParameter("email"))
                    .setPassword(request.getParameter("password"))
                    .setUserType(Role.CLIENT)
                    .build();

            dao.createUser(user);
            if(userService.registerUser(user)) {
                request.setAttribute("services", service.findAll());
                request.setAttribute("masters", master.findAllWithName());

                resultPage = mainPage;
            }
        }

        return resultPage;
    }
}

package com.sakadel.salon.commands.auth;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.MasterDAO;
import com.sakadel.salon.dao.ServiceDAO;
import com.sakadel.salon.service.MasterService;
import com.sakadel.salon.service.ServiceService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that logout a user
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class LogoutCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;

    private static String mainPage;

    public LogoutCommand() {
        LOGGER.info("Initializing LogoutCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing LogoutCommand");
        LOGGER.info("Logging out user " + request.getSession().getAttribute("email"));

        request.getSession().invalidate();

        request.setAttribute("services", service.findAll());
        request.setAttribute("masters", master.findAllWithName());

        return mainPage;
    }
}

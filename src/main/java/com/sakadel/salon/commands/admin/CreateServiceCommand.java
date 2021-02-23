package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.ServiceDAO;
import com.sakadel.salon.model.Service;
import com.sakadel.salon.service.ServiceService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that add new service
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class CreateServiceCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(CreateServiceCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;

    private static String page;
    private static String createServicePage;


    public CreateServiceCommand() {
        LOGGER.info("Initializing CreateServiceCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("mainPage");
        createServicePage = properties.getProperty("createServicePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing CreateServiceCommand");

        if (request.getParameter("name") != null && request.getParameter("desc") != null) {
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");

            LOGGER.info("Parameters name => " + name + ", desc => " + desc);

            if (service.addService(new Service(name, desc))) {
                LOGGER.info("Creating service was successful");
                return page;
            }
        }
        LOGGER.info("Creating service was unsuccessful");
        return createServicePage;

    }
}

package com.sakadel.salon.commands;

import com.sakadel.salon.dao.Master.MasterDAO;
import com.sakadel.salon.dao.Service.ServiceDAO;
import com.sakadel.salon.service.MasterService;
import com.sakadel.salon.service.ServiceService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that get all services
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class OrderPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(OrderPageCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private static String page;

    public OrderPageCommand() {
        LOGGER.info("Initializing OrderPageCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("orderPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing OrderPageCommand");

        request.setAttribute("services", service.findAll());
        return page;
    }
}

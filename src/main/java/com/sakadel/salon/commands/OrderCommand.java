package com.sakadel.salon.commands;

import com.sakadel.salon.dao.MasterDAO;
import com.sakadel.salon.dao.ServiceDAO;
import com.sakadel.salon.entity.Record;
import com.sakadel.salon.entity.User;
import com.sakadel.salon.service.MasterService;
import com.sakadel.salon.service.ServiceService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(OrderCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private static String page;
    private static String mainPage;

    public OrderCommand(){
        LOGGER.info("Initializing GetMainPageCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("orderPage");
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing command");

        String resultPage = mainPage;

//        if (request.getParameter("master-id") != null && request.getParameter("service-id") != null) {
//            Record record = new Record()
//        }
        return resultPage;
    }
}

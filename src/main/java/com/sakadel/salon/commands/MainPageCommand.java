package com.sakadel.salon.commands;

import com.sakadel.salon.dao.MasterDAO;
import com.sakadel.salon.dao.ServiceDAO;
import com.sakadel.salon.entity.Service;
import com.sakadel.salon.service.MasterService;
import com.sakadel.salon.service.ServiceService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(MainPageCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private static String page;

    public MainPageCommand(){
        LOGGER.info("Initializing GetMainPageCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing command");

        request.setAttribute("services", service.findAll());
        request.setAttribute("masters", master.findAllWithName());

        if(request.getParameter("locale") != null) {
            String locale = request.getParameter("locale");
            switch (locale) {
                case "en":
                    request.getSession().setAttribute("locale", "en");
                    break;
                case "ua":
                    request.getSession().setAttribute("locale", "ua");
                    break;
            }
        }

        return page;
    }
}

package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.Service;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateServiceCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(CreateServiceCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService record;
    private RecordDAO recordDAO;
    private UserService user;
    private UserDAO userDAO;

    private static String page;
    private static String createServicePage;


    public CreateServiceCommand(){
        LOGGER.info("Initializing CreateServiceCommand");

        userDAO = UserDAO.getInstance();
        user = new UserService(userDAO);
        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);
        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("mainPage");
        createServicePage = properties.getProperty("createServicePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        if(request.getParameter("name") != null && request.getParameter("desc") != null) {
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");

            service.addService(new Service(name, desc));
            return page;
        }
        else return createServicePage;

    }
}

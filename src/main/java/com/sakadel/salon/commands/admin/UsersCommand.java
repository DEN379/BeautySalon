package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.Master;
import com.sakadel.salon.entity.ServiceMaster;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class UsersCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UsersCommand.class);
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


    public UsersCommand() {
        LOGGER.info("Initializing UsersCommand");

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
        page = properties.getProperty("usersItemPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        long id = Integer.parseInt(request.getParameter("id"));
        int price = Integer.parseInt(request.getParameter("price"));
        long service_id = Integer.parseInt(request.getParameter("service-id"));

        Master mas = master.findMasterByUserId(id);
        ServiceMaster sm = new ServiceMaster();
        sm.setMaster_id(mas.getId());
        sm.setService_id(service_id);
        sm.setPrice(BigDecimal.valueOf(price));

        serviceMaster.addServiceMaster(sm);

        return page;
    }
}

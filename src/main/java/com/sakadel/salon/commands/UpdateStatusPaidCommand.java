package com.sakadel.salon.commands;

import com.sakadel.salon.commands.master.UpdateStatusCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.Status;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateStatusPaidCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UpdateStatusPaidCommand.class);
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


    public UpdateStatusPaidCommand(){
        LOGGER.info("Initializing UpdateStatusPaidCommand");

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
        page = properties.getProperty("userRecordsPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        if(request.getParameter("id") == null) return page;
        long id = Integer.parseInt(request.getParameter("id"));
        record.updateStatus(id, Status.PAID);
        return page;
    }
}

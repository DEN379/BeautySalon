package com.sakadel.salon.commands;

import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.ServiceMaster;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommentCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(CommentCommand.class);
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


    public CommentCommand(){
        LOGGER.info("Initializing CommentCommand");

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

        long id = Integer.parseInt(request.getParameter("id"));
        long master_id = Integer.parseInt(request.getParameter("master"));
        int mark = Integer.parseInt(request.getParameter("mark"));

        record.updateMark(id, mark);

        List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(master_id);

        List<Long> sm = new ArrayList<>();

        for(ServiceMaster s: list){
            sm.add(s.getId());
        }

        float avgMark = record.getAvgRecords(sm);

        LOGGER.info("avg mark "+avgMark);
        master.updateMasterRate(master_id, avgMark);




        return page;
    }
}

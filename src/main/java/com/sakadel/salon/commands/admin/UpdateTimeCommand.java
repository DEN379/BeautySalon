package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.Record;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateTimeCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UpdateTimeCommand.class);
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


    public UpdateTimeCommand(){
        LOGGER.info("Initializing UpdateTimeCommand");

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
        page = properties.getProperty("recordsPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        long record_id = Integer.parseInt(request.getParameter("id"));
        String hour = request.getParameter("time");
        LOGGER.info("Hour " +hour);

        String time;
        if(hour.length() == 1) time = "0" + hour + ":00";
        else time = hour + ":00";
        LOGGER.info("time " +time);
        Record rec = record.findRecord(record_id);

        String date = rec.getTime().substring(0, 11) + time;
        LOGGER.info("date " +date);

        record.updateTime(record_id, date);


        return page;
    }
}

package com.sakadel.salon.commands.master;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.commands.admin.UpdateTimeCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.*;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TimeTablePageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(TimeTablePageCommand.class);
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


    public TimeTablePageCommand(){
        LOGGER.info("Initializing TimeTablePageCommand");

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
        page = properties.getProperty("timeTablePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        HttpSession session = request.getSession();
        Master mas = master.findMasterByUserId((long)session.getAttribute("id"));


        List<ServiceMaster> listSm = serviceMaster.findServiceMasterByMasterId(mas.getId());

        String time = request.getParameter("date");
        if(time == null){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            time = dtf.format(now);
        }



        List<Record> records = new ArrayList<>();
        for(ServiceMaster sm : listSm){
            records.addAll(record.findAllRecordsTime(sm.getId(), time, true));
            sm.setService(service.findServiceById(sm.getService_id()));
        }

        for(Record r: records){
            for(ServiceMaster sm : listSm){
                if(r.getMaster_has_service_id() == sm.getId()) r.setServiceMaster(sm);
                LOGGER.info("....1 "+r.getMaster_has_service_id());
                LOGGER.info("....2 "+sm.getId());

            }

        }


        for(Record r: records){
            r.setStatus(Status.values()[Integer.parseInt(r.getStatus_id()+"") - 1]);
        }

        for(Record r: records){
            r.setUser(user.findUserById(r.getUser_id()));
        }

        List<Record> recWithEmptySpace = MasterTime.getRecordsWithEmptySpace(records);


//        for(Record r: recWithEmptySpace){
//            LOGGER.info(r.);
//        }



        request.setAttribute("records", recWithEmptySpace);

        return page;
    }
}

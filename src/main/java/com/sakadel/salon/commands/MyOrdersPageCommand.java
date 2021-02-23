package com.sakadel.salon.commands;

import com.sakadel.salon.commands.master.OrderByNameCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.*;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyOrdersPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(MyOrdersPageCommand.class);
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
    private static String pageRe;

    public MyOrdersPageCommand(){
        LOGGER.info("Initializing MyOrdersPageCommand");

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
        pageRe = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        if(request.getSession().getAttribute("id") == null) return page;
        long user_id = (long) request.getSession().getAttribute("id");
        List<Record> records = record.findAllRecordsByUserId(user_id);
        //List<User> users = new ArrayList<>();
        List<ServiceMaster> mastersService = new ArrayList<>();
        List<Status> statuses = new ArrayList<>();
        List<Master> masters = new ArrayList<>();
        List<Service> services = new ArrayList<>();
        List<User> mastersUser = new ArrayList<>();

        for(int i = 0; i < records.size(); i++){
            //users.add(user.findUserById(records.get(i).getUser_id()));
            mastersService.add(serviceMaster.findServiceMasterById(records.get(i).getMaster_has_service_id()));
            long id = records.get(i).getStatus_id() - 1;
            statuses.add(Status.values()[(int)id]);
            masters.add(master.findMasterById(mastersService.get(i).getMaster_id()));
            services.add(service.findServiceById(mastersService.get(i).getService_id()));
            mastersUser.add(user.findUserById(masters.get(i).getUser_id()));
           // records.get(i).setUser(users.get(i));
            records.get(i).setUserMaster(mastersUser.get(i));
            records.get(i).setService(services.get(i));
            records.get(i).setServiceMaster(mastersService.get(i));
            records.get(i).setStatus(statuses.get(i));
        }

        request.setAttribute("records", records);

        return page;
    }
}

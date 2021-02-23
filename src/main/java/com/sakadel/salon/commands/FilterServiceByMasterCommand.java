package com.sakadel.salon.commands;

import com.sakadel.salon.commands.master.UpdateStatusCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.Service;
import com.sakadel.salon.entity.ServiceMaster;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FilterServiceByMasterCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(FilterServiceByMasterCommand.class);
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


    public FilterServiceByMasterCommand(){
        LOGGER.info("Initializing FilterServiceByMasterCommand");

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

        if(request.getParameter("id") == null) return page;
        long master_id = Integer.parseInt(request.getParameter("id"));

        List<Service> serviceList = new ArrayList<>();

        if(master_id != 0) {
            List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(master_id);
            for (ServiceMaster sm : list) {
                serviceList.add(service.findServiceById(sm.getService_id()));
            }
        }
        else {
           serviceList = service.findAll();
        }
        request.setAttribute("services", serviceList);



        LOGGER.info("after finding");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();

        LOGGER.info("after writer");
        for(Service s : serviceList){
            sb.append("<div class=\"service\">\n" +
                    "            <h1>").append(s.getName()).append("</h1>\n" +
                    "            <p>").append(s.getDescription()).append("</p>\n" +
                    "        </div>");
        }


        LOGGER.info("after append"+sb.toString());
        out.print(sb.toString());
        LOGGER.info("after print");

        return null;

    }
}

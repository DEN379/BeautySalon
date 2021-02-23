package com.sakadel.salon.commands;

import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.Master;
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

public class FilterMasterByServiceCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(FilterMasterByServiceCommand.class);
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


    public FilterMasterByServiceCommand(){
        LOGGER.info("Initializing FilterMasterByServiceCommand");

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
        long service_id = Integer.parseInt(request.getParameter("id"));

        List<Master> masterList = new ArrayList<>();

        if(service_id != 0) {
            List<ServiceMaster> list = serviceMaster.findMastersByService(service_id);
            for (ServiceMaster sm : list) {
                masterList.add(master.findMasterWithNameById(sm.getMaster_id()));
            }
        }
        else {
            masterList = master.findAllWithName();
        }
        request.setAttribute("masters", masterList);



        LOGGER.info("after finding");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();

        LOGGER.info("after writer");
        for(Master m : masterList){
            sb.append("<div class=\"master\">\n" +
                    "            <h4>").append(m.getUser().getFirstName()).append(" ")
                    .append(m.getUser().getLastName()).append("</h4>\n" +
                    "            <p>").append(m.getMark()).append("</p>\n" +
                    "        </div>");
        }


        LOGGER.info("after append"+sb.toString());
        out.print(sb.toString());
        LOGGER.info("after print");

        return null;

    }
}

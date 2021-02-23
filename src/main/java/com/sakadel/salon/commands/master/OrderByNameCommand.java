package com.sakadel.salon.commands.master;

import com.sakadel.salon.commands.FilterMasterByServiceCommand;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OrderByNameCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(OrderByNameCommand.class);
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


    public OrderByNameCommand(){
        LOGGER.info("Initializing OrderByNameCommand");

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

        String s1 = request.getParameter("id");
        String s2 = request.getParameter("way");
        List<Master> masterList;

        if(s1 != null && s2 != null) {
            int resultColumn = Integer.parseInt(s1);
            int resultWay = Integer.parseInt(s2);
            boolean isRate = false;
            boolean isDescending = false;
            if (resultWay == 1) isDescending = true;
            if (resultColumn != 0) {
                if (resultColumn == 2) isRate = true;
                masterList = master.findAllWithNameOrderBy(isRate, isDescending);
            }
            else {
                masterList = master.findAllWithName();
            }
        }
        else {
            masterList = master.findAllWithName();
        }



        LOGGER.info("after finding");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();

        LOGGER.info("after writer");
        for(Master m : masterList){
            sb.append("<div class=\"master\">\n" +
                    "            <h4>").append(m.getUser().getFirstName()+" ").append(m.getUser().getLastName()).append("</h4>\n" +
                    "            <p>").append(m.getMark()).append("</p>\n" +
                    "        </div>");
        }


        LOGGER.info("after append"+sb.toString());
        out.print(sb.toString());
        LOGGER.info("after print");

        return null;

    }
}

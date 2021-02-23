package com.sakadel.salon.commands;

import com.sakadel.salon.commands.admin.ItemPageCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.*;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetMastersCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(GetMastersCommand.class);
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

    public GetMastersCommand(){
        LOGGER.info("Initializing GetMastersCommand");

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
        page = properties.getProperty("recordItemPage");
        pageRe = properties.getProperty("recordsPage");

    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        if(request.getParameter("id") == null) return pageRe;
        long id = Integer.parseInt(request.getParameter("id"));

        List<ServiceMaster> list = serviceMaster.findMastersByService(id);

        for(ServiceMaster sm : list){
            Master masterUser = master.findMasterWithNameById(sm.getMaster_id());
            LOGGER.info(masterUser.getUser().getFirstName()+masterUser.getUser().getLastName());
            LOGGER.info(masterUser.getMark());
            LOGGER.info(sm.getMaster_id());
            LOGGER.info(sm.getPrice());
            sm.setMaster(masterUser);
        }

        LOGGER.info("after finding");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();

        LOGGER.info("after writer");
        sb.append("<select name=\"master-id\" form=\"order\" required>\n" +
                "    <option selected disabled>Select master for service</option>");
        for(ServiceMaster sm : list){
            sb.append("<option value=\"").append(sm.getMaster_id()).append("\">")
                    .append(sm.getMaster().getUser().getFirstName()).append(" ")
                    .append(sm.getMaster().getUser().getLastName()).append(", ")
                    .append(sm.getPrice()).append("</option>");
            LOGGER.info("append "+sb.toString());
        }
        sb.append("</select>");

        LOGGER.info("after append"+sb.toString());
        out.print(sb.toString());
LOGGER.info("after print");

        return null;
    }
}

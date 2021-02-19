package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
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

public class GetServicesCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(GetServicesCommand.class);
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


    public GetServicesCommand() {
        LOGGER.info("Initializing GetServicesCommand");

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

        long user_id = Integer.parseInt(request.getParameter("id"));
        Master mas = master.findMasterByUserId(user_id);

        List<ServiceMaster> sm = serviceMaster.findServiceMasterByMasterId(mas.getId());

        List<Service> list = service.findAll();

        List<Service> result = new ArrayList<>();
        for (ServiceMaster a : sm) {
            LOGGER.info("servisMaster "+a.getService_id());
            for (Service b : list) {
                if (a.getService_id() == b.getId()) {
                    LOGGER.info("equal service "+b.getId());
                    result.add(b);
                }

            }

        }

        list.removeAll(result);



        LOGGER.info("after finding");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();

        LOGGER.info("after writer");
        sb.append("<select name=\"service-id\" form=\"services\" required>\n" +
                "    <option selected disabled>Select service</option>");
        for(Service ser : list){
            sb.append("<option value=\"").append(ser.getId()).append("\">")
                    .append(ser.getName()).append("</option>");
            LOGGER.info("append "+sb.toString());
        }
        sb.append("</select>");

        LOGGER.info("after append"+sb.toString());
        out.print(sb.toString());
        LOGGER.info("after print");

        return null;
    }
}


package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.MasterDAO;
import com.sakadel.salon.dao.ServiceDAO;
import com.sakadel.salon.dao.ServiceMasterDAO;
import com.sakadel.salon.model.Master;
import com.sakadel.salon.model.Service;
import com.sakadel.salon.model.ServiceMaster;
import com.sakadel.salon.service.MasterService;
import com.sakadel.salon.service.ServiceMasterService;
import com.sakadel.salon.service.ServiceService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that print services in select tag
 *
 * @author Denys Sakadel
 * @version 1.0
 */


public class GetServicesCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(GetServicesCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private static String page;


    public GetServicesCommand() {
        LOGGER.info("Initializing GetServicesCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing GetServicesCommand");

        List<Service> list = new ArrayList<>();
        if (request.getParameter("id") != null) {

            long user_id = Integer.parseInt(request.getParameter("id"));
            Master mas = master.findMasterByUserId(user_id);

            List<ServiceMaster> sm = new ArrayList<>();
            if (mas != null && mas.getId() != null) {
                sm = serviceMaster.findServiceMasterByMasterId(mas.getId());
            }
            list = service.findAll();

            List<Service> result = new ArrayList<>();
            for (ServiceMaster a : sm) {
                for (Service b : list) {
                    if (a.getService_id() == b.getId()) {
                        result.add(b);
                    }
                }
            }
            list.removeAll(result);

        }

        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

        sb.append("<select name=\"service-id\" form=\"services\" required>\n" +
                "    <option selected disabled>Select service</option>");
        for (Service ser : list) {
            sb.append("<option value=\"").append(ser.getId()).append("\">").append(ser.getName())
                    //.append(request.getParameter("locale").equals("en") ? ser.getName() : ser.getNameUkr())
                    .append("</option>");
        }
        sb.append("</select>");
        out.print(sb.toString());

        return null;
    }
}


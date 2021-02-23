package com.sakadel.salon.commands;

import com.sakadel.salon.dao.ServiceDAO;
import com.sakadel.salon.dao.ServiceMasterDAO;
import com.sakadel.salon.model.Service;
import com.sakadel.salon.model.ServiceMaster;
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
 * Class that filter service by master
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class FilterServiceByMasterCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(FilterServiceByMasterCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;

    private static String page;


    public FilterServiceByMasterCommand() {
        LOGGER.info("Initializing FilterServiceByMasterCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("timeTablePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing FilterServiceByMasterCommand");

        if (request.getParameter("id") == null) {
            LOGGER.info("Parameter id is null");
            return page;
        }
        long master_id = Integer.parseInt(request.getParameter("id"));

        List<Service> serviceList = new ArrayList<>();

        if (master_id != 0) {
            List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(master_id);
            for (ServiceMaster sm : list) {
                serviceList.add(service.findServiceById(sm.getService_id()));
            }
        } else {
            serviceList = service.findAll();
        }
        request.setAttribute("services", serviceList);


        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

        for (Service s : serviceList) {
            sb.append("<div class=\"service\">\n" +
                    "            <h1>").append(s.getName()).append("</h1>\n" +
                    "            <p>").append(s.getDescription()).append("</p>\n" +
                    "        </div>");
        }

        out.print(sb.toString());

        return null;

    }
}

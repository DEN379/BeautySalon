package com.sakadel.salon.commands;

import com.sakadel.salon.dao.MasterDAO;
import com.sakadel.salon.dao.ServiceMasterDAO;
import com.sakadel.salon.model.Master;
import com.sakadel.salon.model.ServiceMaster;
import com.sakadel.salon.service.MasterService;
import com.sakadel.salon.service.ServiceMasterService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that filter master by service
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class FilterMasterByServiceCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(FilterMasterByServiceCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;

    private static String page;


    public FilterMasterByServiceCommand() {
        LOGGER.info("Initializing FilterMasterByServiceCommand");

        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("timeTablePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing FilterMasterByServiceCommand");

        if (request.getParameter("id") == null) {
            LOGGER.info("Parameter id is null");
            return page;
        }
        long service_id = Integer.parseInt(request.getParameter("id"));

        List<Master> masterList = new ArrayList<>();

        if (service_id != 0) {
            List<ServiceMaster> list = serviceMaster.findMastersByService(service_id);
            for (ServiceMaster sm : list) {
                masterList.add(master.findMasterWithNameById(sm.getMaster_id()));
            }
        } else {
            masterList = master.findAllWithName();
        }
        request.setAttribute("masters", masterList);


        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();

        for (Master m : masterList) {
            sb.append("<div class=\"master\">\n" +
                    "            <h4>").append(m.getUser().getFirstName()).append(" ")
                    .append(m.getUser().getLastName()).append("</h4>\n" +
                    "            <p>").append(m.getMark()).append("</p>\n" +
                    "        </div>");
        }
        out.print(sb.toString());

        return null;

    }
}

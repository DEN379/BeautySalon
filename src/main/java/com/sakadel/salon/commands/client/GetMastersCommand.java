package com.sakadel.salon.commands.client;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.Master.MasterDAO;
import com.sakadel.salon.dao.ServiceMaster.ServiceMasterDAO;
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
import java.util.List;


/**
 * Class that get masters in select
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class GetMastersCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(GetMastersCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;

    private static String page;
    private static String pageRe;

    public GetMastersCommand() {
        LOGGER.info("Initializing GetMastersCommand");

        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("recordItemPage");
        pageRe = properties.getProperty("recordsPage");

    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing GetMastersCommand");

        if (request.getParameter("id") == null) {
            LOGGER.info("Parameter id is null");
            return page;
        }
        long id = Integer.parseInt(request.getParameter("id"));

        List<ServiceMaster> list = serviceMaster.findMastersByService(id);

        for (ServiceMaster sm : list) {
            Master masterUser = master.findMasterWithNameById(sm.getMaster_id());
            LOGGER.info(masterUser.getUser().getFirstName() + masterUser.getUser().getLastName());
            LOGGER.info(masterUser.getMark());
            LOGGER.info(sm.getMaster_id());
            LOGGER.info(sm.getPrice());
            sm.setMaster(masterUser);
        }

        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

        sb.append("<select name=\"master-id\" form=\"order\" required>\n" +
                "    <option selected disabled>Select master for service</option>");
        for (ServiceMaster sm : list) {
            sb.append("<option value=\"").append(sm.getMaster_id()).append("\">")
                    .append(sm.getMaster().getUser().getFirstName()).append(" ")
                    .append(sm.getMaster().getUser().getLastName()).append(", ")
                    .append(sm.getPrice()).append("</option>");
        }
        sb.append("</select>");

        out.print(sb.toString());

        return null;
    }
}

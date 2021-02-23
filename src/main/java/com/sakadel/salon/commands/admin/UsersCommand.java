package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
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
import java.math.BigDecimal;


/**
 * Class that add new service to master
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class UsersCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UsersCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;

    private static String page;
    private static String pageRe;

    public UsersCommand() {
        LOGGER.info("Initializing UsersCommand");

        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("usersItemPage");
        pageRe = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing UsersCommand");

        if (request.getParameter("id") == null || request.getParameter("price") == null
                || request.getParameter("service-id") == null) {
            LOGGER.info("Parameters are null");
            return pageRe;
        }
        long id = Integer.parseInt(request.getParameter("id"));
        int price = Integer.parseInt(request.getParameter("price"));
        long service_id = Integer.parseInt(request.getParameter("service-id"));

        Master mas = master.findMasterByUserId(id);
        ServiceMaster sm = new ServiceMaster();
        sm.setMaster_id(mas.getId());
        sm.setService_id(service_id);
        sm.setPrice(BigDecimal.valueOf(price));

        if (serviceMaster.addServiceMaster(sm)) {
            LOGGER.info("Added service to master successfully");
        }

        return page;
    }
}

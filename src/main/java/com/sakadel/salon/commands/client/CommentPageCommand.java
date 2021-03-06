package com.sakadel.salon.commands.client;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.Master.MasterDAO;
import com.sakadel.salon.dao.Record.RecordDAO;
import com.sakadel.salon.dao.Service.ServiceDAO;
import com.sakadel.salon.dao.ServiceMaster.ServiceMasterDAO;
import com.sakadel.salon.dao.User.UserDAO;
import com.sakadel.salon.model.*;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Class that get comment page
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class CommentPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(CommentPageCommand.class);
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

    public CommentPageCommand() {
        LOGGER.info("Initializing CommentPageCommand");

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
        page = properties.getProperty("orderCommentPage");
        pageRe = properties.getProperty("userRecordsPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing CommentPageCommand");

        if (request.getParameter("id") == null) return pageRe;
        long id = Integer.parseInt(request.getParameter("id"));
        Record rec = record.findRecord(id);

        User userName = user.findUserById(rec.getUser_id());
        ServiceMaster sm = serviceMaster.findServiceMasterById(rec.getMaster_has_service_id());
        long status_id = rec.getStatus_id() - 1;
        Status status = Status.values()[(int) status_id];
        Master mas = master.findMasterById(sm.getMaster_id());
        Service ser = service.findServiceById(sm.getService_id());
        User userMaster = user.findUserById(mas.getUser_id());
        rec.setUser(userName);
        rec.setUserMaster(userMaster);
        rec.setService(ser);
        rec.setServiceMaster(sm);
        rec.setStatus(status);

        request.setAttribute("record", rec);

        return page;
    }
}

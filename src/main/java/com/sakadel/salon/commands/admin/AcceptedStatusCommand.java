package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.RecordDAO;
import com.sakadel.salon.model.Status;
import com.sakadel.salon.service.RecordService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that set status to accept of record
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class AcceptedStatusCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(AcceptedStatusCommand.class);
    private static RecordDAO recordDAO;
    private static RecordService record;

    private static String recordsPage;

    public AcceptedStatusCommand() {
        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        recordsPage = properties.getProperty("recordsPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing AcceptedStatusCommand");

        String resultPage = recordsPage;
        LOGGER.info("Id parameter " + request.getParameter("id"));
        if (request.getParameter("id") != null) {
            long id = Integer.parseInt(request.getParameter("id"));
            if (record.updateStatus(id, Status.ACCEPTED)) {
                LOGGER.info("Set status to accepted successful");
                return resultPage;
            }
        }
        LOGGER.info("Set status to accepted unsuccessful");
        return recordsPage;
    }
}

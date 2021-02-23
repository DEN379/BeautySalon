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
 * Class that set status to cancel of record
 *
 * @author Denys Sakadel
 * @version 1.0
 */
public class CancelStatusCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(CancelStatusCommand.class);
    private static RecordDAO recordDAO;
    private static RecordService record;

    private static String recordsPage;
    private static String mainPage;

    public CancelStatusCommand() {
        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        recordsPage = properties.getProperty("recordsPage");
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing CancelStatusCommand");

        String resultPage = recordsPage;
        LOGGER.info("Id parameter " + request.getParameter("id"));
        if (request.getParameter("id") != null) {
            long id = Integer.parseInt(request.getParameter("id"));
            if (record.updateStatus(id, Status.CANCELED)) {
                LOGGER.info("Set status to canceled successful");
                return resultPage;
            }
        }
        LOGGER.info("Set status to canceled unsuccessful");
        return recordsPage;
    }
}

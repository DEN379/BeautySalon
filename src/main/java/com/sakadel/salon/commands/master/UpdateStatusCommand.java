package com.sakadel.salon.commands.master;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.Record.RecordDAO;
import com.sakadel.salon.model.Status;
import com.sakadel.salon.service.RecordService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Class that update status record to finished
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class UpdateStatusCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UpdateStatusCommand.class);
    private RecordService record;
    private RecordDAO recordDAO;

    private static String page;


    public UpdateStatusCommand() {
        LOGGER.info("Initializing UpdateStatusCommand");

        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("timeTablePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing UpdateStatusCommand");

        if (request.getParameter("id") != null) {
            long id = Integer.parseInt(request.getParameter("id"));
            if (record.updateStatus(id, Status.FINISHED)) {
                LOGGER.info("Updating status to finished was successful");
            }
        }
        LOGGER.info("Updating status to finished was unsuccessful");
        return page;
    }

}

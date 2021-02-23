package com.sakadel.salon.commands.client;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.Record.RecordDAO;
import com.sakadel.salon.model.Status;
import com.sakadel.salon.service.RecordService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateStatusPaidCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UpdateStatusPaidCommand.class);
    private RecordService record;
    private RecordDAO recordDAO;

    private static String page;


    public UpdateStatusPaidCommand() {
        LOGGER.info("Initializing UpdateStatusPaidCommand");

        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("userRecordsPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing UpdateStatusPaidCommand");

        if (request.getParameter("id") != null) {
            long id = Integer.parseInt(request.getParameter("id"));
            if (record.updateStatus(id, Status.PAID)) {
                LOGGER.info("Updating status to paid was successful");
            } else LOGGER.info("Updating status to paid was unsuccessful");
        }
        return page;
    }
}

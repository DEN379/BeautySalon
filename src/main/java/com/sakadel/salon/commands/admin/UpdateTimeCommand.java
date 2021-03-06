package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.Record.RecordDAO;
import com.sakadel.salon.model.Record;
import com.sakadel.salon.service.RecordService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Class that update time in record
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class UpdateTimeCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UpdateTimeCommand.class);
    private RecordService record;
    private RecordDAO recordDAO;

    private static String page;


    public UpdateTimeCommand() {
        LOGGER.info("Initializing UpdateTimeCommand");

        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("recordsPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing UpdateTimeCommand");


        if (request.getParameter("id") != null && request.getParameter("time") != null) {
            long record_id = Integer.parseInt(request.getParameter("id"));
            String hour = request.getParameter("time");
            LOGGER.info("Hour " + hour);

            String time;
            if (hour.length() == 1) time = "0" + hour + ":00";
            else time = hour + ":00";
            LOGGER.info("Time " + time);
            Record rec = record.findRecord(record_id);

            String date = rec.getTime().substring(0, 11) + time;
            LOGGER.info("Date " + date);

            if (record.updateTime(record_id, date)) {
                LOGGER.info("Update time was successfully");
            } else LOGGER.info("Update time was unsuccessfully");
        }


        return page;
    }
}

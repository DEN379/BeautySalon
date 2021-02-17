package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.RecordDAO;
import com.sakadel.salon.entity.Status;
import com.sakadel.salon.service.RecordService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        LOGGER.info("Executing command");

        String resultPage = recordsPage;
        LOGGER.info("PARAMETR "+ request.getParameter("id"));
        long id = Integer.parseInt(request.getParameter("id"));
        if(record.updateStatus(id, Status.CANCELED)){
            return resultPage;
        }
        else return recordsPage;
    }
}

package com.sakadel.salon.commands;

import com.sakadel.salon.dao.RecordDAO;
import com.sakadel.salon.dao.ServiceMasterDAO;
import com.sakadel.salon.model.Record;
import com.sakadel.salon.model.ServiceMaster;
import com.sakadel.salon.model.Status;
import com.sakadel.salon.service.RecordService;
import com.sakadel.salon.service.ServiceMasterService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Class that post order
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class OrderCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(OrderCommand.class);
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService recordS;
    private RecordDAO recordDAO;

    private static String mainPage;

    public OrderCommand() {
        LOGGER.info("Initializing OrderCommand");

        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        recordDAO = RecordDAO.getInstance();
        recordS = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing OrderCommand");

        String resultPage = mainPage;

        String masterId = request.getParameter("master-id");
        String serviceId = request.getParameter("service-id");
        String calendar = request.getParameter("calendar");
        String time = request.getParameter("time");

        LOGGER.info("master id => " + masterId);
        LOGGER.info("serviceId id => " + serviceId);
        LOGGER.info("calendar id => " + calendar);
        LOGGER.info("time id => " + time);
        HttpSession session = request.getSession();

        Object user_id = session.getAttribute("id");
        LOGGER.info("user_id => " + user_id);


        if (masterId != null
                && serviceId != null
                && calendar != null
                && time != null) {
            LOGGER.info("order not null");
            Record record = new Record();

            record.setUser_id((long) user_id);
            ServiceMaster sm = serviceMaster.findServiceMasterByMasterAndService(
                    (long) Integer.parseInt(masterId), (long) Integer.parseInt(serviceId));
            record.setMaster_has_service_id(sm.getId());

            record.setTime(calendar + " " + time);
            record.setStatus_id((long) Status.PENDING.ordinal() + 1);

            if (recordS.addRecord(record)) {
                LOGGER.info("Added record successfully");
            } else LOGGER.info("Added record unsuccessfully");

        }
        return resultPage;
    }
}

package com.sakadel.salon.commands;

import com.sakadel.salon.dao.MasterDAO;
import com.sakadel.salon.dao.RecordDAO;
import com.sakadel.salon.dao.ServiceDAO;
import com.sakadel.salon.dao.ServiceMasterDAO;
import com.sakadel.salon.entity.Record;
import com.sakadel.salon.entity.ServiceMaster;
import com.sakadel.salon.entity.Status;
import com.sakadel.salon.entity.User;
import com.sakadel.salon.service.MasterService;
import com.sakadel.salon.service.RecordService;
import com.sakadel.salon.service.ServiceMasterService;
import com.sakadel.salon.service.ServiceService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(OrderCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService recordS;
    private RecordDAO recordDAO;

    private static String page;
    private static String mainPage;

    public OrderCommand(){
        LOGGER.info("Initializing GetMainPageCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        recordDAO = RecordDAO.getInstance();
        recordS = new RecordService(recordDAO);
        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("orderPage");
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing command");

        String resultPage = mainPage;

        String masterId = request.getParameter("master-id");
        String serviceId = request.getParameter("service-id");
        String calendar = request.getParameter("calendar");
        String time = request.getParameter("time");

        LOGGER.info("master id"+masterId);
        LOGGER.info("serviceId id"+serviceId);
        LOGGER.info("calendar id"+calendar);
        LOGGER.info("time id"+time);
        HttpSession session = request.getSession();

        Object user_id = session.getAttribute("id");
        LOGGER.info("user_id id"+user_id);


        if ( masterId != null
                && serviceId != null
                && calendar != null
                && time != null) {
            LOGGER.info("order not null");
            Record record = new Record();

            record.setUser_id((long)user_id);
            ServiceMaster sm = serviceMaster.findServiceMasterByMasterAndService(
                    (long)Integer.parseInt(masterId), (long)Integer.parseInt(serviceId));
            LOGGER.info("sm " + sm.getId());
            record.setMaster_has_service_id(sm.getId());

            record.setTime(calendar+" "+time);
            record.setStatus_id((long)Status.PENDING.ordinal()+1);

            LOGGER.info("a? "+recordS.addRecord(record));

        }
        return resultPage;
    }
}

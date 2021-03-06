package com.sakadel.salon.commands.client;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.Master.MasterDAO;
import com.sakadel.salon.dao.Record.RecordDAO;
import com.sakadel.salon.dao.ServiceMaster.ServiceMasterDAO;
import com.sakadel.salon.model.ServiceMaster;
import com.sakadel.salon.service.MasterService;
import com.sakadel.salon.service.RecordService;
import com.sakadel.salon.service.ServiceMasterService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that post a mark to master and update his rate
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class CommentCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(CommentCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService record;
    private RecordDAO recordDAO;

    private static String page;


    public CommentCommand() {
        LOGGER.info("Initializing CommentCommand");

        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("userRecordsPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing CommentCommand");

        long id = Integer.parseInt(request.getParameter("id"));
        long master_id = Integer.parseInt(request.getParameter("master"));
        int mark = Integer.parseInt(request.getParameter("mark"));

        if(!record.updateMark(id, mark)) return page;

        List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(master_id);

        List<Long> sm = new ArrayList<>();

        for (ServiceMaster s : list) {
            sm.add(s.getId());
        }

        float avgMark = record.getAvgRecords(sm);

        LOGGER.info("Avg mark => " + avgMark);
        if (master.updateMasterRate(master_id, avgMark)) {
            LOGGER.info("Updating master rate successful");
        } else LOGGER.info("Updating master rate unsuccessful");

        return page;
    }
}

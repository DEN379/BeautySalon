package com.sakadel.salon.commands.client;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.Record.RecordDAO;
import com.sakadel.salon.dao.ServiceMaster.ServiceMasterDAO;
import com.sakadel.salon.utility.MasterTime;
import com.sakadel.salon.model.Record;
import com.sakadel.salon.model.ServiceMaster;
import com.sakadel.salon.service.RecordService;
import com.sakadel.salon.service.ServiceMasterService;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Class that get time in select
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class GetTimeCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(GetTimeCommand.class);
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService record;
    private RecordDAO recordDAO;

    private static String page;


    public GetTimeCommand() {
        LOGGER.info("Initializing GetTimeCommand");

        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("recordItemPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing GetTimeCommand");

        String date = request.getParameter("date");
        long id = Integer.parseInt(request.getParameter("id"));

        List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(id);

        List<Record> records = new ArrayList<>();

        for (ServiceMaster sm : list) {
            records.addAll(record.findAllRecordsTime(sm.getId(), date, false));
        }

        for (Record sm : records) {
            LOGGER.info("Time => " + sm.getTime());
        }

        List<Integer> freeHours = MasterTime.getFreeHours(records);

        LOGGER.info("Hours => " + Arrays.toString(freeHours.toArray()));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateNow = dtf.format(now);

        if (date.equals(dateNow.substring(0, 10))) {
            freeHours = freeHours.stream()
                    .filter(n -> n > Integer.parseInt(dateNow.substring(11, 13)))
                    .collect(Collectors.toList());
        }

        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

        sb.append("<select name=\"time\" form=\"order\" required>\n" +
                "    <option selected disabled>Select time for order</option>");
        for (Integer i : freeHours) {
            sb.append("<option value=\"").append(i).append(":00").append("\">")
                    .append(i).append(":00").append("</option>");
        }
        sb.append("</select>");

        out.print(sb.toString());

        return null;
    }
}

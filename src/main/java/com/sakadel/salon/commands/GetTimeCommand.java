package com.sakadel.salon.commands;

import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.Master;
import com.sakadel.salon.entity.MasterTime;
import com.sakadel.salon.entity.Record;
import com.sakadel.salon.entity.ServiceMaster;
import com.sakadel.salon.service.*;
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

public class GetTimeCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(GetTimeCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService record;
    private RecordDAO recordDAO;
    private UserService user;
    private UserDAO userDAO;

    private static String page;


    public GetTimeCommand(){
        LOGGER.info("Initializing GetTimeCommand");

        userDAO = UserDAO.getInstance();
        user = new UserService(userDAO);
        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);
        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("recordItemPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        String date = request.getParameter("date");
        long id = Integer.parseInt(request.getParameter("id"));

        List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(id);

        List<Record> records = new ArrayList<>();

        for(ServiceMaster sm : list){
            LOGGER.info("AAAAAAAAAAAAAAAAA");
            records.addAll(record.findAllRecordsTime(sm.getId(), date, false));
            LOGGER.info("in for sm list");
        }

        for(Record sm : records){
            LOGGER.info("time = "+sm.getTime());
        }

        List<Integer> freeHours = MasterTime.getFreeHours(records);

        LOGGER.info("hours = "+Arrays.toString(freeHours.toArray()));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateNow = dtf.format(now);

        LOGGER.info(",,,1"+date+"1,,,");
        LOGGER.info(",,,2"+dateNow.substring(0,10)+"2,,,");
        if(date.equals(dateNow.substring(0,10))){
            freeHours = freeHours.stream()
                    .filter(n -> n > Integer.parseInt(dateNow.substring(11,13)))
                    .collect(Collectors.toList());
        }





        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();

        LOGGER.info("after writer");
        sb.append("<select name=\"time\" form=\"order\" required>\n" +
                "    <option selected disabled>Select time for order</option>");
        for(Integer i : freeHours){
            sb.append("<option value=\"").append(i).append(":00").append("\">")
                    .append(i).append(":00").append("</option>");
            LOGGER.info("append "+sb.toString());
        }
        sb.append("</select>");

        LOGGER.info("after append"+sb.toString());
        out.print(sb.toString());
        LOGGER.info("after print");

        return null;
    }
}

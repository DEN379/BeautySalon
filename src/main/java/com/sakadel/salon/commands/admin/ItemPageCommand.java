package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.entity.*;
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

public class ItemPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(ItemPageCommand.class);
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
    private static String pageRe;


    public ItemPageCommand(){
        LOGGER.info("Initializing RecordsPageCommand");

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
        pageRe = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        if(request.getParameter("id") == null){
            return pageRe;
        }

        long id = Integer.parseInt(request.getParameter("id"));
        Record rec = record.findRecord(id);

        if (rec == null || rec.getId() == null) {
            return pageRe;
        }

        User userName = user.findUserById(rec.getUser_id());
        ServiceMaster sm = serviceMaster.findServiceMasterById(rec.getMaster_has_service_id());
        long status_id = rec.getStatus_id() - 1;
        Status status = Status.values()[(int)status_id];
        Master mas = master.findMasterById(sm.getMaster_id());
        Service ser = service.findServiceById(sm.getService_id());
        User userMaster = user.findUserById(mas.getUser_id());
        rec.setUser(userName);
        rec.setUserMaster(userMaster);
        rec.setService(ser);
        rec.setServiceMaster(sm);
        rec.setStatus(status);



        List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(sm.getMaster_id());
        List<Record> recs = new ArrayList<>();
        for(ServiceMaster s : list){
            recs.addAll(record.findAllRecordsTime(s.getId(), rec.getTime().substring(0,10), false));
            LOGGER.info("in for sm list");
        }

        String date = rec.getTime().substring(0,11);
        LOGGER.info("subst "+rec.getTime().substring(0,11)+"sss");
        //List<Record> recs = record.findAllRecordsTime(rec.getMaster_has_service_id(), rec.getTime().substring(0,10));
        for(Record r : recs) {
            LOGGER.info("rec !!!! "+ r.getTime());
        }
        List<Integer> freeHours = MasterTime.getFreeHours(recs);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateNow = dtf.format(now);
        LOGGER.info("subst2 "+dateNow.substring(0,11)+"sss2");
        if(date.equals(dateNow.substring(0,11))){
            freeHours = freeHours.stream()
                    .filter(n -> n > Integer.parseInt(dateNow.substring(11,13)))
                    .collect(Collectors.toList());
        } else if(date.compareTo(dateNow.substring(0,11)) < 0){
            freeHours = null;
        }

        //LOGGER.info("hours = "+ Arrays.toString(freeHours.toArray()));
//        PrintWriter out = response.getWriter();
//
//        StringBuilder sb = new StringBuilder();
//        String time = rec.getTime().substring(11,13);
//        LOGGER.info("after writer");
//        sb.append("<select name=\"time\" form=\"order\" required onchange=\"\">\n" +
//                "    <option selected value=\"").append(time).append("\">").append("Current - ").append(time).append(":00").append("</option>");
//        for(Integer i : freeHours){
//            sb.append("<option value=\"").append(i).append(":00").append("\">")
//                    .append(i).append(":00").append("</option>");
//            LOGGER.info("append "+sb.toString());
//        }
//        sb.append("</select>");
//
//        LOGGER.info("after append"+sb.toString());
//        out.print(sb.toString());
//        LOGGER.info("after print");


        request.setAttribute("record", rec);
        request.setAttribute("recTime", freeHours);


        return page;
    }
}

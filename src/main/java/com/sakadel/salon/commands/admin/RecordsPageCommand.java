package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.OrderPageCommand;
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
import java.util.ArrayList;
import java.util.List;

public class RecordsPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(RecordsPageCommand.class);
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
    private static String pageDetails;


    public RecordsPageCommand(){
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
        page = properties.getProperty("recordsPage");
        pageDetails = properties.getProperty("recordItemPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        //List<Record> records = record.findAllRecord();
        int pageNumb = 1;
        if(request.getParameter("page") != null)
            pageNumb = Integer.parseInt(request.getParameter("page"));
        int count = record.getCountRecords();
        LOGGER.info("counttttt " + count);
        int limit = 2;
        int numberPages = (int) Math.ceil((float)count / limit);
        LOGGER.info("numberrrr pages"+numberPages);
        List<Record> records = record.findRecordsWithLimit((pageNumb-1)*limit, limit);


        List<User> users = new ArrayList<>();
        for(int i = 0; i < records.size(); i++){
            users.add(user.findUserById(records.get(i).getUser_id()));
        }
        List<ServiceMaster> mastersService = new ArrayList<>();
        for(int i = 0; i < records.size(); i++){
            mastersService.add(serviceMaster.findServiceMasterById(records.get(i).getMaster_has_service_id()));
        }
        List<Status> statuses = new ArrayList<>();
        for(int i = 0; i < records.size(); i++){
            long id = records.get(i).getStatus_id() - 1;
            statuses.add(Status.values()[(int)id]);
        }


        List<Master> masters = new ArrayList<>();
        for(int i = 0; i < mastersService.size(); i++){
            masters.add(master.findMasterById(mastersService.get(i).getMaster_id()));
        }
        List<Service> services = new ArrayList<>();
        for(int i = 0; i < mastersService.size(); i++){
            services.add(service.findServiceById(mastersService.get(i).getService_id()));
        }

        List<User> mastersUser = new ArrayList<>();
        for(int i = 0; i < masters.size(); i++){
            mastersUser.add(user.findUserById(masters.get(i).getUser_id()));
        }

        for(int i = 0; i < records.size(); i++){
            records.get(i).setUser(users.get(i));
            records.get(i).setUserMaster(mastersUser.get(i));
            records.get(i).setService(services.get(i));
            records.get(i).setServiceMaster(mastersService.get(i));
            records.get(i).setStatus(statuses.get(i));
        }
        request.setAttribute("records", records);


        //PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

        sb.append("<ul type=\"none\" class=\"pager\">\n");
        for(int i = 0; i < numberPages; i++){

            sb.append(
                    "<li class=\"pager-item\"><a href=\"").append(request.getContextPath())
                    .append("/admin/records?page=").append(i + 1).append("\" title=\"На страницу номер ").append(i).append("\">")
                    .append(i+1).append("</a></li>\n");
        }
        sb.append( "   </ul>");

        //out.print(sb.toString());

        request.setAttribute("pages",sb.toString());

//        List<Record> records = record.findAllRecord();
//        List<User> users = new ArrayList<>();
//        List<ServiceMaster> mastersService = new ArrayList<>();
//        List<Status> statuses = new ArrayList<>();
//        List<Master> masters = new ArrayList<>();
//        List<Service> services = new ArrayList<>();
//        List<User> mastersUser = new ArrayList<>();
//
//        for(int i = 0; i < records.size(); i++){
//            users.add(user.findUserById(records.get(i).getUser_id()));
//            mastersService.add(serviceMaster.findServiceMasterById(records.get(i).getMaster_has_service_id()));
//            long id = records.get(i).getStatus_id() - 1;
//            statuses.add(Status.values()[(int)id]);
//            masters.add(master.findMasterById(mastersService.get(i).getMaster_id()));
//            services.add(service.findServiceById(mastersService.get(i).getService_id()));
//            mastersUser.add(user.findUserById(masters.get(i).getUser_id()));
//            records.get(i).setUser(users.get(i));
//            records.get(i).setUserMaster(mastersUser.get(i));
//            records.get(i).setService(services.get(i));
//            records.get(i).setServiceMaster(mastersService.get(i));
//            records.get(i).setStatus(statuses.get(i));
//        }

        return page;
    }
}

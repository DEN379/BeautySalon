package com.sakadel.salon.commands;

import com.sakadel.salon.commands.admin.*;
import com.sakadel.salon.commands.auth.*;
import com.sakadel.salon.commands.client.*;
import com.sakadel.salon.commands.master.OrderByNameCommand;
import com.sakadel.salon.commands.master.TimeTablePageCommand;
import com.sakadel.salon.commands.master.UpdateStatusCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * Class that manage commands by request
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class CommandManager {

    private static final Logger LOGGER = Logger.getLogger(CommandManager.class);

    private HashMap<String, ServletCommand> getCommands;
    private HashMap<String, ServletCommand> postCommands;
    private static String errorPage;

    public CommandManager() {
        LOGGER.info("Initializing CommandManager");

        getCommands = new HashMap<>();
        postCommands = new HashMap<>();

        getCommands.put("/", new MainPageCommand());
        getCommands.put("/login", new LoginPageCommand());

        getCommands.put("/register", new RegisterPageCommand());

        getCommands.put("/order", new OrderPageCommand());
        getCommands.put("/order/masters", new GetMastersCommand());
        getCommands.put("/order/time", new GetTimeCommand());
        getCommands.put("/order/comment", new CommentPageCommand());

        getCommands.put("/filterServices", new FilterServiceByMasterCommand());
        getCommands.put("/filterMasters", new FilterMasterByServiceCommand());

        getCommands.put("/orderBy", new OrderByNameCommand());

        getCommands.put("/myOrders", new MyOrdersPageCommand());


        getCommands.put("/admin/records", new RecordsPageCommand());
        getCommands.put("/admin/records/edit", new ItemPageCommand());

        getCommands.put("/admin/users", new UsersPageCommand());
        getCommands.put("/admin/users/edit", new UsersItemPageCommand());
        getCommands.put("/admin/users/getService", new GetServicesCommand());

        getCommands.put("/master/timeTable", new TimeTablePageCommand());
        getCommands.put("/admin/createService", new CreateServicePageCommand());

        postCommands.put("/login", new LoginCommand());
        postCommands.put("/logout", new LogoutCommand());
        postCommands.put("/register", new RegisterCommand());
        postCommands.put("/order", new OrderCommand());
        postCommands.put("/order/comment", new CommentCommand());

        postCommands.put("/master/timeTable/updateStatus", new UpdateStatusCommand());
        postCommands.put("/myOrders/paid", new UpdateStatusPaidCommand());
        postCommands.put("/admin/createService", new CreateServiceCommand());

        postCommands.put("/admin/users", new UsersPageCommand());
        postCommands.put("/admin/users/setService", new UsersCommand());
        postCommands.put("/admin/users/setMaster", new SetMasterRoleCommand());

        postCommands.put("/admin/records/cancel", new CancelStatusCommand());
        postCommands.put("/admin/records/accept", new AcceptedStatusCommand());
        postCommands.put("/admin/records/updateTime", new UpdateTimeCommand());


//        ParseProperties properties = ParseProperties.getInstance();
//        errorPage = properties.getProperty("errorPage");
    }


    public ServletCommand getGetCommand(HttpServletRequest request) {
        String command = getMapping(request);
        LOGGER.info("Getting command " + command);

        if (getCommands.get(command) == null) {
            return getCommands.get("/");
        }

        return getCommands.get(command);
    }


    public ServletCommand getPostCommand(HttpServletRequest request) {
        String command = getMapping(request);
        LOGGER.info("Getting command " + command);

        if (postCommands.get(command) == null) {
            return getCommands.get("/");
        }

        return postCommands.get(command);
    }


    public String getMapping(HttpServletRequest request) {
        String mapping = request.getRequestURI().substring(request.getContextPath().length());
        if (mapping.endsWith("/")) {
            mapping = mapping.substring(0, mapping.length() - 1);
        }

        return mapping;
    }
}

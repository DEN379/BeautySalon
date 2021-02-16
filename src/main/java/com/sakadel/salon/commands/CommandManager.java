package com.sakadel.salon.commands;

import com.sakadel.salon.commands.admin.*;
import com.sakadel.salon.commands.master.TimeTablePageCommand;
import com.sakadel.salon.commands.master.UpdateStatusCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CommandManager {

    private static final Logger LOGGER = Logger.getLogger(CommandManager.class);

    private HashMap<String, ServletCommand> getCommands;
    private HashMap<String, ServletCommand> postCommands;
    private static String errorPage;

    public CommandManager(){
        LOGGER.info("Initializing CommandManager");

        getCommands = new HashMap<>();
        postCommands = new HashMap<>();

        getCommands.put("/", new MainPageCommand());
        getCommands.put("/login", new LoginPageCommand());
        postCommands.put("/logout", new LogoutCommand());
        getCommands.put("/register", new RegisterPageCommand());

        getCommands.put("/order", new OrderPageCommand());
        getCommands.put("/order/masters", new GetMastersCommand());
        getCommands.put("/order/time", new GetTimeCommand());

        getCommands.put("/records", new RecordsPageCommand());
        getCommands.put("/records/edit", new ItemPageCommand());

        getCommands.put("/timeTable", new TimeTablePageCommand());
        postCommands.put("/timeTable/updateStatus", new UpdateStatusCommand());

        postCommands.put("/login", new LoginCommand());
        postCommands.put("/register", new RegisterCommand());
        postCommands.put("/order", new OrderCommand());

        postCommands.put("/records/cancel", new CancelStatusCommand());
        postCommands.put("/records/accept", new AcceptedStatusCommand());
        postCommands.put("/records/updateTime", new UpdateTimeCommand());
//        ParseProperties properties = ParseProperties.getInstance();
//        errorPage = properties.getProperty("errorPage");
    }


    public ServletCommand getGetCommand(HttpServletRequest request) {
        String command = getMappting(request);
        LOGGER.info("Getting command " + command);

        if(getCommands.get(command) == null) {
            return getCommands.get("/");
        }

        return getCommands.get(command);
    }


    public ServletCommand getPostCommand(HttpServletRequest request) {
        String command = getMappting(request);
        LOGGER.info("Getting command " + command);

        if(postCommands.get(command) == null) {
            return getCommands.get("/");
        }

        return postCommands.get(command);
    }


    public String getMappting(HttpServletRequest request) {
        String mapping = request.getRequestURI().substring(request.getContextPath().length());
        if(mapping.endsWith("/")) {
            mapping = mapping.substring(0, mapping.length() - 1);
        }

        return mapping;
    }
}

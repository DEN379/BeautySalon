package com.sakadel.salon.commands.admin;

import com.sakadel.salon.commands.ServletCommand;
import com.sakadel.salon.dao.*;
import com.sakadel.salon.model.Role;
import com.sakadel.salon.service.*;
import com.sakadel.salon.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Class that set user as master and add master
 *
 * @author Denys Sakadel
 * @version 1.0
 */

public class SetMasterRoleCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(SetMasterRoleCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;
    private UserService user;
    private UserDAO userDAO;

    private static String page;
    private static String pageRe;


    public SetMasterRoleCommand() {
        LOGGER.info("Initializing SetMasterRoleCommand");

        userDAO = UserDAO.getInstance();
        user = new UserService(userDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("usersItemPage");
        pageRe = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        if (request.getParameter("id") != null) {
            LOGGER.info("Parameter user_id => " + request.getParameter("id"));
            long user_id = Long.parseLong(request.getParameter("id"));

            if (user.updateRole(user_id, Role.MASTER)) {
                if (master.addMaster(user_id))
                    LOGGER.info("Master added successfully");
            }
        } else return pageRe;


        return page;
    }
}

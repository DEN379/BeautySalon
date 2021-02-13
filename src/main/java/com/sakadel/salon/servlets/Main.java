package com.sakadel.salon.servlets;

import com.sakadel.salon.commands.CommandManager;
import com.sakadel.salon.commands.ServletCommand;
import org.apache.log4j.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Servlet.class);

    private CommandManager commandManager;

    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("Initializing Servlet");
        commandManager = new CommandManager();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing get request");

        ServletCommand command = commandManager.getGetCommand(request);
        String page = command.execute(request, response);
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing post request");

        ServletCommand command = commandManager.getPostCommand(request);
        String page = command.execute(request, response);
        //request.getRequestDispatcher(page).forward(request, response);
        response.sendRedirect("/salon");
    }
}
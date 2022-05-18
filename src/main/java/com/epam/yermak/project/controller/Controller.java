package com.epam.yermak.project.controller;

import com.epam.yermak.project.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        executeRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        executeRequest(req, resp);
    }

    private void executeRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String commandName = req.getParameter(RequestParam.COMMAND);
        Command command = Command.of(commandName);
        ResponseContext responseContext = command.execute(req, req.getSession(true));
        if (responseContext.getResponseContextType().equals(ResponseContext.ResponseContextType.REDIRECT)) {
            resp.sendRedirect(req.getContextPath() + responseContext.getPagePath());
        } else {
            req.getRequestDispatcher(responseContext.getPagePath()).forward(req, resp);
        }
    }
}

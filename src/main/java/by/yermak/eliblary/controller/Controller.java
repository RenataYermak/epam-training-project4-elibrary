package by.yermak.eliblary.controller;

import by.yermak.eliblary.controller.command.Command;

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
        var commandName = req.getParameter(RequestParameter.COMMAND);
        var command = Command.of(commandName);
        var router = command.execute(req, req.getSession(true));
        if (router.getRouterType().equals(Router.RouterType.REDIRECT)) {
            resp.sendRedirect(req.getContextPath() + router.getPagePath());
        } else {
            req.getRequestDispatcher(router.getPagePath()).forward(req, resp);
        }
    }
}

package by.yermak.eliblary.controller;

import by.yermak.eliblary.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();


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
        switch (router.getRouterType()) {
            case FORWARD -> req.getRequestDispatcher(router.getPagePath()).forward(req, resp);
            case REDIRECT -> resp.sendRedirect(req.getContextPath() + router.getPagePath());
            case ERROR -> resp.sendError(router.getPagePath().equals(PagePath.ERROR_PAGE_404) ? 404 : 500);
            default -> {
                LOGGER.error("wrong router type");
                resp.sendError(500);
            }
        }
//        if (router.getRouterType().equals(Router.RouterType.REDIRECT)) {
//            resp.sendRedirect(req.getContextPath() + router.getPagePath());
//        } else {
//            req.getRequestDispatcher(router.getPagePath()).forward(req, resp);
//        }
    }
}

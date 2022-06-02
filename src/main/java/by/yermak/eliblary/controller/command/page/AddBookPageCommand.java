package by.yermak.eliblary.controller.command.page;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddBookPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        Router currentRouter = new Router();
        currentRouter.setPagePath(PagePath.ADD_BOOK);
        currentRouter.setRouteType(Router.RouterType.FORWARD);
        return currentRouter;
    }
}
package by.yermak.eliblary.controller.command.page;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddAuthorPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        if (isAuthorized(session) && isAdmin(session)) {
            Router currentRouter = new Router();
            currentRouter.setPagePath(PagePath.ADD_AUTHOR);
            currentRouter.setRouteType(Router.RouterType.FORWARD);
            return currentRouter;
        }
        return new Router(PagePath.ADD_AUTHOR, Router.RouterType.FORWARD);
    }
}

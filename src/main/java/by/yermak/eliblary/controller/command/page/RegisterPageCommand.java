package by.yermak.eliblary.controller.command.page;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegisterPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        Router currentRouter = new Router();
        currentRouter.setPagePath(PagePath.REGISTRATION);
        currentRouter.setRouteType(Router.RouterType.REDIRECT);
        return currentRouter;
    }
}

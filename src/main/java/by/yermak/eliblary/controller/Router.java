package by.yermak.eliblary.controller;

public class Router {
    public enum RouterType {
        FORWARD,
        REDIRECT
    }

    private String pagePath;
    private RouterType routerType;

    public Router() {
    }

    public Router(String pagePath, RouterType routerType) {
        this.pagePath = pagePath;
        this.routerType = routerType;
    }

    public void setRouteType(RouterType routerType) {
        if (routerType == null) {
            routerType = RouterType.REDIRECT;
        }
        this.routerType = routerType;
    }

    public RouterType getRouterType() {
        return routerType;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getPagePath() {
        return pagePath;
    }
}

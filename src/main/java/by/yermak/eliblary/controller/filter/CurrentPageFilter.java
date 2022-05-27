package by.yermak.eliblary.controller.filter;

import by.yermak.eliblary.controller.RequestAttribute;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CurrentPageFilter implements Filter {

    private static final String CONTAINS_JSP = "webapp/";
    private static final String CONTAINS_CONTROLLER = "controller";
    private static final String CONTAINS_CHANGE_LOCALE = "command=change_locale";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var currentPage = httpRequest.getRequestURL().toString();

        if (currentPage.contains(CONTAINS_JSP)) {
            int index = currentPage.indexOf(CONTAINS_JSP);
            currentPage = currentPage.substring(index);
            httpRequest.getSession().setAttribute(RequestAttribute.CURRENT_PAGE, currentPage);
        } else if (currentPage.contains(CONTAINS_CONTROLLER) && !httpRequest.getParameterMap().isEmpty()
                && httpRequest.getQueryString() != null &&
                !httpRequest.getQueryString().contains(CONTAINS_CHANGE_LOCALE)) {
            int index = currentPage.indexOf(CONTAINS_CONTROLLER);
            currentPage = currentPage.substring(index) + "?" + httpRequest.getQueryString();

            httpRequest.getSession().setAttribute(RequestAttribute.CURRENT_PAGE, currentPage);
        }
        filterChain.doFilter(httpRequest, servletResponse);
    }
}

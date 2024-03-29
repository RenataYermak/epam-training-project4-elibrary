package by.yermak.eliblary.controller.filter;

import by.yermak.eliblary.controller.RequestAttribute;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        if (request.getSession().getAttribute(RequestAttribute.LOCALE_NAME) == null) {
            request.getSession().setAttribute(RequestAttribute.LOCALE_NAME, RequestAttribute.DEFAULT_LANG);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}

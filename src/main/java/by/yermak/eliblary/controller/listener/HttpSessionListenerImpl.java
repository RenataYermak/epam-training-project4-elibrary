package by.yermak.eliblary.controller.listener;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.SessionAttribute;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "en_EN";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        var session = se.getSession();
        session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.SIGN_IN);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
package com.epam.project.controller.listener;

import com.epam.project.controller.PagePath;
import com.epam.project.controller.SessionAttribute;
import com.mysql.cj.Session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@WebListener
public class  HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "en_EN";

    public HttpSessionListenerImpl() {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSessionListener.super.sessionCreated(se);
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
        //session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.BOOKS);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);
    }
}
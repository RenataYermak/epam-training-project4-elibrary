package by.yermak.eliblary.util.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SessionFactory {
    static final String USER_NAME = "mail.smtp.user";
    static final String USER_PASSWORD = "mail.smtp.password";
    private static SessionFactory instance;

    private SessionFactory() {
    }

    public static SessionFactory getInstance() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance;
    }

    static Session createSession(Properties properties) {
        String userName = properties.getProperty(USER_NAME);
        String userPassword = properties.getProperty(USER_PASSWORD);
        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, userPassword);
            }
        });
    }
}
package by.yermak.eliblary.util.email;

import by.yermak.eliblary.util.exception.UtilException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import static org.apache.logging.log4j.core.util.Loader.getClassLoader;

public class MailSender {
    private static final Logger LOGGER = LogManager.getLogger();

    private Properties properties;
    private static final String PROPERTIES_FILE = "config\\email.properties";
    private static final String CONTENT_TYPE ="text/plain; charset=UTF-8";
    private static MailSender instance;

    private MailSender() {
        try {
            properties = new Properties();
            properties.load(getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static MailSender getInstance() {
        if (instance == null) {
            instance = new MailSender();
        }
        return instance;
    }

    public void send(String sendToMail, String mailSubject, String mailText) {
        try {
            var mimeMessage = initMessage(sendToMail, mailText, mailSubject);
            Transport.send(mimeMessage);
        } catch (MessagingException | UtilException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private MimeMessage initMessage(String sendToMail, String mailText, String mailSubject) throws UtilException {
        MimeMessage message;
        try {
            var session = SessionFactory.createSession(properties);
            session.setDebug(true);
            message = new MimeMessage(session);
            message.setSubject(mailSubject);
            message.setContent(mailText, CONTENT_TYPE);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToMail));
        } catch (MessagingException e) {
            throw new UtilException(e.getMessage());
        }
        return message;
    }
}

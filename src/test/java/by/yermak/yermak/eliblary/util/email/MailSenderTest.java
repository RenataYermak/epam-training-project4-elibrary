package by.yermak.yermak.eliblary.util.email;

import by.yermak.eliblary.util.email.MailSender;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

class MailSenderTest {
    public static final String SEND_EMAIL_TO = "renatkahturo@gmail.com";
    public static final String MAIL_SUBJECT = "mail subject";
    public static final String MAIL_TEXT = "Text message";

    @Test
    void sendTest() {
        MailSender.getInstance().send(SEND_EMAIL_TO, MAIL_SUBJECT, MAIL_TEXT);
    }
}



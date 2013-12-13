package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.commons.mail.EmailException;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;

import es.udc.fi.dc.photoalbum.util.utils.Mail;

/**
 * The class <code>MailTest</code> contains tests for the class
 * {@link <code>Mail</code>}.
 */
public class MailTest {

    private static final String EMAIL_HOST_NAME = "testmail.com";
    private static final String EMAIL_FROM = "test.org@testmail.com";
    private static final Locale LOCALE_EN = Locale.US;
    private static final Locale LOCALE_ES = new Locale("es","ES");

    private Mail mail;

    private void initMail(String emailTo) throws EmailException {
        mail = new Mail(emailTo) {
            @Override
            protected void initializeEmail() throws EmailException {
                this.email.setHostName(EMAIL_HOST_NAME);
                this.email.setFrom(EMAIL_FROM);
            }
        };
    }

    @Before
    public void setUp() {
        Mailbox.clearAll();
    }

    /**
     * Run the void sendRegister(Locale) method test
     */
    @Test
    public void testSendRegister() throws EmailException,
            MessagingException, IOException {
        String emailTo = "reg.dest@foo.com";
        initMail(emailTo);
        mail.sendRegister(LOCALE_EN);
        mail.sendRegister(LOCALE_ES);
        List<Message> inbox = Mailbox.get(emailTo);
        assertTrue(inbox.size() == 2);
        assertEquals(Mail.SUBJECT_REG_EN, inbox.get(0).getSubject());
        assertEquals(Mail.MESSAGE_REG_EN, inbox.get(0).getContent());
        assertEquals(Mail.SUBJECT_REG_ES, inbox.get(1).getSubject());
        assertEquals(Mail.MESSAGE_REG_ES, inbox.get(1).getContent());
    }

    /**
     * Run the void sendPass(String, Locale) method test
     */
    @Test
    public void testSendPass() throws EmailException,
            MessagingException, IOException {
        String emailTo = "pass.dest@foo.com";
        String password = "123456aA";
        initMail(emailTo);
        mail.sendPass(password, LOCALE_EN);
        mail.sendPass(password, LOCALE_ES);
        List<Message> inbox = Mailbox.get(emailTo);
        assertTrue(inbox.size() == 2);
        assertEquals(Mail.SUBJECT_PASS_EN, inbox.get(0).getSubject());
        assertEquals(Mail.MESSAGE_PASS_EN+password, inbox.get(0).getContent());
        assertEquals(Mail.SUBJECT_PASS_ES, inbox.get(1).getSubject());
        assertEquals(Mail.MESSAGE_PASS_ES+password, inbox.get(1).getContent());
    }
}
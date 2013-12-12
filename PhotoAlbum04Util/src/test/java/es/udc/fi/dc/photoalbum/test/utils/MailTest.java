package es.udc.fi.dc.photoalbum.test.utils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.mail.Message;
import javax.mail.MessagingException;

import junit.framework.TestCase;

import org.apache.commons.mail.EmailException;
import org.jvnet.mock_javamail.Mailbox;

import es.udc.fi.dc.photoalbum.util.utils.Mail;

/**
 * The class <code>MailTest</code> contains tests for the class
 * {@link <code>Mail</code>}.
 */
public class MailTest extends TestCase {

    private static final String EMAIL_HOST_NAME = "testmail.com";
    private static final String EMAIL_FROM = "test.org@testmail.com";;

    private Mail mail;

    @Override
    protected void setUp() {
        Mailbox.clearAll();
    }

    private void initMail(String emailTo) throws EmailException {
        mail = new Mail(emailTo) {
            @Override
            protected void initializeEmail() throws EmailException {
                this.email.setHostName(EMAIL_HOST_NAME);
                this.email.setFrom(EMAIL_FROM);
            }
        };
    }

    /**
     * Run the void sendRegister(Locale) method test
     */
    public void testSendRegister() throws EmailException,
            MessagingException, IOException {
        String emailTo = "reg.dest@foo.com";
        initMail(emailTo);
        mail.sendRegister(Locale.US);
        List<Message> inbox = Mailbox.get(emailTo);
        assertTrue(inbox.size() == 1);
        assertEquals(Mail.SUBJECT_REG_EN, inbox.get(0).getSubject());
        assertEquals(Mail.MESSAGE_REG_EN, inbox.get(0).getContent());
    }

    /**
     * Run the void sendPass(String, Locale) method test
     */
    public void testSendPass() throws EmailException,
            MessagingException, IOException {
        String emailTo = "pass.dest@foo.com";
        String password = "123456aA";
        initMail(emailTo);
        mail.sendPass(password, Locale.US);
        List<Message> inbox = Mailbox.get(emailTo);
        assertTrue(inbox.size() == 1);
        assertEquals(Mail.SUBJECT_PASS_EN, inbox.get(0).getSubject());
        assertEquals(Mail.MESSAGE_PASS_EN+password, inbox.get(0).getContent());
    }
}
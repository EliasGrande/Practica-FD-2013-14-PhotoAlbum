package es.udc.fi.dc.photoalbum.util.utils;

import java.util.Locale;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Utility for sending mails
 */
public class Mail {
    /**
     * Defines a host name for the mail.
     */
    private static final String HOST_NAME = "s246.sam-solutions.net";
    /**
     * Defines a an SMTP port for the mail
     */
    private static final int SMTP_PORT = 25;
    /**
     * Defines an email variable for the mail.
     */
    private static final String EMAIL = "";
    /**
     * Defines a login variable to do the email.
     */
    private static final String LOGIN = "ssav";
    /**
     * Defines a password variable to do the email.
     */
    private static final String PASSWORD = "";
    /**
     * Defines a subject on the registration in English.
     */
    private static final String SUBJECT_REG_EN = "Registration at \"Photo Albums\"";
    /**
     * Defines a variable for password recovery in English.
     */
    private static final String SUBJECT_PASS_EN = "Password recovery";
    /**
     * Defines a message for the registration in English language.
     */
    private static final String MESSAGE_REG_EN = "Dear User, thanks for registration. Enjoy!";
    /**
     * Defines a message for get the new password in English language.
     */
    private static final String MESSAGE_PASS_EN = "Dear User, you can login with new password "
            + "and change";
    /**
     * Defines a subject on the registration in Spanish.
     */
    private static final String SUBJECT_REG_ES = "Registro en \"Photo Albums\"";
    /**
     * Defines a variable for password recovery in Spanish.
     */
    private static final String SUBJECT_PASS_ES = "Recuperar la contraseña";
    /**
     * Defines a password on the registration in Spanish language.
     */
    private static final String MESSAGE_REG_ES = "Estimado usuario, gracias por inscripción. Disfrute!";
    /**
     * Defines a message for get the new password in Spanish language.
     */
    private static final String MESSAGE_PASS_ES = "Estimado usuario, puedes iniciar sesión con la nueva "
            + "contraseña y cambiarla en Perfil. Nueva contraseña es:";

    /**
     * Constructor of class {@link Email}.
     * 
     * @return An {@link Email}
     */
    private Email email = new SimpleEmail();

    /**
     * Constructor of class {@link Mail}.
     * 
     * @param emailTo
     *            recepient's email
     * 
     */
    public Mail(String emailTo) throws EmailException {
        this.email.setHostName(HOST_NAME);
        this.email.setSmtpPort(SMTP_PORT);
        this.email.setFrom(EMAIL);
        this.email.setAuthenticator(new DefaultAuthenticator(LOGIN,
                PASSWORD));
        this.email.addTo(emailTo);
    }

    /**
     * Sends mail on registration
     * 
     * @param locale
     *            Defines the language, country, etc that is using the
     *            {@link User}.
     */
    public void sendRegister(Locale locale) throws EmailException {
        if (locale.equals(Locale.US)) {
            this.email.setSubject(SUBJECT_REG_EN);
            this.email.setMsg(MESSAGE_REG_EN);
        } else {
            this.email.setSubject(SUBJECT_REG_ES);
            this.email.setMsg(MESSAGE_REG_ES);
        }
        this.email.send();
    }

    /**
     * Sends mail on password forget
     * 
     * @param password
     *            Password for the {@link User} who has forgotten his
     *            password
     * @param locale
     *            Defines the language, country, etc that is using the
     *            {@link User}.
     */
    public void sendPass(String password, Locale locale)
            throws EmailException {
        if (locale.equals(Locale.US)) {
            this.email.setSubject(SUBJECT_PASS_EN);
            this.email.setMsg(MESSAGE_PASS_EN + password);
        } else {
            this.email.setSubject(SUBJECT_PASS_ES);
            this.email.setMsg(MESSAGE_PASS_ES + password);
        }
        this.email.send();
    }
}

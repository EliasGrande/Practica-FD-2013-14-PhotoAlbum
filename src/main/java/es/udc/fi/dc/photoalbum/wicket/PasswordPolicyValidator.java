package es.udc.fi.dc.photoalbum.wicket;

import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * Validates if the password meets the requirements.
 */
@SuppressWarnings("serial")
public class PasswordPolicyValidator implements IValidator<String> {

    /**
     * Pattern that forces you to put at least one uppercase letter in
     * the password.
     */
    private static final Pattern UPPER = Pattern.compile("[A-Z]");
    /**
     * Pattern that forces you to put at least one lowercase letter in
     * the password.
     */
    private static final Pattern LOWER = Pattern.compile("[a-z]");
    /**
     * Pattern that forces you to put at least one numberin the
     * password.
     */
    private static final Pattern NUMBER = Pattern.compile("[0-9]");

    /**
     * Method that validate the password.
     * 
     * @param validatable
     *            Represents any object that can be validate.
     * @see 
     *      org.apache.wicket.validation.IValidator#validate(IValidatable
     *      <String>)
     */
    public void validate(IValidatable<String> validatable) {
        final String password = validatable.getValue();
        if (!NUMBER.matcher(password).find()) {
            error(validatable, "no-digit");
        }
        if (!LOWER.matcher(password).find()) {
            error(validatable, "no-lower");
        }
        if (!UPPER.matcher(password).find()) {
            error(validatable, "no-upper");
        }
    }

    /**
     * Method error.
     * 
     * @param validatable
     *            Represents any object that can be validate.
     * @param errorKey
     *            The error occurred while trying to validate the
     *            password.
     */
    @SuppressWarnings("deprecation")
    private void error(IValidatable<String> validatable,
            String errorKey) {
        ValidationError error = new ValidationError();
        error.addMessageKey(getClass().getSimpleName() + "."
                + errorKey);
        validatable.error(error);
    }
}

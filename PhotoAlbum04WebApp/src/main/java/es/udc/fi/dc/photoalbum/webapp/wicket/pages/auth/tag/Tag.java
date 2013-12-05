package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag;

import java.io.Serializable;

/**
 * Class necessary to use a form that return a {@link Tag}.
 */
@SuppressWarnings("serial")
public class Tag implements Serializable {
    /**
     * The value of the tag.
     */
    private String value;

    /**
     * Empty constructor for tag.
     */
    public Tag() {
    }

    /**
     * Constructor for Tag.
     * 
     * @param value
     *            {@link #value}
     */
    public Tag(String value) {
        this.setValue(value);
    }

    /**
     * Method getValue.
     * 
     * @return String the value of the tag.
     */
    public String getValue() {
        return value;
    }

    /**
     * Method setValue.
     * 
     * @param value
     *            The new value of tag.
     */
    public void setValue(String value) {
        this.value = value;
    }

}

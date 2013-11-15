package es.udc.fi.dc.photoalbum.wicket.pages.auth.tag;

import java.io.Serializable;

/**
 */
@SuppressWarnings("serial")
public class Tag implements Serializable {

    private String value;

    public Tag() {
    }

    /**
     * Constructor for Tag.
     * 
     * @param value
     *            String
     */
    public Tag(String value) {
        this.setValue(value);
    }

    /**
     * Method getValue.
     * 
     * @return String
     */
    public String getValue() {
        return value;
    }

    /**
     * Method setValue.
     * 
     * @param value
     *            String
     */
    public void setValue(String value) {
        this.value = value;
    }

}

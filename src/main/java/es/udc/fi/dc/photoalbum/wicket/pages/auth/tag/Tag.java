package es.udc.fi.dc.photoalbum.wicket.pages.auth.tag;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tag implements Serializable {

    private String value;

    public Tag() {
    }

    public Tag(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

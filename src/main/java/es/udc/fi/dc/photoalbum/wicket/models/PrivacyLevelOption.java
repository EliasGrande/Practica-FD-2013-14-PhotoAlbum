package es.udc.fi.dc.photoalbum.wicket.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.model.StringResourceModel;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

@SuppressWarnings("serial")
public class PrivacyLevelOption implements Serializable {

    private String label;
    private String value;

    public PrivacyLevelOption(String privacyLevel, Component cmp) {
        this.label = getLabel(privacyLevel, cmp);
        this.value = privacyLevel;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    private static String getLabel(String value, Component cmp) {
        return new StringResourceModel("privacyLevel.label."
                + value.toLowerCase(), cmp, null).getString();
    }

    public static ArrayList<PrivacyLevelOption> getAlbumOptions(
            Component cmp) {
        ArrayList<PrivacyLevelOption> options = new ArrayList<PrivacyLevelOption>();
        Iterator<String> iterator = PrivacyLevel.LIST_ALBUM
                .iterator();
        while (iterator.hasNext())
            options.add(new PrivacyLevelOption(iterator.next(), cmp));
        return options;
    }

    public static ArrayList<PrivacyLevelOption> getFileOptions(
            Component cmp) {
        ArrayList<PrivacyLevelOption> options = new ArrayList<PrivacyLevelOption>();
        Iterator<String> iterator = PrivacyLevel.LIST_FILE.iterator();
        while (iterator.hasNext())
            options.add(new PrivacyLevelOption(iterator.next(), cmp));
        return options;
    }
}

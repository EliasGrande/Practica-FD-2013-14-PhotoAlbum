package es.udc.fi.dc.photoalbum.wicket.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.StringResourceModel;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

/**
 */
@SuppressWarnings("serial")
public class PrivacyLevelOption implements Serializable {

    private String label;
    private String value;

    /**
     * Constructor for PrivacyLevelOption.
     * 
     * @param privacyLevel
     *            String
     * @param cmp
     *            Component
     */
    public PrivacyLevelOption(String privacyLevel, Component cmp) {
        this.label = getLabel(privacyLevel, cmp);
        this.value = privacyLevel;
    }

    /**
     * Method getLabel.
     * 
     * @return String
     */
    public String getLabel() {
        return label;
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
     * Method getLabel.
     * 
     * @param value
     *            String
     * @param cmp
     *            Component
     * @return String
     */
    private static String getLabel(String value, Component cmp) {
        return new StringResourceModel("privacyLevel.label."
                + value.toLowerCase(), cmp, null).getString();
    }

    /**
     * Method getAlbumOptions.
     * 
     * @param cmp
     *            Component
     * @return List<PrivacyLevelOption>
     */
    public static List<PrivacyLevelOption> getAlbumOptions(
            Component cmp) {
        ArrayList<PrivacyLevelOption> options = new ArrayList<PrivacyLevelOption>();
        Iterator<String> iterator = PrivacyLevel.LIST_ALBUM
                .iterator();
        while (iterator.hasNext())
            options.add(new PrivacyLevelOption(iterator.next(), cmp));
        return options;
    }

    /**
     * Method getFileOptions.
     * 
     * @param cmp
     *            Component
     * @return List<PrivacyLevelOption>
     */
    public static List<PrivacyLevelOption> getFileOptions(
            Component cmp) {
        ArrayList<PrivacyLevelOption> options = new ArrayList<PrivacyLevelOption>();
        Iterator<String> iterator = PrivacyLevel.LIST_FILE.iterator();
        while (iterator.hasNext())
            options.add(new PrivacyLevelOption(iterator.next(), cmp));
        return options;
    }
}

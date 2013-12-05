package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.StringResourceModel;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

/**
 * All methods necessaries to privacy management.
 * @author alejandro
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class PrivacyLevelOption implements Serializable {
    /**
     * The label privacy level.
     */
    private String label;
    /**
     * The value of the privacy level.
     */
    private String value;

    /**
     * Constructor for PrivacyLevelOption.
     * 
     * @param privacyLevel
     *            The value of the privacy level.
     * @param cmp
     *            The component which use the privacy level.
     */
    public PrivacyLevelOption(String privacyLevel, Component cmp) {
        this.label = getLabel(privacyLevel, cmp);
        this.value = privacyLevel;
    }

    /**
     * Obtain the label of privacy level.
     * 
    
     * @return String The label of privacy level. */
    public String getLabel() {
        return label;
    }

    /**
     * Obtain the value of the privacy level.
     * 
    
     * @return String The value of the privacy level. */
    public String getValue() {
        return value;
    }

    /**
     * Obtain the label of privacy level.
     * 
     * @param value
     *            {@link #value}
     * @param cmp
     *            The component which use the privacy level.
    
     * @return String The label of privacy level. */
    private static String getLabel(String value, Component cmp) {
        return new StringResourceModel("privacyLevel.label."
                + value.toLowerCase(), cmp, null).getString();
    }

    /**
     * Method getAlbumOptions.
     * 
     * @param cmp
     *            The component which use the privacy level.
    
     * @return List<PrivacyLevelOption> Return the available privacy
     *         levels for an {@link Album}. */
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
     *            The component which use the privacy level.
    
     * @return List<PrivacyLevelOption> Return the available privacy
     *         levels for an {@link File}. */
    public static List<PrivacyLevelOption> getFileOptions(
            Component cmp) {
        ArrayList<PrivacyLevelOption> options = new ArrayList<PrivacyLevelOption>();
        Iterator<String> iterator = PrivacyLevel.LIST_FILE.iterator();
        while (iterator.hasNext())
            options.add(new PrivacyLevelOption(iterator.next(), cmp));
        return options;
    }
}

package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;

/**
 * Necessary model to use the {@link PrivacyLevelOption}. This class
 * has two constructors because the {@link Album}s and {@link File}s
 * have different levels of privacy.
 */
@SuppressWarnings("serial")
public class PrivacyLevelsModel extends
        LoadableDetachableModel<List<PrivacyLevelOption>> {

    /**
     * List of the privacy options available.
     */
    private List<PrivacyLevelOption> options;

    /**
     * Constructor for PrivacyLevelsModel.
     * 
     * @param album
     *            Differentiate if the options are for an
     *            {@link Album} or {@link File}.
     * @param cmp
     *            The component which use the privacy level.
     */
    public PrivacyLevelsModel(Album album, Component cmp) {
        this.options = PrivacyLevelOption.getAlbumOptions(cmp);
        Injector.get().inject(this);
    }

    /**
     * Constructor for PrivacyLevelsModel.
     * 
     * @param file
     *            Differentiate if the options are for an
     *            {@link Album} or {@link File}.
     * @param cmp
     *            The component which use the privacy level.
     */
    public PrivacyLevelsModel(File file, Component cmp) {
        this.options = PrivacyLevelOption.getFileOptions(cmp);
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
    
     * @return List<{@link PrivacyLevelOption}> Return a list that
     *         contains the possible options for privacy level. */
    protected List<PrivacyLevelOption> load() {
        return options;
    }
}
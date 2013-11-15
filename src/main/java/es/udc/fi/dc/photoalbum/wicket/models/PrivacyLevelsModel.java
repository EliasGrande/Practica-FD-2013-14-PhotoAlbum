package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;

/**
 */
@SuppressWarnings("serial")
public class PrivacyLevelsModel extends
        LoadableDetachableModel<List<PrivacyLevelOption>> {

    private List<PrivacyLevelOption> options;

    // ya se que no usa el Album y File, solo me sirvo de ellos para
    // diferenciar
    // constructores

    /**
     * Constructor for PrivacyLevelsModel.
     * 
     * @param album
     *            Album
     * @param cmp
     *            Component
     */
    public PrivacyLevelsModel(Album album, Component cmp) {
        this.options = PrivacyLevelOption.getAlbumOptions(cmp);
        Injector.get().inject(this);
    }

    /**
     * Constructor for PrivacyLevelsModel.
     * 
     * @param file
     *            File
     * @param cmp
     *            Component
     */
    public PrivacyLevelsModel(File file, Component cmp) {
        this.options = PrivacyLevelOption.getFileOptions(cmp);
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
     * @return List<PrivacyLevelOption>
     */
    protected List<PrivacyLevelOption> load() {
        return options;
    }
}
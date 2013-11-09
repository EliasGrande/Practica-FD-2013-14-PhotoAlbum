package es.udc.fi.dc.photoalbum.wicket.models;

import org.apache.wicket.Component;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class PrivacyLevelsModel extends
        LoadableDetachableModel<ArrayList<PrivacyLevelOption>> {

    private ArrayList<PrivacyLevelOption> options;

    // ya se que no usa el Album y File, solo me sirvo de ellos para
    // diferenciar
    // constructores

    public PrivacyLevelsModel(Album album, Component cmp) {
        this.options = PrivacyLevelOption.getAlbumOptions(cmp);
        Injector.get().inject(this);
    }

    public PrivacyLevelsModel(File file, Component cmp) {
        this.options = PrivacyLevelOption.getFileOptions(cmp);
        Injector.get().inject(this);
    }

    protected ArrayList<PrivacyLevelOption> load() {
        return options;
    }
}
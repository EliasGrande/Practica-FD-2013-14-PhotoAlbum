package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.util.utils.ComparatorById;

/**
 * Model that return a list of the hottest {@link File files}.
 */
@SuppressWarnings("serial")
public class HottestFilesModel extends
        LoadableDetachableModel<List<File>> {

    /**
     * The id of the {@link User} who wants to view the {@link File
     * files}.
     */
    private int userId;

    /**
     * Constructor for HottestFilesModel.
     * 
     * @param userId
     *            {@link #userId}
     */
    public HottestFilesModel(int userId) {
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * List of the hottest {@link File files}.
     * 
     * @return File list
     */
    @Override
    protected List<File> load() {
        // TODO Use REST search service here, now returns empty list
        ArrayList<File> list = new ArrayList<File>();
        Collections.sort(list, new ComparatorById());
        return list;
    }
}

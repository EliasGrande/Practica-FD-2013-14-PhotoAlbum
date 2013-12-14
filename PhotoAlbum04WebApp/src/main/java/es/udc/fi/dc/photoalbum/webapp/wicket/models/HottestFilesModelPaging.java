package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;

/**
 * Model that return a paginated list of the hottest {@link File
 * files}.
 */
@SuppressWarnings("serial")
public class HottestFilesModelPaging extends
        LoadableDetachableModel<List<File>> {

    /**
     * The id of the {@link User} that want to view the {@link File}s.
     */
    private int userId;

    /**
     * The index of first {@link File}.
     */
    private int first;

    /**
     * The number of {@link File files} to return.
     */
    private int count;

    /**
     * Constructor for HottestFilesModelPaging.
     * 
     * @param userId
     *            {@link #userId}
     * @param first
     *            {@link #first}
     * @param count
     *            {@link #count}
     */
    public HottestFilesModelPaging(int userId, int first, int count) {
        this.userId = userId;
        this.first = first;
        this.count = count;
        Injector.get().inject(this);
    }

    /**
     * Loads a list of the hottest {@link File files}.
     * 
     * @return File list
     */
    @Override
    protected List<File> load() {
        // TODO Use REST search service here, now returns empty list
        return new ArrayList<File>();
    }
}

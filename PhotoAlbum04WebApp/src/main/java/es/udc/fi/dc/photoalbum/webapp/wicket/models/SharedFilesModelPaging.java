package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;

/**
 * Model that return a paginated list {@link File}, which are share.
 */
@SuppressWarnings("serial")
public class SharedFilesModelPaging extends
        LoadableDetachableModel<List<File>> {
    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;
    /**
     * The id of the {@link Album} that contains the share
     * {@link File}.
     */
    private int albumId;
    /**
     * The id of the {@link User} that want to view the {@link File}s.
     */
    private int userId;
    /**
     * The index of first {@link File}.
     */
    private int first;
    /**
     * The number of {@link File}s to return.
     */
    private int count;

    /**
     * Constructor for SharedFilesModelPaging.
     * 
     * @param albumId
     *            {@link #albumId}
     * @param userId
     *            {@link #userId}
     * @param first
     *            {@link #first}
     * @param count
     *            {@link #count}
     */
    public SharedFilesModelPaging(int albumId, int userId, int first,
            int count) {
        this.albumId = albumId;
        this.userId = userId;
        this.first = first;
        this.count = count;
        Injector.get().inject(this);
    }

    /**
    * Method that load a list of share {@link File}s.
    * 
    * 
    * @return List<{@link File}> that contains all the share
    *         {@link File}s for an {@link Album} with this
    *         {@link #id}.
    */
    @Override
    protected List<File> load() {
        return new ArrayList<File>(
                fileService.getAlbumFilesSharedPaging(albumId,
                        userId, first, count));
    }
}

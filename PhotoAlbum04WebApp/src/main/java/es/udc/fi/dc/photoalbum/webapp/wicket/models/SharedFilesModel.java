package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.util.utils.ComparatorById;

/**
 * Model that return a list {@link File}, which are shared.
 */
@SuppressWarnings("serial")
public class SharedFilesModel extends
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
     * Constructor for SharedFilesModel.
     * 
     * @param albumId
     *            {@link #albumId}
     * @param userId
     *            {@link #userId}
     */
    public SharedFilesModel(int albumId, int userId) {
        this.albumId = albumId;
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * Method that return the list of {@link File} that coincides with
     * the parameters.
     * 
     * 
     * @return List<{@link File}> Return a list of files.
     */
    @Override
    protected List<File> load() {
        ArrayList<File> list = new ArrayList<File>(
                fileService.getAlbumFilesShared(albumId, userId));
        Collections.sort(list, new ComparatorById());
        return list;
    }

}

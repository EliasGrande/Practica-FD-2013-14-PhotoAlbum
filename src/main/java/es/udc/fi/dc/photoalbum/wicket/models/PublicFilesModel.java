package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.utils.FileComparator;

/**
 * Model that return a list {@link File}, which are public.
 */
@SuppressWarnings("serial")
public class PublicFilesModel extends
        LoadableDetachableModel<List<File>> {
    /**
     * @use FileService
     */
    @SpringBean
    private FileService fileService;
    /**
     * The id of the {@link Album} that contains the public
     * {@link File}.
     */
    private int albumId;
    /**
     * The id of the {@link User} that want to view the {@link File}s.
     */
    private int userId;

    /**
     * Constructor for PublicFilesModel.
     * 
     * @param albumId
     *            {@link #albumId}
     * @param userId
     *            {@link #userId}
     */
    public PublicFilesModel(int albumId, int userId) {
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
                fileService.getAlbumFilesPublic(albumId, userId));
        Collections.sort(list, new FileComparator());
        return list;
    }

}

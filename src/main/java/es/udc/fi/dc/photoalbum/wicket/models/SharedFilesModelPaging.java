package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;

/**
 */
@SuppressWarnings("serial")
public class SharedFilesModelPaging extends
        LoadableDetachableModel<List<File>> {
    @SpringBean
    private FileService fileService;
    private int albumId;
    private int userId;
    private int first;
    private int count;

    /**
     * Constructor for SharedFilesModelPaging.
     * 
     * @param albumId
     *            int
     * @param userId
     *            int
     * @param first
     *            int
     * @param count
     *            int
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
     * Method load.
     * 
     * @return List<File>
     */
    @Override
    protected List<File> load() {
        return new ArrayList<File>(
                fileService.getAlbumFilesSharedPaging(albumId,
                        userId, first, count));
    }
}

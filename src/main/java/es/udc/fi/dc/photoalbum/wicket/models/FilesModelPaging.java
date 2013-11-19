package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;

/**
 * Model for a list of {@link File} for a specific {@link Album}. This
 * class is a paginated model.
 */
@SuppressWarnings("serial")
public class FilesModelPaging extends
        LoadableDetachableModel<List<File>> {
    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;
    /**
     * The id of the {@link Album} that contains the {@link File}s.
     */
    private int id;
    /**
     * The index of first {@link File}.
     */
    private int first;
    /**
     * The number of {@link File}s to return.
     */
    private int count;

    /**
     * Constructor for FilesModelPaging.
     * 
     * @param id
     *            {@link #id}
     * @param first
     *            {@link #first}
     * @param count
     *            {@link #count}
     */
    public FilesModelPaging(int id, int first, int count) {
        this.id = id;
        this.first = first;
        this.count = count;
        Injector.get().inject(this);
    }

    /**
     * Method that load a list of {@link File}s.
     * 
    
     * @return List<{@link File}> that contains all the files for an
     *         {@link Album} with this {@link #id}. 
     */
    @Override
    protected List<File> load() {
        return new ArrayList<File>(
                fileService.getAlbumFilesOwnPaging(id, first, count));
    }
}

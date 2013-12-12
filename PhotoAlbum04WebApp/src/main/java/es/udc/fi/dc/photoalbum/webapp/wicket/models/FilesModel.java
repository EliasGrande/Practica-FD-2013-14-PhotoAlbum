package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.spring.FileService;

/**
 * Model for a list of {@link File} for an especific {@link Album}.
 */
@SuppressWarnings("serial")
public class FilesModel extends LoadableDetachableModel<List<File>> {

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
     * Constructor for FilesModel.
     * 
     * @param id
     *            {@link #id}
     */
    public FilesModel(int id) {
        this.id = id;
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
    
     * @return List<{@link File}> that contains all the files of the
     *         {@link Album} with {@link #id}. */
    @Override
    protected List<File> load() {
        ArrayList<File> list = new ArrayList<File>(
                fileService.getAlbumFilesOwn(id));
        Collections.sort(list);
        return list;
    }
}

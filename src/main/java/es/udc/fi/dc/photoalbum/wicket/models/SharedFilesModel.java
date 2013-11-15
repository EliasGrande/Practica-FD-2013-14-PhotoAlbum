package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.utils.FileComparator;

/**
 */
@SuppressWarnings("serial")
public class SharedFilesModel extends
        LoadableDetachableModel<List<File>> {

    @SpringBean
    private FileService fileService;
    private int albumId;
    private int userId;

    /**
     * Constructor for SharedFilesModel.
     * 
     * @param albumId
     *            int
     * @param userId
     *            int
     */
    public SharedFilesModel(int albumId, int userId) {
        this.albumId = albumId;
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
     * @return List<File>
     */
    @Override
    protected List<File> load() {
        ArrayList<File> list = new ArrayList<File>(
                fileService.getAlbumFilesShared(albumId, userId));
        Collections.sort(list, new FileComparator());
        return list;
    }

}

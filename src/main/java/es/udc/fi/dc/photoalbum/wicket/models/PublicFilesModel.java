package es.udc.fi.dc.photoalbum.wicket.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.utils.FileComparator;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("serial")
public class PublicFilesModel extends
        LoadableDetachableModel<ArrayList<File>> {

    @SpringBean
    private FileService fileService;
    private int albumId;
    private int userId;

    public PublicFilesModel(int albumId, int userId) {
        this.albumId = albumId;
        this.userId = userId;
        Injector.get().inject(this);
    }

    @Override
    protected ArrayList<File> load() {
        ArrayList<File> list = new ArrayList<File>(
                fileService.getAlbumFilesPublic(albumId, userId));
        Collections.sort(list, new FileComparator());
        return list;
    }

}

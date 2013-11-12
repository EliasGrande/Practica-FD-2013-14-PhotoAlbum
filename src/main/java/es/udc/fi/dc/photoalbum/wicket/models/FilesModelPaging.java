package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;

@SuppressWarnings("serial")
public class FilesModelPaging extends
        LoadableDetachableModel<List<File>> {
    @SpringBean
    private FileService fileService;
    private int id;
    private int first;
    private int count;

    public FilesModelPaging(int id, int first, int count) {
        this.id = id;
        this.first = first;
        this.count = count;
        Injector.get().inject(this);
    }

    @Override
    protected List<File> load() {
        return new ArrayList<File>(
                fileService.getAlbumFilesOwnPaging(id, first, count));
    }
}

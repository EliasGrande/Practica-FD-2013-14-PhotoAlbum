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

@SuppressWarnings("serial")
public class FilesModel extends LoadableDetachableModel<List<File>> {

    @SpringBean
    private FileService fileService;
    private int id;

    public FilesModel(int id) {
        this.id = id;
        Injector.get().inject(this);
    }

    @Override
    protected List<File> load() {
        ArrayList<File> list = new ArrayList<File>(
                fileService.getAlbumFilesOwn(id));
        Collections.sort(list, new FileComparator());
        return list;
    }
}

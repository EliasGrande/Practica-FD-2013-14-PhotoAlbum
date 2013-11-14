package es.udc.fi.dc.photoalbum.wicket;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.wicket.models.PublicFilesModelPaging;

@SuppressWarnings("serial")
public class PublicFileListDataProvider implements
        IDataProvider<File> {
    @SpringBean
    private FileService fileService;
    private int size;
    private int albumId;
    private int userId;

    public void detach() {
    }

    public PublicFileListDataProvider(int size, int albumId,
            int userId) {
        this.size = size;
        this.albumId = albumId;
        this.userId = userId;
        Injector.get().inject(this);
    }

    public Iterator<File> iterator(long first, long count) {
        LoadableDetachableModel<List<File>> ldm = new PublicFilesModelPaging(
                this.albumId, this.userId, (int) first, (int) count);
        return ldm.getObject().iterator();
    }

    public long size() {
        return this.size;
    }

    public IModel<File> model(File object) {
        final Integer id = object.getId();
        return new LoadableDetachableModel<File>() {
            @Override
            protected File load() {
                return fileService.getById(id);
            }
        };
    }

}

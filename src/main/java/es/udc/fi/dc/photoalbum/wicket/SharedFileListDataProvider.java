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
import es.udc.fi.dc.photoalbum.wicket.models.SharedFilesModelPaging;

/**
 */
@SuppressWarnings("serial")
public class SharedFileListDataProvider implements
        IDataProvider<File> {
    @SpringBean
    private FileService fileService;
    private int size;
    private int albumId;
    private int userId;

    /**
     * Method detach.
     * 
     * @see org.apache.wicket.model.IDetachable#detach()
     */
    public void detach() {
    }

    /**
     * Constructor for SharedFileListDataProvider.
     * 
     * @param size
     *            int
     * @param albumId
     *            int
     * @param userId
     *            int
     */
    public SharedFileListDataProvider(int size, int albumId,
            int userId) {
        this.size = size;
        this.albumId = albumId;
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * Method iterator.
     * 
     * @param first
     *            long
     * @param count
     *            long
     * @return Iterator<File>
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(long,
     *      long)
     */
    public Iterator<File> iterator(long first, long count) {
        LoadableDetachableModel<List<File>> ldm = new SharedFilesModelPaging(
                this.albumId, this.userId, (int) first, (int) count);
        return ldm.getObject().iterator();
    }

    /**
     * Method size.
     * 
     * @return long
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
     */
    public long size() {
        return this.size;
    }

    /**
     * Method model.
     * 
     * @param object
     *            File
     * @return IModel<File>
     */
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

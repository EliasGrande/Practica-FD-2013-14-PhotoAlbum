package es.udc.fi.dc.photoalbum.webapp.wicket;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.FilesModelPaging;

/**
 * ListDataProvider for {@link File}.
 * 
 * @see IDataProvider
 */
@SuppressWarnings("serial")
public class FileListDataProvider implements IDataProvider<File> {

    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;

    /**
     * The size of the ListDataProvider.
     */
    private int size;

    /**
     * The id of the {@link File}
     */
    private int id;

    /**
     * Method detach.
     * 
     * @see org.apache.wicket.model.IDetachable#detach()
     */
    public void detach() {
    }

    /**
     * Constructor for FileListDataProvider.
     * 
     * @param size
     *            {@link #size}
     * @param id
     *            {@link #id}
     */
    public FileListDataProvider(int size, int id) {
        this.size = size;
        this.id = id;
        Injector.get().inject(this);
    }

    /**
     * Method iterator for {@link File}.
     * 
     * @param first
     *            The id of the first {@link File} to shown.
     * @param count
     *            The number of {@link File} that contains the
     *            Iterator.
     * @return Iterator<File> Return the iterator.
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(long,
     *      long)
     */
    public Iterator<File> iterator(long first, long count) {
        LoadableDetachableModel<List<File>> ldm = new FilesModelPaging(
                this.id, (int) first, (int) count);
        return ldm.getObject().iterator();
    }

    /**
     * Method size.
     * 
     * @return long The size of the FileListDataProvider.
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
     */
    public long size() {
        return this.size;
    }

    /**
     * Method model that return {@link LoadableDetachableModel} that
     * contains a {@link File}.
     * 
     * @param object
     *            The object to obtains.
     * @return IModel<File> Return a LoadableDetachableModel that
     *         contains an {@link File}.
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

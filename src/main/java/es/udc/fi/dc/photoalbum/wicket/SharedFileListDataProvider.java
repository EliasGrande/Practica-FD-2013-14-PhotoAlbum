package es.udc.fi.dc.photoalbum.wicket;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.wicket.models.SharedFilesModelPaging;

/**
 * ListDataProvider for a shared {@list File}.
 * 
 * @see IDataProvider.
 */
@SuppressWarnings("serial")
public class SharedFileListDataProvider implements
        IDataProvider<File> {
    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;
    /**
     * The size of the {@link IDataProvider}.
     */
    private int size;
    /**
     * The id of the {@link Album} that contains the {@link File}.
     */
    private int albumId;
    /**
     * The id of the {@link User} that want to view the {@link File}.
     */
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
     *            {@link #size}
     * @param albumId
     *            {@link #albumId}
     * @param userId
     *            {@link #userId}
     */
    public SharedFileListDataProvider(int size, int albumId,
            int userId) {
        this.size = size;
        this.albumId = albumId;
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * Method that returns an iterator of {@link File}s.
     * 
     * @param first
     *            The index of the first {@link File}.
     * @param count
     *            The number of elements that contains the iterator.
     * @return Iterator<? extends File> The iterator with the
     *         {@link File}s inside.
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
     * @return long {@link #size}
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
     */
    public long size() {
        return this.size;
    }

    /**
     * Method model.
     * 
     * @param object
     *            The shared {@link File}.
     * @return IModel<{@link File}> The model of the {@link File}.
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

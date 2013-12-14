package es.udc.fi.dc.photoalbum.webapp.wicket;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.HottestFilesModelPaging;

/**
 * ListDataProvider for a hottest {@link File}.
 * 
 * @see IDataProvider
 * @see HottestFilesModelPaging
 */
@SuppressWarnings("serial")
public class HottestFileListDataProvider implements
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
     * The id of the {@link User} who wants to view the {@link File}.
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
     * Constructor for HottestFileListDataProvider.
     * 
     * @param size
     *            {@link #size}
     * @param userId
     *            {@link #userId}
     */
    public HottestFileListDataProvider(int size, int userId) {
        this.size = size;
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * Method that returns an iterator of {@link File files}.
     * 
     * @param first
     *            The index of the first {@link File}.
     * @param count
     *            The number of elements that contains the iterator.
     * @return Iterator of the hottest files
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(long,
     *      long)
     */
    public Iterator<File> iterator(long first, long count) {
        LoadableDetachableModel<List<File>> ldm = new HottestFilesModelPaging(
                this.userId, (int) first, (int) count);
        return ldm.getObject().iterator();
    }

    /**
     * Hottest files list size.
     * 
     * @return long {@link #size}
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
     */
    public long size() {
        return this.size;
    }

    /**
     * Hottest file model.
     * 
     * @param object
     *            A "hottest" {@link File}.
     * @return File model
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

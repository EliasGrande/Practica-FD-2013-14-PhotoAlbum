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
import es.udc.fi.dc.photoalbum.util.dto.FileDto;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.HottestFilesModelPaging;

/**
 * ListDataProvider for a hottest {@link File}.
 * 
 * @see IDataProvider
 * @see HottestFilesModelPaging
 */
@SuppressWarnings("serial")
public class HottestFileListDataProvider implements
        IDataProvider<FileDto> {

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
     */
    public HottestFileListDataProvider(int size) {
        this.size = size;
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
    public Iterator<FileDto> iterator(long first, long count) {
        LoadableDetachableModel<List<FileDto>> ldm = new HottestFilesModelPaging(
                (int) first, (int) count);
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
    public IModel<FileDto> model(FileDto object) {
        final FileDto fileDto = object;
        return new LoadableDetachableModel<FileDto>() {
            @Override
            protected FileDto load() {
                return fileDto;
            }
        };
    }
}

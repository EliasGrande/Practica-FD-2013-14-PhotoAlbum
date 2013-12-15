package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.util.dto.FileDto;

/**
 * Model that return a paginated list of the hottest {@link File
 * files}.
 */
@SuppressWarnings("serial")
public class HottestFilesModelPaging extends
        LoadableDetachableModel<List<FileDto>> {

    /**
     * The index of first {@link File}.
     */
    private int first;

    /**
     * The number of {@link File files} to return.
     */
    private int count;

    /**
     * Constructor for HottestFilesModelPaging.
     * 
     * @param first
     *            {@link #first}
     * @param count
     *            {@link #count}
     */
    public HottestFilesModelPaging(int first, int count) {
        this.first = first;
        this.count = count;
        Injector.get().inject(this);
    }

    /**
     * Loads a list of the hottest {@link File files}.
     * 
     * @return File list
     */
    @Override
    protected List<FileDto> load() {
        // TODO Use REST search service here, now returns empty list
        return new ArrayList<FileDto>();
    }
}

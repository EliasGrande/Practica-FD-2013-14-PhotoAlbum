package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;

/**
 * Model that return a paginated list {@link File}s that contains the
 * tag.
 */
@SuppressWarnings("serial")
public class TagFilesModelPaging extends
        LoadableDetachableModel<List<File>> {
    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;
    /**
     * The tag that contains all the {@link File}s.
     */
    private String tag;
    /**
     * The id of the {@link User} that want to view the {@link File}s.
     */
    private int userId;
    /**
     * The index of first {@link File}.
     */
    private int first;
    /**
     * The number of {@link File}s to return.
     */
    private int count;

    /**
     * Constructor for TagFilesModelPaging.
     * 
     * @param tag
     *            {@link #tag}
     * @param userId
     *            {@link #userId}
     * @param first
     *            {@link #first}
     * @param count
     *            {@link #count}
     */
    public TagFilesModelPaging(String tag, int userId, int first,
            int count) {
        this.tag = tag;
        this.userId = userId;
        this.first = first;
        this.count = count;
        Injector.get().inject(this);
    }

    /**
     * Method that load a list of {@link File}s that contains the
     * {@link #tag}.
     * 
     * 
     * @return List<{@link File}> that contains the {@link File}s that
     *         contains the {@link #tag}.
     */
    @Override
    protected List<File> load() {
        return new ArrayList<File>(fileService.getFilesByTagPaging(
                userId, tag, first, count));
    }
}

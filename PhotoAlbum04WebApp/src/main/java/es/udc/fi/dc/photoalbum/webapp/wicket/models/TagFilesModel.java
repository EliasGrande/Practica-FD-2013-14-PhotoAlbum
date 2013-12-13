package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.util.utils.ComparatorById;

/**
 * Model that return a list {@link File} that contains the tag.
 */
@SuppressWarnings("serial")
public class TagFilesModel extends
        LoadableDetachableModel<List<File>> {
    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;
    /**
     * The tag that contains all the {@link File}s of the list.
     */
    private String tag;
    /**
     * The id of the {@link User} that want to view the {@link File}s.
     */
    private int userId;

    /**
     * Constructor for TagFilesModel.
     * 
     * @param tag
     *            {@link #tag}
     * @param userId
     *            {@link #userId}
     */
    public TagFilesModel(String tag, int userId) {
        this.tag = tag;
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * Method that return the list of {@link File} that contains the
     * {@link #tag}.
     * 
     * 
     * @return List<{@link File}> Return a list of files.
     */
    @Override
    protected List<File> load() {
        ArrayList<File> list = new ArrayList<File>(
                fileService.getFilesByTag(userId, tag));
        Collections.sort(list, new ComparatorById());
        return list;
    }

}

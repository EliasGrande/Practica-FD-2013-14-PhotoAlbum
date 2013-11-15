package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;

/**
 */
@SuppressWarnings("serial")
public class TagFilesModelPaging extends
        LoadableDetachableModel<List<File>> {
    @SpringBean
    private FileService fileService;
    private String tag;
    private int userId;
    private int first;
    private int count;

    /**
     * Constructor for TagFilesModelPaging.
     * 
     * @param tag
     *            String
     * @param userId
     *            int
     * @param first
     *            int
     * @param count
     *            int
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
     * Method load.
     * 
     * @return List<File>
     */
    @Override
    protected List<File> load() {
        return new ArrayList<File>(fileService.getFilesByTagPaging(
                userId, tag, first, count));
    }
}

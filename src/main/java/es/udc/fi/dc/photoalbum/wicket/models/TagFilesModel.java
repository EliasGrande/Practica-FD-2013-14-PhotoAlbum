package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.utils.FileComparator;

/**
 */
@SuppressWarnings("serial")
public class TagFilesModel extends
        LoadableDetachableModel<List<File>> {

    @SpringBean
    private FileService fileService;
    private String tag;
    private int userId;

    /**
     * Constructor for TagFilesModel.
     * 
     * @param tag
     *            String
     * @param userId
     *            int
     */
    public TagFilesModel(String tag, int userId) {
        this.tag = tag;
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
     * @return List<File>
     */
    @Override
    protected List<File> load() {
        ArrayList<File> list = new ArrayList<File>(
                fileService.getFilesByTag(userId, tag));
        Collections.sort(list, new FileComparator());
        return list;
    }

}

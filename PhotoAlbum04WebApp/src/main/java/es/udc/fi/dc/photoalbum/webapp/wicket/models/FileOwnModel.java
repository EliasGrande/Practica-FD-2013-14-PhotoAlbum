package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;

/**
 * Model that return a {@link File}, which an specific {@link Album}'s
 * name.
 */
@SuppressWarnings("serial")
public class FileOwnModel extends LoadableDetachableModel<File> {

    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;
    /**
     * The id of the {@link File}.
     */
    private int id;
    /**
     * The name of the {@link Album} that contains the {@link File}.
     */
    private String name;
    /**
     * The id of the {@link User} owner of the {@link File}.
     */
    private int userId;

    /**
     * Constructor for FileOwnModel.
     * 
     * @param id
     *            {@link #id}
     * @param name
     *            {@link #name}
     * @param userId
     *            {@link #userId}
     */
    public FileOwnModel(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * Method that return the {@link File} that coincides with the
     * parameters.
     * 
     * 
     * @return {@link File}
     */
    protected File load() {
        return fileService.getFileOwn(id, name, userId);
    }
}

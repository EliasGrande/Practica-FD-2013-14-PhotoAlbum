package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;

/**
 * Model that return a {@link File}, wich an especific {@link Album}'s
 * name.
 */
@SuppressWarnings("serial")
public class FileSharedAlbum extends LoadableDetachableModel<File> {
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
     * The {@link User} who want view the {@link File}.
     */
    private int userId;

    /**
     * Constructor for FileSharedAlbum.
     * 
     * @param id
     *            {@link #id}
     * @param name
     *            {@link #name}
     * @param userId
     *            {@link #userId}
     */
    public FileSharedAlbum(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        Injector.get().inject(this);
    }

    /**
     * Method load that return a {@link File} that is shared with the
     * {@link User} {@link #userId}.
     * 
    
     * @return {@link File}. */
    protected File load() {
        return fileService.getFileShared(id, name, userId);
    }
}

package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.spring.FileService;

/**
 * The model for an {@link File}.
 */
@SuppressWarnings("serial")
public class FileModelForNavigate extends
        LoadableDetachableModel<File> {

    /**
     * The id of the {@link File}.
     */
    private Integer id;
    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;

    /**
     * Obtain the id of a {@link File}.
     * 
    
     * @return Integer The id of the {@link File}. */
    public Integer getId() {
        return this.id;
    }

    /**
     * Method setId.
     * 
     * @param id
     *            Assign the id of the {@link File}.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Constructor for FileModelForNavigate.
     * 
     * @param id
     *            The id of the {@link File}.
     */
    public FileModelForNavigate(Integer id) {
        this.id = id;
        Injector.get().inject(this);
    }

    /**
     * Load a {@link File}.
     * 
    
     * @return {@link File} with the {@link #id}. */
    @Override
    protected File load() {
        if (this.id == -1) {
            return null;
        } else {
            System.out.println(this.id);
            return fileService.getById(this.id);
        }

    }
}

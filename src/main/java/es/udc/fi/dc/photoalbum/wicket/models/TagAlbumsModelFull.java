package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.utils.AlbumsComparator;

/**
 * The model for an {@link Album}. This model return an array of
 * albums that contains the tag.
 */
@SuppressWarnings("serial")
public class TagAlbumsModelFull extends
        LoadableDetachableModel<List<Album>> {
    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;
    /**
     * The tag that must contains the {@link Album}..
     */
    private String tag;
    /**
     * The id of the {@link User}.
     */
    private Integer userId;

    /**
     * Constructor for TagAlbumsModelFull.
     * 
     * @param userId
     *            {@link #userId}
     * @param tag
     *            {@link #tag}
     */
    public TagAlbumsModelFull(Integer userId, String tag) {
        this.userId = userId;
        this.tag = tag;
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
     * 
     * @return List<{@link Album}> Return a list of {@link Album}'s
     *         which contains the tag.
     */
    protected List<Album> load() {
        ArrayList<Album> list = new ArrayList<Album>(
                albumService.getAlbumsByTag(userId, tag));
        Collections.sort(list, new AlbumsComparator());
        return list;
    }
}

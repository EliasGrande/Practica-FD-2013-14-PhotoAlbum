package es.udc.fi.dc.photoalbum.wicket;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.wicket.models.TagAlbumsModelFull;

/**
 * ListDataProvider for a {@list Album} that contains the necessary
 * tag.
 * 
 * @see IDataProvider.
 */
@SuppressWarnings("serial")
public class TagAlbumListDataProvider implements IDataProvider<Album> {
    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;
    /**
     * The size of the {@link IDataProvider}.
     */
    private int size;
    /**
     * The id of the {@link User} that want to view the {@link File}.
     */
    private int userId;
    /**
     * The tag that contains all the {@link Album}s in this
     * {@link IDataProvider}
     */
    private String tag;

    /**
     * Constructor for TagAlbumListDataProvider.
     * 
     * @param size
     *            {@link #size}
     * @param userId
     *            {@link #userId}
     * @param tag
     *            {@link #tag}
     */
    public TagAlbumListDataProvider(int size, int userId, String tag) {
        this.userId = userId;
        this.tag = tag;
        this.size = size;
        Injector.get().inject(this);
    }

    /**
     * Method detach.
     * 
     * @see org.apache.wicket.model.IDetachable#detach()
     */
    public void detach() {
    }

    /**
     * Method that returns an iterator of {@link Album}s.
     * 
     * @param first
     *            The index of the first {@link Album}.
     * @param count
     *            The number of elements that contains the iterator.
     * @return Iterator<? extends Album> The iterator with the
     *         {@link Album}s inside.
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(long,
     *      long)
     */
    public Iterator<? extends Album> iterator(long first, long count) {
        LoadableDetachableModel<List<Album>> ldm = new TagAlbumsModelFull(
                userId, tag);
        long toIndex = first + count;
        if (toIndex > ldm.getObject().size()) {
            toIndex = ldm.getObject().size();
        }
        return ldm.getObject().subList((int) first, (int) toIndex)
                .iterator();
    }

    /**
     * Method size.
     * 
     * @return long {@link #size}.
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
     */
    public long size() {
        return this.size;
    }

    /**
     * Method model.
     * 
     * @param object
     *            The {@link Album} that contains the {@link #tag}.
     * @return IModel<{@link Album}> The model of the {@link Album}.
     */
    public IModel<Album> model(Album object) {
        final Integer id = object.getId();
        return new LoadableDetachableModel<Album>() {
            @Override
            protected Album load() {
                return albumService.getById(id);
            }
        };
    }
}

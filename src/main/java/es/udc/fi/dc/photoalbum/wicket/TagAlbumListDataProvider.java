package es.udc.fi.dc.photoalbum.wicket;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.wicket.models.TagAlbumsModelFull;

/**
 */
@SuppressWarnings("serial")
public class TagAlbumListDataProvider implements IDataProvider<Album> {

    @SpringBean
    private AlbumService albumService;
    private int size;
    private int userId;
    private String tag;

    /**
     * Constructor for TagAlbumListDataProvider.
     * 
     * @param size
     *            int
     * @param userId
     *            int
     * @param tag
     *            String
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
     * Method iterator.
     * 
     * @param first
     *            long
     * @param count
     *            long
     * @return Iterator<? extends Album>
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
     * @return long
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
     */
    public long size() {
        return this.size;
    }

    /**
     * Method model.
     * 
     * @param object
     *            Album
     * @return IModel<Album>
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

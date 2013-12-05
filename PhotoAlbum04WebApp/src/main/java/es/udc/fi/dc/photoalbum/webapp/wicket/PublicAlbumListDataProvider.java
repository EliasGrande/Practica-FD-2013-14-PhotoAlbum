package es.udc.fi.dc.photoalbum.webapp.wicket;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.PublicAlbumsModelFull;

/**
 * ListDataProvider for a public {@link Album}.
 * 
 * @see IDataProvider
 */
@SuppressWarnings("serial")
public class PublicAlbumListDataProvider implements
        IDataProvider<Album> {

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
     * Constructor for PublicAlbumListDataProvider.
     * 
     * @param size
     *            {@link #size}
     */
    public PublicAlbumListDataProvider(int size) {
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
        LoadableDetachableModel<List<Album>> ldm = new PublicAlbumsModelFull();
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
     *            The public {@link Album}.
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

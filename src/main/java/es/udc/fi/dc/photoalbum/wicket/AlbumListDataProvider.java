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
import es.udc.fi.dc.photoalbum.wicket.models.AlbumsModelFull;

/**
 * Provides a ListDataProvider of {@link Album}. @see IDataProvider.
 */
@SuppressWarnings("serial")
public class AlbumListDataProvider implements IDataProvider<Album> {

    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;

    /**
     * The size of the ListDataProvider.
     */
    private int size;

    /**
     * Constructor for AlbumListDataProvider.
     * 
     * @param size
     *            The size of the AlbumListDataProvider.
     */
    public AlbumListDataProvider(int size) {
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
     *            The first element of the iterator.
     * @param count
     *            The number of element in the iterator.
     * @return Iterator<? extends Album> Return an iterator of
     *         {@link Album}'s.
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(long,
     *      long)
     */
    public Iterator<? extends Album> iterator(long first, long count) {
        LoadableDetachableModel<List<Album>> ldm = new AlbumsModelFull();
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
     * @return long Return the size of the AlbumListDataProvider.
     * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
     */
    public long size() {
        return this.size;
    }

    /**
     * Method that obtains an {@link Album} of the
     * AlbumListDataProvider, into LoadableDetachableModel.
     * 
     * @param object
     *            The object to obtains.
     * @return IModel<Album> Return a LoadableDetachableModel that
     *         contains an {@link Album}.
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

package es.udc.fi.dc.photoalbum.wicket;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.wicket.models.TagAlbumsModelFull;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class TagAlbumListDataProvider implements IDataProvider<Album> {

    @SpringBean
    private AlbumService albumService;
    private int size;
    private int userId;
    private String tag;

    public TagAlbumListDataProvider(int size, int userId, String tag) {
        this.userId = userId;
        this.tag = tag;
        this.size = size;
        Injector.get().inject(this);
    }

    public void detach() {
    }

    public Iterator<? extends Album> iterator(int first, int count) {
        LoadableDetachableModel<ArrayList<Album>> ldm = new TagAlbumsModelFull(
                userId, tag);
        int toIndex = first + count;
        if (toIndex > ldm.getObject().size()) {
            toIndex = ldm.getObject().size();
        }
        return ldm.getObject().subList(first, toIndex).iterator();
    }

    public int size() {
        return this.size;
    }

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

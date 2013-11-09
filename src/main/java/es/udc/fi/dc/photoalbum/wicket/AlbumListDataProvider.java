package es.udc.fi.dc.photoalbum.wicket;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.wicket.models.AlbumsModelFull;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class AlbumListDataProvider implements IDataProvider<Album> {

	@SpringBean
	private AlbumService albumService;
	private int size;

	public AlbumListDataProvider(int size) {
		this.size = size;
		Injector.get().inject(this);
	}

	public void detach() {
	}

	public Iterator<? extends Album> iterator(long first, long count) {
		LoadableDetachableModel<ArrayList<Album>> ldm = new AlbumsModelFull();
		long toIndex = first + count;
		if (toIndex > ldm.getObject().size()) {
			toIndex = ldm.getObject().size();
		}
		return ldm.getObject().subList((int) first, (int) toIndex).iterator();
	}

	public long size() {
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

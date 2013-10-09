package es.udc.fi.dc.photoalbum.wicket;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.wicket.models.PublicAlbumsModelFull;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class PublicAlbumListDataProvider implements IDataProvider<Album> {

	@SpringBean
	private AlbumService albumService;
	private int size;

	public PublicAlbumListDataProvider(int size) {
		this.size = size;
		Injector.get().inject(this);
	}

	public void detach() {
	}

	public Iterator<? extends Album> iterator(int first, int count) {
		LoadableDetachableModel<ArrayList<Album>> ldm = new PublicAlbumsModelFull();
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

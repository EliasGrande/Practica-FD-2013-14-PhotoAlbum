package es.udc.fi.dc.photoalbum.wicket.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.utils.FileComparator;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("serial")
public class FilesModel extends LoadableDetachableModel<ArrayList<File>> {

	@SpringBean
	private FileService fileService;
	private int id;
	private String minPrivacyLevel;

	public FilesModel(int id) {
		this(id, PrivacyLevel.PRIVATE);
	}

	public FilesModel(int id, String minPrivacyLevel) {
		this.id = id;
		this.minPrivacyLevel = minPrivacyLevel;
		Injector.get().inject(this);
	}

	@Override
	protected ArrayList<File> load() {
		ArrayList<File> list = new ArrayList<File>(
				fileService.getAlbumFiles(id, minPrivacyLevel)
		);
		Collections.sort(list, new FileComparator());
		return list;
	}
}

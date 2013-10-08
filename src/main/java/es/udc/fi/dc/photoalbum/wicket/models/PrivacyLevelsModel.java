package es.udc.fi.dc.photoalbum.wicket.models;

import org.apache.wicket.Component;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class PrivacyLevelsModel extends LoadableDetachableModel<ArrayList<PrivacyLevelOption>> {

	private Component cmp;
	
	public PrivacyLevelsModel(Component cmp) {
		this.cmp = cmp;
		Injector.get().inject(this);
	}

	protected ArrayList<PrivacyLevelOption> load() {
		return PrivacyLevelOption.getOptions(cmp);
	}
}
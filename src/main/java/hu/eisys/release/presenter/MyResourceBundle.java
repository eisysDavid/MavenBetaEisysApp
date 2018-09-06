package hu.eisys.release.presenter;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyResourceBundle implements IBundle {

	ResourceBundle bundle;

	@PostConstruct
	public void init() {
		if (bundle == null) {
			if (!Locale.getDefault().toString().equals("en_US") && !Locale.getDefault().toString().equals("hu_HU")) {
				Locale.setDefault(new Locale("en", "US"));
				bundle = ResourceBundle.getBundle(IConstans.RESOURCE_TEXTS);
			}

			else {
				bundle = ResourceBundle.getBundle(IConstans.RESOURCE_TEXTS);
			}
		}
	}

	@Override
	public String getString(String key) {
		// TODO Auto-generated method stub
		return bundle.getString(key);
	}

}
package shapes;

import base.objects.model.loaders.Loader;
import base.objects.model.Model;

public abstract class Shape extends Model {
	
	public Shape(String name, Loader... loaders) {
		super(name, loaders);
	}
}

import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;

import java.util.ArrayList;

public abstract class CompositeOpenGLObject extends OpenGLObject {
	
	private ArrayList<OpenGLObject> childObjects;
	
	public CompositeOpenGLObject(String name, OpenGLObject ...childObjects) {
		super(name);
		
		this.childObjects = new ArrayList<>();
		
		for (int i = 0; i < childObjects.length; i++) {
			this.childObjects.add(childObjects[i]);
		}
	}
	
	public void addOpenGLObject(OpenGLObject openGLObject) {
		this.childObjects.add(openGLObject);
	}
	
	@Override
	public void init(GL4 gl) {
		for (OpenGLObject openGLObject : this.childObjects)	{
			openGLObject.init(gl);
		}
	}
	
	@Override
	public void destroy(GL4 gl) {
		for (OpenGLObject openGLObject : this.childObjects)	{
			openGLObject.destroy(gl);
		}
	}
}

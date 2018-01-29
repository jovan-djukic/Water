package base.objects;

import com.jogamp.opengl.GL4;

import java.util.ArrayList;

public class CompositeOpenGLObject extends OpenGLObject {
	
	private ArrayList<OpenGLObject> childObjects;
	public CompositeOpenGLObject(String name, OpenGLObject ...childObjects) {
		super(name);
		
		this.childObjects = new ArrayList<>();
		
		for (int i = 0; i < childObjects.length; i++) {
			this.childObjects.add(childObjects[i]);
		}
	}
	
	@Override
	public void init(GL4 gl) {
		for (OpenGLObject childObject : this.childObjects) {
			childObject.init(gl);
		}
	}
	
	@Override
	public void destroy(GL4 gl) {
		for (OpenGLObject childObject : this.childObjects) {
			childObject.destroy(gl);
		}
	}
}

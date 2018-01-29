package base.objects.model;

import base.objects.CompositeOpenGLObject;
import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

public abstract class ModelBase extends CompositeOpenGLObject {
	private Matrix4f transform, resultingTransform;
	
	public ModelBase(String name, OpenGLObject ...openGLObjects) {
		super(name, openGLObjects);
		
		this.transform = new Matrix4f();
		this.transform.identity();
		
		this.resultingTransform = new Matrix4f();
		this.resultingTransform.identity();
	}
	
	public Matrix4f getTransform() {
		return transform;
	}
	
	protected Matrix4f applyTransform(Matrix4f parentTransform) {
		parentTransform.mul(this.transform, this.resultingTransform);
		return this.resultingTransform;
	}
	
	public abstract void render(GL4 gl, Matrix4f parentTransform, int transformUniformLocation);
}

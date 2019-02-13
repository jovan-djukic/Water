package base.objects.renderer;

import base.objects.CompositeOpenGLObject;
import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;

public abstract class RendererBase extends CompositeOpenGLObject {
	
	public RendererBase(String name, OpenGLObject... childObjects) {
		super(name, childObjects);
	}
	
	protected abstract void preRender(GL4 gl);
	protected abstract void postRender(GL4 gl);
	protected abstract void renderImplementation(GL4 gl);
	
	public void render(GL4 gl) {
		this.preRender(gl);
		this.renderImplementation(gl);
		this.postRender(gl);
	}
	
	public abstract void update();
}

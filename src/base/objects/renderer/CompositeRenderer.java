package base.objects.renderer;

import base.objects.CompositeOpenGLObject;
import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;

import java.util.ArrayList;

public abstract class CompositeRenderer extends RendererBase {
	
	private static class Constants {
		public static final String empty = "";
	}
	
	private ArrayList<RendererBase> renderers;
	
	public CompositeRenderer(String name, RendererBase renderers[], OpenGLObject ...openGLObjects) {
		super(name, new CompositeOpenGLObject(Constants.empty, renderers), new CompositeOpenGLObject(Constants.empty, openGLObjects));
		
		this.renderers = new ArrayList<>();
		
		for (int i = 0; i < renderers.length; i++) {
			this.renderers.add(renderers[i]);
		}
	}
	
	@Override
	protected void renderImplementation(GL4 gl) {
		for (RendererBase renderer : this.renderers) {
			renderer.render(gl);
		}
	}
	
	@Override
	public void update() {
		for (RendererBase renderer : this.renderers) {
			renderer.update();
		}
	}
}

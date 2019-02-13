package base.objects.renderer;

import base.Utilities;
import base.objects.CompositeOpenGLObject;
import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeRenderer extends RendererBase {
	private List<RendererBase> rendererBaseList;
	
	public CompositeRenderer(String name, RendererBase rendererBases[], OpenGLObject ...openGLObjects) {
		super(name, Utilities.getInstance().concatenate(OpenGLObject.class, rendererBases, openGLObjects));
		
		this.rendererBaseList = new ArrayList<>();
		
		for (int i = 0; i < rendererBases.length; i++) {
			this.rendererBaseList.add(rendererBases[i]);
		}
	}
	
	@Override
	protected void renderImplementation(GL4 gl) {
		for (RendererBase renderer : this.rendererBaseList) {
			renderer.render(gl);
		}
	}
	
	@Override
	public void update() {
		for (RendererBase renderer : this.rendererBaseList) {
			renderer.update();
		}
	}
}

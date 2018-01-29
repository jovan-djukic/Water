package basic;

import java.util.ArrayList;

import base.objects.renderer.RendererBase;
import base.views.GLView;
import com.jogamp.newt.opengl.GLWindow;

import base.objects.renderer.scene.Scene;

public abstract class BasicView extends GLView {
	
	private BasicCamera basicCamera;
	
	protected abstract Scene getScene(BasicCamera basicCamera);
	
	@Override
	protected ArrayList<RendererBase> getRenderers() {
		ArrayList<RendererBase> rendererBases =  super.getRenderers();
		
		this.basicCamera = new BasicCamera();
		
		rendererBases.add(this.getScene(basicCamera));
		
		return rendererBases;
	}
	
	@Override
	protected void setListeners(GLWindow window) {
		window.addMouseListener(this.basicCamera);
	}
	
	@Override
	protected void onReshape(int width, int height) {
		basicCamera.setAspectRation(width, height);
	}
	
}

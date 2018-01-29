package tests.perlinNoiseTest;

import base.objects.renderer.RendererBase;
import base.views.GLView;
import com.jogamp.newt.opengl.GLWindow;

import java.util.ArrayList;

public class PerlinNoiseTestView extends GLView {
	
	@Override
	protected ArrayList<RendererBase> getRenderers() {
		ArrayList<RendererBase> rendererBases =  super.getRenderers();
		
		rendererBases.add(new PerlinNoiseTestRenderer());
		
		return rendererBases;
	}
	
	@Override
	protected void setListeners(GLWindow window) {
	
	}
	
	@Override
	protected void onReshape(int width, int height) {
	
	}
}

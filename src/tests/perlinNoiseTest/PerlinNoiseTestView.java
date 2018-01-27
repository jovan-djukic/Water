package tests.perlinNoiseTest;

import base.views.GLView;
import base.objects.IRenderable;
import base.objects.IUpdateable;
import base.objects.OpenGLObject;
import com.jogamp.newt.opengl.GLWindow;

import java.util.ArrayList;

public class PerlinNoiseTestView extends GLView {
	
	@Override
	protected void fill(ArrayList<OpenGLObject> openGLObjects, ArrayList<IRenderable> renderables, ArrayList<IUpdateable> updateables) {
		PerlinNoiseTestFrameBuffer perlinNoiseTestFrameBuffer = new PerlinNoiseTestFrameBuffer();
		
		openGLObjects.add(perlinNoiseTestFrameBuffer);
		renderables.add(perlinNoiseTestFrameBuffer);
	}
	
	@Override
	protected void setListeners(GLWindow window) {
	
	}
	
	@Override
	protected void onReshape(int width, int height) {
	
	}
}

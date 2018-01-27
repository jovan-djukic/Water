package basic;

import java.util.ArrayList;

import base.objects.IRenderable;
import base.objects.IUpdateable;
import base.objects.OpenGLObject;
import base.views.GLView;
import com.jogamp.newt.opengl.GLWindow;

import base.objects.scene.Scene;

public abstract class BasicView extends GLView {
	
	protected BasicCamera basicCamera;
	protected Scene       scene;
	
	protected Scene getScene(BasicCamera basicCamera) {
		return new Scene(basicCamera);
	}
	
	@Override
	protected void fill(ArrayList<OpenGLObject> openGLObjects, ArrayList<IRenderable> renderables, ArrayList<IUpdateable> updateables) {
		this.basicCamera = new BasicCamera();
		
		this.scene = this.getScene(this.basicCamera);
		
		openGLObjects.add(this.scene);
		renderables.add(this.scene);
		updateables.add(this.scene);
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

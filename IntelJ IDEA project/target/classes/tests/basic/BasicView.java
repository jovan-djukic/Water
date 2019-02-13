package tests.basic;

import java.util.ArrayList;

import base.objects.renderer.RendererBase;
import base.views.GLView;
import com.jogamp.newt.opengl.GLWindow;

import base.objects.renderer.scene.Scene;

public abstract class BasicView extends GLView {
	public static class Constants {
		public static final float distance          = 5;
		public static final float pitch             = -30;
		public static final float yaw               = 0;
		public static final float roll              = 0;
		public static final float fieldOfView       = (float) Math.toRadians(45);
		public static final float aspectRation      = 1;
		public static final float nearClippingPlane = 0.1f;
		public static final float farClippingPlane  = 1000;
		public static final float zStep             = 0.5f;
	}
	
	private BasicCamera basicCamera;
	
	protected abstract Scene getScene(BasicCamera basicCamera);
	
	@Override
	protected ArrayList<RendererBase> getRenderers() {
		ArrayList<RendererBase> rendererBases =  super.getRenderers();
		
		this.basicCamera = new BasicCamera(
			Constants.distance,
			Constants.pitch,
			Constants.yaw,
			Constants.roll,
			Constants.fieldOfView,
			Constants.aspectRation,
			Constants.nearClippingPlane,
			Constants.farClippingPlane,
			Constants.zStep
		);
		
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

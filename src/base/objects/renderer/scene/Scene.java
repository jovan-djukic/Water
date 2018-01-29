package base.objects.renderer.scene;

import base.objects.OpenGLObject;
import base.objects.model.ModelBase;
import base.objects.renderer.Renderer;
import base.objects.renderer.scene.camera.Camera;

import com.jogamp.opengl.GL4;

public class Scene extends Renderer {
	private static class Constants {
		public static final int    transformArrayLength = 16;
		public static final String scene                = "scene-";
		public static final String preRenderTag         = Constants.scene + "preRender";
	}
	
	private Camera   camera;
	private float[]  transformArray;
	private SceneShaderProgram sceneShaderProgram;
	
	public Scene(String name, SceneShaderProgram sceneShaderProgram, ModelBase modelBases[],  Camera camera, OpenGLObject ...openGLObjects) {
		super(name, sceneShaderProgram, modelBases, openGLObjects);
		
		this.camera = camera;
		this.transformArray = new float[Constants.transformArrayLength];
		this.sceneShaderProgram = sceneShaderProgram;
	}
	
	public void update() {
		super.update();
		camera.update();
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		camera.getView().get(this.transformArray);
		gl.glUniformMatrix4fv(this.sceneShaderProgram.getViewMatrixUniformPosition(), 1, false, this.transformArray, 0);
		
		camera.getProjection().get(this.transformArray);
		gl.glUniformMatrix4fv(this.sceneShaderProgram.getProjectionMatrixUniformPosition(), 1, false, this.transformArray, 0);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
}

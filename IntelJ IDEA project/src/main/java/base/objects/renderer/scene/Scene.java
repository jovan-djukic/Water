package base.objects.renderer.scene;

import base.Utilities;
import base.objects.OpenGLObject;
import base.objects.renderer.Renderer;
import base.objects.renderer.scene.camera.Camera;

import base.objects.renderer.scene.sceneModel.SceneModel;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class Scene extends Renderer {
	private static class Constants {
		public static final int    transformArrayLength    = 16;
		public static final String scene                   = "scene-";
		public static final String preRenderTag            = Constants.scene + "preRender";
		public static final String renderImplementationTag = Constants.scene + "renderImplementation";
	}
	
	private Camera             camera;
	private Matrix4f           transform;
	private float[]            transformArray;
	private SceneShaderProgram sceneShaderProgram;
	private List<SceneModel>   sceneModelList;
	
	public Scene(String name, SceneShaderProgram sceneShaderProgram, Camera camera, SceneModel sceneModels[], OpenGLObject ...openGLObjects) {
		super(name, sceneShaderProgram, sceneModels, openGLObjects);
		
		this.camera = camera;
		this.transform = new Matrix4f();
		this.transform.identity();
		this.transformArray = new float[Constants.transformArrayLength];
		this.sceneShaderProgram = sceneShaderProgram;
		
		this.sceneModelList = new ArrayList<>();
		for (int i = 0; i < sceneModels.length; i++) {
			this.sceneModelList.add(sceneModels[i]);
		}
	}
	
	protected Camera getCamera() {
		return this.camera;
	}
	
	@Override
	public void update() {
		camera.update();
	}
	
	protected void setViewMatrixUniform(GL4 gl) {
		camera.getView().get(this.transformArray);
		this.sceneShaderProgram.setViewMatrixUniform(gl, this.transformArray);
	}
	
	
	protected void setProjectionMatrixUniform(GL4 gl) {
		camera.getProjection().get(this.transformArray);
		this.sceneShaderProgram.setProjectionMatrixUniform(gl, this.transformArray);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.setViewMatrixUniform(gl);
		
		this.setProjectionMatrixUniform(gl);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	public void renderImplementation(GL4 gl) {
		for (SceneModel sceneModel : this.sceneModelList) {
			sceneModel.render(gl, this.transform, this.sceneShaderProgram);
		}
		this.checkForErrors(gl, Constants.renderImplementationTag);
	}
}

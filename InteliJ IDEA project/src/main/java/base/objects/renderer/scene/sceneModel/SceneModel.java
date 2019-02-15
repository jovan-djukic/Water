package base.objects.renderer.scene.sceneModel;

import base.Utilities;
import base.objects.model.Model;
import base.objects.model.loaders.Loader;
import base.objects.model.loaders.VerticesLoader;
import base.objects.renderer.scene.SceneShaderProgram;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

public abstract class SceneModel extends Model {
	protected static class Constants {
		public static final String sceneModel      = "sceneModel";
		public static final String renderTag       = Constants.sceneModel + "-render";
		public static final int    transformLength = 16;
	}
	
	private Matrix4f transform, resultingTransform;
	private float transformArray[];
	
	public SceneModel(String name, VerticesLoader verticesLoader, Loader... loaders) {
		super(name, Utilities.getInstance().concatenate(Loader.class, verticesLoader, loaders));
		
		this.transform = new Matrix4f();
		this.transform.identity();
		
		this.resultingTransform = new Matrix4f();
		this.resultingTransform.identity();
		
		this.transformArray = new float[Constants.transformLength];
	}
	
	public Matrix4f getTransform() {
		return transform;
	}
	
	public void render(GL4 gl, Matrix4f parentTransform, SceneShaderProgram sceneShaderProgram) {
		if (parentTransform != null && sceneShaderProgram != null) {
			parentTransform.mul(this.transform, this.resultingTransform);
			this.resultingTransform.get(this.transformArray);
			sceneShaderProgram.setTransformMatrixUniformName(gl, this.transformArray);
			
			this.checkForErrors(gl, Constants.renderTag);
		}
		
		super.render(gl);
	}
}

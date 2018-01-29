package tests.basicMeshTest;
import base.objects.OpenGLObject;
import base.objects.model.ModelBase;
import base.objects.renderer.scene.Scene;
import base.objects.renderer.scene.SceneShaderProgram;
import base.objects.renderer.scene.camera.Camera;
import tests.basic.basicSceneShaderProgram.BasicSceneShaderProgram;
import tests.basic.BasicCamera;
import tests.basic.BasicView;
import com.jogamp.opengl.GL4;
import shapes.Mesh;

public class BasicMeshTestView extends BasicView {
	private static class Constants {
		public static final String meshName  = "mesh";
		public static final String sceneName = "scene";
		public static final float  x         = -1;
		public static final float  z         = +1;
		public static final float  width     = 2;
		public static final float  height    = 2;
		public static final int    rows      = 20;
		public static final int    columns   = 20;
	}
	
	private static class BasicMeshScene extends Scene {
		
		public BasicMeshScene(String name, SceneShaderProgram sceneShaderProgram, ModelBase[] modelBases, Camera camera, OpenGLObject... openGLObjects) {
			super(name, sceneShaderProgram, modelBases, camera, openGLObjects);
		}
		
		@Override
		protected void preRender(GL4 gl) {
			super.preRender(gl);
			
			gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_LINE);
		}
	}
	
	@Override
	protected Scene getScene(BasicCamera basicCamera) {
		BasicSceneShaderProgram basicSceneShaderProgram = new BasicSceneShaderProgram();
		Mesh mesh = new Mesh(Constants.meshName, Constants.x, Constants.z, Constants.width, Constants.height, Constants.rows, Constants.columns, basicSceneShaderProgram.getVertexPositionAttributeLocation());
		
		return new BasicMeshScene(
			Constants.sceneName, basicSceneShaderProgram,
			new ModelBase[] {
					mesh
			}, basicCamera
		);
	}
	
}

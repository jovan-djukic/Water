package shapes.skybox.shaderProgram;

import base.objects.scene.SceneShaderProgram;
import base.objects.scene.camera.Camera;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class SkyboxShaderProgram extends SceneShaderProgram {
	
	private static class Constants {
		public static final String name                 = "skyboxShaderProgram";
		public static final String vertexShaderName     = "skyboxVertexShader";
		public static final String fragmentShaderName   = "skyboxFragmentShader";
		public static final String vertexShaderSource   = "vertexShader.glsl";
		public static final String fragmentShaderSource = "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String projection = "projection";
		public static final String view       = "view";
		public static final String cubeMap    = "cubeMap";
	}
	
	protected float array[];
	protected Matrix4f temp;
	
	public SkyboxShaderProgram(Camera camera) {
		super(Constants.name, camera);
		
		this.array = new float[SceneShaderProgram.Constants.transformArrayLength];
		this.temp = new Matrix4f();
	}
	
	@Override
	protected ArrayList<String> getUniforms() {
		ArrayList<String> uniforms = super.getUniforms();
		
		uniforms.add(Uniforms.projection);
		uniforms.add(Uniforms.view);
		uniforms.add(Uniforms.cubeMap);
		
		return uniforms;
	}
	
	public void setProjection(GL4 gl, Matrix4f projection) {
		int projectionLocation = super.getUniformLocation(Uniforms.projection);
		projection.get(this.array);
		gl.glUniformMatrix4fv(projectionLocation, 1, false, this.array, 0);
	}
	
	public void setView(GL4 gl, Matrix4f view) {
		view.m30(0)
				.m31(0)
				.m32(0);
		int viewLocation = super.getUniformLocation(Uniforms.view);
		view.get(this.array);
		gl.glUniformMatrix4fv(viewLocation, 1, false, this.array, 0);
	}
	
	public void setCubeMap(GL4 gl, int textureUnit) {
		int cubeMapLocation = super.getUniformLocation(Uniforms.cubeMap);
		gl.glUniform1i(cubeMapLocation, textureUnit);
	}
	
	@Override
	public void applyTransform(GL4 gl, Matrix4f transform) {
		this.setProjection(gl, camera.getProjection());
		camera.getView().get(this.temp);
		this.setView(gl, this.temp);
	}
	
	@Override
	protected String getVertexShaderSource() throws Exception {
		return new String(this.getClass().getResourceAsStream(Constants.vertexShaderSource).readAllBytes());
	}
	
	@Override
	protected String getVertexShaderName() throws Exception {
		return Constants.vertexShaderName;
	}
	
	@Override
	protected String getFragmentShaderSource() throws Exception {
		return new String(this.getClass().getResourceAsStream(Constants.fragmentShaderSource).readAllBytes());
	}
	
	@Override
	protected String getFragmentShaderName() throws Exception {
		return Constants.fragmentShaderName;
	}
	
	@Override
	public int getVertexAttributePosition() {
		return 0;
	}
}

package basic.basicSceneShaderProgram;

import base.objects.scene.SceneShaderProgram;
import base.objects.scene.camera.Camera;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class BasicSceneShaderProgram extends SceneShaderProgram {
	private static class Constants {
		public static final String	shaderProgramName		= "basicShaderProgram";
		public static final String	vertexShaderName		= "basicVertexShader";
		public static final String	fragmentShaderName		= "basicFragmentShader";
		public static final String	vertexShaderSource		= "vertexShader.glsl";
		public static final String	fragmentShaderSource	= "fragmentShader.glsl";
	}
	
	public BasicSceneShaderProgram(Camera camera) {
		super(Constants.shaderProgramName, camera);
	}
	
	public static class Uniforms {
		public static final String transform    = "transform";
	}
	
	@Override
	public int getVertexAttributePosition() {
		return 0;
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
	public void applyTransform(GL4 gl, Matrix4f transform) {
		super.applyTransform(gl, transform);
		int transformLocation = super.getUniformLocation(Uniforms.transform);
		
		gl.glUniformMatrix4fv(transformLocation, 1, false, super.transformArray, 0);
	}
	
	@Override
	protected ArrayList<String> getUniforms() {
		ArrayList<String> uniforms = super.getUniforms();
		
		uniforms.add(Uniforms.transform);
		
		return uniforms;
	}
}

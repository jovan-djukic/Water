package water.basicWater.basicTerrain.basicTerrainShaderProgram;

import base.objects.scene.SceneShaderProgram;
import base.objects.scene.camera.Camera;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class BasicTerrainShaderProgram extends SceneShaderProgram {
	private static class Constants {
		public static final String	shaderProgramName		= "basicTerrainShaderProgram";
		public static final String	vertexShaderName		= "basicTerrainVertexShader";
		public static final String	fragmentShaderName		= "basicTerrainFragmentShader";
		public static final String	vertexShaderSource		= "vertexShader.glsl";
		public static final String	fragmentShaderSource	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String transform    = "transform";
		public static final String grassTexture = "grassTexture";
		public static final String sandTexture  = "sandTexture";
	}
	
	public BasicTerrainShaderProgram(Camera camera) {
		super(Constants.shaderProgramName, camera);
	}
	
	@Override
	public int getVertexAttributePosition() {
		return 0;
	}
	
	@Override
	public int getTextureCoordinateAttributePosition() {
		return 1;
	}
	
	@Override
	public boolean hasTextureCoordinates() {
		return true;
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
	
	public void setGrassTexture(GL4 gl, int textureUnit) {
		int grassTextureLocation = super.getUniformLocation(Uniforms.grassTexture);
		gl.glUniform1i(grassTextureLocation, textureUnit);
	}
	
	public void setSandTexture(GL4 gl, int textureUnit) {
		int sandTextureLocation = this.getUniformLocation(Uniforms.sandTexture);
		gl.glUniform1i(sandTextureLocation, textureUnit);
	}
	
	@Override
	protected ArrayList<String> getUniforms() {
		ArrayList<String> uniforms = super.getUniforms();
		
		uniforms.add(Uniforms.transform);
		uniforms.add(Uniforms.grassTexture);
		uniforms.add(Uniforms.sandTexture);
		
		return uniforms;
	}
}

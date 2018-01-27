package tests.renderTextureTest.renderTextureShaderProgram;

import java.util.ArrayList;

import org.joml.Matrix4f;

import com.jogamp.opengl.GL4;

import base.objects.scene.camera.Camera;
import water.heightMapAndNormalMap.HeightMapAndNormalMapGenerator;
import water.heightMapAndNormalMap.HeightMapAndNormalMapSurfaceMeshShaderProgram;

public class RenderTextureShaderProgram extends HeightMapAndNormalMapSurfaceMeshShaderProgram {
	
	
	private static class Constants {
		public static final String	shaderProgramName		= "renderTextureTestShaderProgram";
		public static final String	vertexShaderName		= "renderTextureTestVertexShader";
		public static final String	fragmentShaderName		= "renderTextureTestFragmentShader";
		
		public static final String	vertexShaderSource		= "vertexShader.glsl";
		public static final String	fragmentShaderSource	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String	transform			= "transform";
		public static final String	heightMapTexture	= "heightMapTexture";
		public static final String	normalMapTexture	= "normalMapTexture";
	}
	
	public RenderTextureShaderProgram(Camera camera, HeightMapAndNormalMapGenerator heightMapAndNormalMapGenerator) {
		super(Constants.shaderProgramName, camera, heightMapAndNormalMapGenerator);
	}
	
	@Override
	public ArrayList<String> getUniforms() {
		ArrayList<String> uniforms = super.getUniforms();
		
		uniforms.add(Uniforms.transform);
		uniforms.add(Uniforms.heightMapTexture);
		uniforms.add(Uniforms.normalMapTexture);
		
		return uniforms;
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
	
	@Override
	public int getTextureCoordinateAttributePosition() {
		return 1;
	}
	
	@Override
	public void applyTransform(GL4 gl, Matrix4f transform) {
		super.applyTransform(gl, transform);
		
		int transformLocation = super.getUniformLocation(Uniforms.transform);
		gl.glUniformMatrix4fv(transformLocation, 1, false, super.transformArray, 0);
	}
	
	@Override
	protected void setupHeightMap(GL4 gl, int heightMapTextureID) {
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		gl.glBindTexture(GL4.GL_TEXTURE_2D, heightMapTextureID);
		gl.glUniform1i(super.getUniformLocation(Uniforms.heightMapTexture), 0);
	}
	
	@Override
	protected void setupNormalMap(GL4 gl, int normalMapTextureID) {
		gl.glActiveTexture(GL4.GL_TEXTURE1);
		gl.glBindTexture(GL4.GL_TEXTURE_2D, normalMapTextureID);
		gl.glUniform1i(super.getUniformLocation(Uniforms.normalMapTexture), 1);
	}
}

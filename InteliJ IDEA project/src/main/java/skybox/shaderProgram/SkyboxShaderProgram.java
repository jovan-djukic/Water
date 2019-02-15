package skybox.shaderProgram;

import base.glsl.Shader;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.glsl.uniformSetter.Uniform1i;
import base.objects.renderer.scene.SceneShaderProgram;
import com.jogamp.opengl.GL4;

import java.io.IOException;

public class SkyboxShaderProgram extends SceneShaderProgram {
	private static class Constants {
		public static final String name                 = "skyboxShaderProgram";
		public static final String vertexShaderName     = "skyboxVertexShader";
		public static final String fragmentShaderName   = "skyboxFragmentShader";
		public static final String vertexShaderSource   = "vertexShader.glsl";
		public static final String fragmentShaderSource = "fragmentShader.glsl";
		
		public static final int m30 = 12;
		public static final int m31 = 13;
		public static final int m32 = 14;
	}
	
	public static class Uniforms {
		public static final String projection = "projection";
		public static final String view       = "view";
		public static final String transform  = "transform";
		public static final String cubeMap    = "cubeMap";
		
		public static final String[] uniforms = {
				Uniforms.projection,
				Uniforms.view,
				Uniforms.transform,
				Uniforms.cubeMap
		};
	}
	
	public SkyboxShaderProgram() {
		super(
				Constants.name,
				Uniforms.uniforms,
				new VertexShader(Constants.vertexShaderName, Shader.getShaderSource(SkyboxShaderProgram.class, Constants.vertexShaderSource)),
				new FragmentShader(Constants.fragmentShaderName, Shader.getShaderSource(SkyboxShaderProgram.class, Constants.fragmentShaderSource))
		);
	}
	
	@Override
	protected String getViewMatrixUniformName() {
		return Uniforms.view;
	}
	
	@Override
	public void setViewMatrixUniform(GL4 gl, float viewMatrix[]) {
		viewMatrix[Constants.m30] = 0;
		viewMatrix[Constants.m31] = 0;
		viewMatrix[Constants.m32] = 0;
		super.setViewMatrixUniform(gl, viewMatrix);
	}
	
	@Override
	protected String getProjectionMatrixUniformName() {
		return Uniforms.projection;
	}
	
	@Override
	protected String getTransformMatrixUniformName() {
		return Uniforms.transform;
	}
	
	@Override
	public int getVertexAttributeLocation() {
		return 0;
	}
	
	public void setCubeMapTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.cubeMap), textureUnit));
	}
}

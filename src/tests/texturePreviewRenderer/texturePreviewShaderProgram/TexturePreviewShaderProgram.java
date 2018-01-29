package tests.texturePreviewRenderer.texturePreviewShaderProgram;

import base.glsl.Shader;
import base.glsl.ShaderProgram;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;

public abstract class TexturePreviewShaderProgram extends ShaderProgram {
	private static class Constants {
		public static final String shaderProgramName                = "texturePreviewShaderProgram";
		public static final String vertexShaderName                 = "texturePreviewVertexShader";
		public static final String fragmentShaderName               = "texturePreviewFragmentShader";
		public static final String vertexShaderSourceFileName       = "vertexShader.glsl";
	}
	
	public static class Uniforms {
		public static final String sampler    = "sampler";
		public static final String uniforms[] = {
				Uniforms.sampler
		};
	}
	
	private static String[] concatenate(String array0[], String array1[]) {
		String array[] = new String[array0.length + array1.length];
		
		for (int i = 0; i < array.length; i++) {
			if (i < array0.length) {
				array[i] = array0[i];
			} else {
				array[i] = array1[i - array0.length];
			}
		}
		
		return array;
	}
	
	public TexturePreviewShaderProgram(String fragmentShaderSource, String ...additionalUniforms) {
		super(
			Constants.shaderProgramName,
			TexturePreviewShaderProgram.concatenate(Uniforms.uniforms, additionalUniforms),
			new VertexShader(Constants.vertexShaderName, Shader.getShaderSource(TexturePreviewShaderProgram.class, Constants.vertexShaderSourceFileName)),
			new FragmentShader(Constants.fragmentShaderName, fragmentShaderSource)
		);
	}
	
	public int getSampleUniformLocation() {
		return super.getUniformLocation(Uniforms.sampler);
	}
}

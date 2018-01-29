package perlinNoise.perlinNoiseTextureGenerator.shaderProgram;

import base.glsl.Shader;
import base.glsl.ShaderProgram;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;

public class PerlinNoiseShaderProgram extends ShaderProgram {
	private static class Constants {
		public static final String name                 = "perlinNoiseShaderProgram";
		public static final String vertexShaderName     = "perlinNoiseVertexShader";
		public static final String vertexShaderSource   = "vertexShader.glsl";
		public static final String fragmentShaderName   = "perlinNoiseFragmentShader";
		public static final String fragmentShaderSource = "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String permutation       = "permutation";
		public static final int    permutationLength = 512;
		public static final String numberOfOctaves   = "numberOfOctaves";
		public static final String persistence       = "persistence";
		public static final String scaleX            = "scaleX";
		public static final String scaleY            = "scaleY";
		
		public static final String[] uniforms = {
			Uniforms.permutation,
			Uniforms.numberOfOctaves,
			Uniforms.persistence,
			Uniforms.scaleX,
			Uniforms.scaleY
		};
	}
	
	public PerlinNoiseShaderProgram() {
		super(
				Constants.name,
				Uniforms.uniforms,
				new VertexShader(Constants.vertexShaderName, Shader.getShaderSource(PerlinNoiseShaderProgram.class, Constants.vertexShaderSource)),
				new FragmentShader(Constants.fragmentShaderName, Shader.getShaderSource(PerlinNoiseShaderProgram.class, Constants.fragmentShaderSource))
		);
	}
	
	public int getPermutationLocation() {
		return super.getUniformLocation(Uniforms.permutation);
	}
	
	public int getNumberOfOctavesLocation() {
		return super.getUniformLocation(Uniforms.numberOfOctaves);
	}
	
	public int getPersistenceLocation() {
		return super.getUniformLocation(Uniforms.persistence);
	}
	
	public int getScaleXLocation() {
		return super.getUniformLocation(Uniforms.scaleX);
	}
	public int getScaleYLocation() {
		return super.getUniformLocation(Uniforms.scaleY);
	}
}

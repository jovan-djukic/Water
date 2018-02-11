package perlinNoise.perlinNoiseTextureGenerator.shaderProgram;

import base.glsl.Shader;
import base.glsl.ShaderProgram;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.glsl.uniformSetter.Uniform1f;
import base.glsl.uniformSetter.Uniform1i;
import base.glsl.uniformSetter.Uniform1iv;
import com.jogamp.opengl.GL4;

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
	
	public void setPermutationUniform(GL4 gl, int[] permutation) {
		super.setUniform(gl, new Uniform1iv(super.getUniformLocation(Uniforms.permutation), permutation.length, permutation, 0));
	}
	
	public void setNumberOfOctavesUniform(GL4 gl, int numberOfOctaves) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.numberOfOctaves), numberOfOctaves));
	}
	
	public void setPersistenceUniform(GL4 gl, float persistence) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.persistence), persistence));
	}
	
	public void setScaleXUniform(GL4 gl, float scaleX) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.scaleX), scaleX));
	}
	
	public void setScaleYUniform(GL4 gl, float scaleY) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.scaleY), scaleY));
	}
}

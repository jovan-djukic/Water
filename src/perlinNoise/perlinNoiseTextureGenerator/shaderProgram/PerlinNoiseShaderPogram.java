package perlinNoise.perlinNoiseTextureGenerator.shaderProgram;

import base.glsl.ShaderProgramWrapper;

import java.util.ArrayList;

public class PerlinNoiseShaderPogram extends ShaderProgramWrapper {
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
	}
	
	public PerlinNoiseShaderPogram() {
		super(Constants.name);
	}
	
	@Override
	protected ArrayList<String> getUniforms() {
		ArrayList<String> uniforms = super.getUniforms();
		
		uniforms.add(Uniforms.permutation);
		uniforms.add(Uniforms.numberOfOctaves);
		uniforms.add(Uniforms.persistence);
		uniforms.add(Uniforms.scaleX);
		uniforms.add(Uniforms.scaleY);
		
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
}

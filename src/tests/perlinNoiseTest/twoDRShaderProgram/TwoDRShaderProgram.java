package tests.perlinNoiseTest.twoDRShaderProgram;

import base.glsl.ShaderProgramWrapper;

import java.util.ArrayList;

public class TwoDRShaderProgram extends ShaderProgramWrapper {
	private static class Constants {
		public static final String	shaderProgramName			= "twoDRShaderProgram";
		public static final String	vertexShaderName			= "twoDRVertexShader";
		public static final String	fragmentShaderName			= "twoDRFragmentShader";
		
		public static final String	vertexShaderSourceFile		= "vertexShader.glsl";
		public static final String	fragmentShaderSourceFile	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String	sampler	= "sampler";
	}
	
	public TwoDRShaderProgram() {
		super(Constants.shaderProgramName);
	}
	
	@Override
	protected String getVertexShaderSource() throws Exception {
		return new String(this.getClass().getResourceAsStream(Constants.vertexShaderSourceFile).readAllBytes());
	}
	
	@Override
	protected String getVertexShaderName() throws Exception {
		return Constants.vertexShaderName;
	}
	
	@Override
	protected String getFragmentShaderSource() throws Exception {
		return new String(this.getClass().getResourceAsStream(Constants.fragmentShaderSourceFile).readAllBytes());
	}
	
	@Override
	protected String getFragmentShaderName() throws Exception {
		return Constants.fragmentShaderName;
	}
	
	@Override
	protected ArrayList<String> getUniforms() {
		ArrayList<String> uniforms = super.getUniforms();
		
		uniforms.add(Uniforms.sampler);
		
		return uniforms;
	}
}

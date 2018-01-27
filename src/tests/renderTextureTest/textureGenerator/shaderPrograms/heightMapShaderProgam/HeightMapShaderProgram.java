package tests.renderTextureTest.textureGenerator.shaderPrograms.heightMapShaderProgam;

import java.util.ArrayList;

import base.glsl.ShaderProgramWrapper;

public class HeightMapShaderProgram extends ShaderProgramWrapper {
	private static class Constants {
		public static final String	shaderProgramName			= "textureGeneratorHeightMapShaderProgram";
		public static final String	vertexShaderName			= "textureGeneratorHeightMapVertexShader";
		public static final String	fragmentShaderName			= "textureGeneratorHeightMapFragmentShader";
		
		public static final String	vertexShaderSourceFile		= "vertexShader.glsl";
		public static final String	fragmentShaderSourceFile	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String	width	= "width";
		public static final String	height	= "height";
	}
	
	public HeightMapShaderProgram() {
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
		
		uniforms.add(Uniforms.width);
		uniforms.add(Uniforms.height);
		
		return uniforms;
	}
}

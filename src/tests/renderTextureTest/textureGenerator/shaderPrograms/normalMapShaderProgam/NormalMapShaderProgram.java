package tests.renderTextureTest.textureGenerator.shaderPrograms.normalMapShaderProgam;

import java.util.ArrayList;

import base.glsl.ShaderProgramWrapper;

public class NormalMapShaderProgram extends ShaderProgramWrapper {
	private static class Constants {
		public static final String	shaderProgramName			= "textureGeneratorNormalMapShaderProgram";
		public static final String	vertexShaderName			= "textureGeneratorNormalMapVertexShader";
		public static final String	fragmentShaderName			= "textureGeneratorNormalMapFragmentShader";
		
		public static final String	vertexShaderSourceFile		= "vertexShader.glsl";
		public static final String	fragmentShaderSourceFile	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String	width	= "width";
		public static final String	height	= "height";
	}
	
	public NormalMapShaderProgram() {
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

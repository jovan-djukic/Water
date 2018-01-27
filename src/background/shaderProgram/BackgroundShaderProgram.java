package background.shaderProgram;

import base.glsl.ShaderProgramWrapper;

import java.util.ArrayList;

public class BackgroundShaderProgram extends ShaderProgramWrapper {
	private static class Constants {
		public static final String name                 = "backgroundShaderProgram";
		public static final String vertexShaderName     = "backgroundVertexShader";
		public static final String fragmentShaderName   = "backgroundFragmentShader";
		public static final String vertexShaderSource   = "vertexShader.glsl";
		public static final String fragmentShaderSource = "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String background = "background";
	}
	
	public BackgroundShaderProgram() {
		super(Constants.name);
	}
	
	@Override
	protected ArrayList<String> getUniforms() {
		ArrayList<String> uniforms = super.getUniforms();
		
		uniforms.add(Uniforms.background);
		
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

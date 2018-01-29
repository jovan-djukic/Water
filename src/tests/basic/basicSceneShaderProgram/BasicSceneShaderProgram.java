package tests.basic.basicSceneShaderProgram;

import base.glsl.Shader;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.objects.renderer.scene.SceneShaderProgram;

public class BasicSceneShaderProgram extends SceneShaderProgram {
	
	private static class Constants {
		public static final String name                 = "basicShaderProgram";
		public static final String vertexShaderName     = "basicVertexShader";
		public static final String fragmentShaderName   = "basicFragmentShader";
		public static final String vertexShaderSource   = "vertexShader.glsl";
		public static final String fragmentShaderSource = "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String projection = "projection";
		public static final String view       = "view";
		public static final String uniforms[] = {
				Uniforms.projection,
				Uniforms.view
		};
	}
	
	public BasicSceneShaderProgram() {
		super(
				Constants.name,
				Uniforms.uniforms,
				new VertexShader(Constants.vertexShaderName, Shader.getShaderSource(BasicSceneShaderProgram.class, Constants.vertexShaderSource)),
				new FragmentShader(Constants.fragmentShaderName, Shader.getShaderSource(BasicSceneShaderProgram.class, Constants.fragmentShaderSource))
		);
	}
	
	@Override
	public int getViewMatrixUniformPosition() {
		return super.getUniformLocation(Uniforms.view);
	}
	
	@Override
	public int getProjectionMatrixUniformPosition() {
		return super.getUniformLocation(Uniforms.projection);
	}
	
	public int getVertexPositionAttributeLocation() {
		return 0;
	}
}

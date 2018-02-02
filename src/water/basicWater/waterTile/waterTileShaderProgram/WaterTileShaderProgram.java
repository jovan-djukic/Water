package water.basicWater.waterTile.waterTileShaderProgram;

import base.glsl.Shader;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.objects.renderer.scene.SceneShaderProgram;

public class WaterTileShaderProgram extends SceneShaderProgram {
	private static class Constants {
		public static final String	shaderProgramName		= "waterTileShaderProgram";
		public static final String	vertexShaderName		= "waterTielVertexShader";
		public static final String	fragmentShaderName		= "waterTielFragmentShader";
		public static final String	vertexShaderSource		= "vertexShader.glsl";
		public static final String	fragmentShaderSource	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String projection   = "projection";
		public static final String view         = "view";
		public static final String transform    = "transform";
		public static final String uniforms[]   = {
				Uniforms.projection,
				Uniforms.view,
				Uniforms.transform,
		};
	}
	
	public WaterTileShaderProgram() {
		super(
				Constants.shaderProgramName,
				Uniforms.uniforms,
				new VertexShader(
						Constants.vertexShaderName,
						Shader.getShaderSource(WaterTileShaderProgram.class, Constants.vertexShaderSource)
				),
				new FragmentShader(
						Constants.fragmentShaderName,
						Shader.getShaderSource(WaterTileShaderProgram.class, Constants.fragmentShaderSource)
				)
		);
	}
	
	public int getVertexPositionAttributeLocation() {
		return 0;
	}
	
	public int getTextureCoordinateAttributeLocation() {
		return 1;
	}
	
	@Override
	public int getViewMatrixUniformPosition() {
		return super.getUniformLocation(Uniforms.view);
	}
	
	@Override
	public int getProjectionMatrixUniformPosition() {
		return super.getUniformLocation(Uniforms.projection);
	}
	
	@Override
	public int getTransformUniformLocation() {
		return super.getUniformLocation(Uniforms.transform);
	}
}

package water.basicWater.basicTerrain.basicTerrainShaderProgram;

import base.glsl.Shader;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.objects.renderer.scene.SceneShaderProgram;
import base.objects.renderer.scene.camera.Camera;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class BasicTerrainShaderProgram extends SceneShaderProgram {
	private static class Constants {
		public static final String	shaderProgramName		= "basicTerrainShaderProgram";
		public static final String	vertexShaderName		= "basicTerrainVertexShader";
		public static final String	fragmentShaderName		= "basicTerrainFragmentShader";
		public static final String	vertexShaderSource		= "vertexShader.glsl";
		public static final String	fragmentShaderSource	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String projection   = "projection";
		public static final String view         = "view";
		public static final String transform    = "transform";
		public static final String grassTexture = "grassTexture";
		public static final String sandTexture  = "sandTexture";
		public static final String uniforms[]   = {
				Uniforms.projection,
				Uniforms.view,
				Uniforms.transform,
				Uniforms.grassTexture,
				Uniforms.sandTexture
		};
	}
	
	public BasicTerrainShaderProgram() {
		super(
				Constants.shaderProgramName,
				Uniforms.uniforms,
				new VertexShader(
						Constants.vertexShaderName,
						Shader.getShaderSource(BasicTerrainShaderProgram.class, Constants.vertexShaderSource)
				),
				new FragmentShader(
						Constants.fragmentShaderName,
						Shader.getShaderSource(BasicTerrainShaderProgram.class, Constants.fragmentShaderSource)
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

	public int getGrassTextureUniformLocation() {
		return super.getUniformLocation(Uniforms.grassTexture);
	}
	
	public int getSandTextureUniformLocation() {
		return super.getUniformLocation(Uniforms.sandTexture);
	}
}

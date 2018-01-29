package skybox.shaderProgram;

import base.glsl.Shader;
import base.glsl.ShaderProgram;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

public class SkyboxShaderProgram extends ShaderProgram {
	
	private static class Constants {
		public static final String name                 = "skyboxShaderProgram";
		public static final String vertexShaderName     = "skyboxVertexShader";
		public static final String fragmentShaderName   = "skyboxFragmentShader";
		public static final String vertexShaderSource   = "vertexShader.glsl";
		public static final String fragmentShaderSource = "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String projection = "projection";
		public static final String view       = "view";
		public static final String cubeMap    = "cubeMap";
		
		public static final String[] uniforms = {
				Uniforms.projection,
				Uniforms.view,
				Uniforms.cubeMap
		};
	}
	
	public SkyboxShaderProgram() {
		super(
				Constants.name,
				Uniforms.uniforms,
				new VertexShader(Constants.vertexShaderName, Shader.getShaderSource(SkyboxShaderProgram.class, Constants.vertexShaderSource)),
				new FragmentShader(Constants.fragmentShaderName, Shader.getShaderSource(SkyboxShaderProgram.class, Constants.fragmentShaderSource))
		);
	}
	
	public int getProjectionUniformLocation() {
		return super.getUniformLocation(Uniforms.projection);
	}
	
	public int getViewUniformLocation() {
		return super.getUniformLocation(Uniforms.view);
	}
	
	public int getCubeMapUniformLocation() {
		return super.getUniformLocation(Uniforms.cubeMap);
	}
	
	public int getVertexPositionAttributeLocation() {
		return 0;
	}
}

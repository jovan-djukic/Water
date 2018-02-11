package tests.texturePreviewRenderer.texturePreviewShaderProgram;

import base.Utilities;
import base.glsl.Shader;
import base.glsl.ShaderProgram;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.glsl.uniformSetter.Uniform1i;
import com.jogamp.opengl.GL4;

public abstract class TexturePreviewShaderProgram extends ShaderProgram {
	private static class Constants {
		public static final String shaderProgramName                = "texturePreviewShaderProgram";
		public static final String vertexShaderName                 = "texturePreviewVertexShader";
		public static final String fragmentShaderName               = "texturePreviewFragmentShader";
		public static final String vertexShaderSourceFileName       = "vertexShader.glsl";
	}
	
	public static class Uniforms {
		public static final String sampler    = "sampler";
		public static final String uniforms[] = {
				Uniforms.sampler
		};
	}
	
	
	public TexturePreviewShaderProgram(String fragmentShaderSource, String ...additionalUniforms) {
		super(
			Constants.shaderProgramName,
			Utilities.getInstance().concatenate(String.class, Uniforms.uniforms, additionalUniforms),
			new VertexShader(Constants.vertexShaderName, Shader.getShaderSource(TexturePreviewShaderProgram.class, Constants.vertexShaderSourceFileName)),
			new FragmentShader(Constants.fragmentShaderName, fragmentShaderSource)
		);
	}
	
	public void setSamplerUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.sampler), textureUnit));
	}
}

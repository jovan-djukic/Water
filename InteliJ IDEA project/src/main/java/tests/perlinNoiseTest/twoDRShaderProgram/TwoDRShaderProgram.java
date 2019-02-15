package tests.perlinNoiseTest.twoDRShaderProgram;

import base.glsl.Shader;
import base.glsl.ShaderProgram;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.glsl.uniformSetter.Uniform1i;
import com.jogamp.opengl.GL4;

import java.util.ArrayList;

public class TwoDRShaderProgram extends ShaderProgram {
	private static class Constants {
		public static final String	shaderProgramName			= "texturePreviewShaderProgram";
		public static final String	vertexShaderName			= "twoDRVertexShader";
		public static final String	fragmentShaderName			= "twoDRFragmentShader";
		
		public static final String	vertexShaderSourceFile		= "vertexShader.glsl";
		public static final String	fragmentShaderSourceFile	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String sampler    = "sampler";
		public static final String uniforms[] = {
				Uniforms.sampler
		};
	}
	
	public TwoDRShaderProgram() {
		super(
			Constants.shaderProgramName,
			Uniforms.uniforms,
			new VertexShader(Constants.vertexShaderName, Shader.getShaderSource(TwoDRShaderProgram.class, Constants.vertexShaderSourceFile)),
			new FragmentShader(Constants.fragmentShaderName, Shader.getShaderSource(TwoDRShaderProgram.class, Constants.fragmentShaderSourceFile))
		);
	}
	
	public void setSamplerUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.sampler), textureUnit));
	}
}

package base.glsl.shaders;

import com.jogamp.opengl.GL4;

import base.glsl.Shader;

public class VertexShader extends Shader {
	public VertexShader(String shaderName) {
		super(GL4.GL_VERTEX_SHADER, shaderName);
	}
}

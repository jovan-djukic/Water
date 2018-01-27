package base.glsl.shaders;

import com.jogamp.opengl.GL4;

import base.glsl.Shader;

public class GeometryShader extends Shader {
	public GeometryShader(String shaderName) {
		super(GL4.GL_GEOMETRY_SHADER, shaderName);
	}
}

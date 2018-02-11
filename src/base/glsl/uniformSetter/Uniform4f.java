package base.glsl.uniformSetter;

import com.jogamp.opengl.GL4;
import org.joml.Vector4f;

public class Uniform4f extends UniformSetter {
	private static class Constants {
		public static final String tag = "uniform4f";
	}
	
	private Vector4f vector4f;
	
	public Uniform4f(int uniformLocation, Vector4f vector4f) {
		super(uniformLocation);
		
		this.vector4f = vector4f;
	}
	
	public Uniform4f(int uniformLocation, float v0, float v1, float v2, float v3) {
		this(uniformLocation, new Vector4f(v0, v1, v2, v3));
	}
	
	@Override
	public void set(GL4 gl) {
		gl.glUniform4f(super.getUniformLocation(), this.vector4f.x, this.vector4f.y, this.vector4f.z, this.vector4f.w);
		
		super.checkForErrors(gl, Constants.tag);
	}
}

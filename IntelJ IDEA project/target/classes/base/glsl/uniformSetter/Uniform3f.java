package base.glsl.uniformSetter;

import com.jogamp.opengl.GL4;
import org.joml.Vector3f;

public class Uniform3f extends UniformSetter {
	private static class Constants {
		public static final String tag = "uniform3f";
	}
	
	private Vector3f vector3f;
	
	public Uniform3f(int uniformLocation, Vector3f vector4f) {
		super(uniformLocation);
		
		this.vector3f = vector4f;
	}
	
	public Uniform3f(int uniformLocation, float v0, float v1, float v2) {
		this(uniformLocation, new Vector3f(v0, v1, v2));
	}
	
	@Override
	public void set(GL4 gl) {
		gl.glUniform3f(super.getUniformLocation(), this.vector3f.x, this.vector3f.y, this.vector3f.z);
		
		super.checkForErrors(gl, Constants.tag);
	}
}

package base.glsl.uniformSetter;

import com.jogamp.opengl.GL4;

public class Uniform1f extends UniformSetter {
	private static class Constants {
		public static final String tag = "uniform1iv";
	}
	
	private float value;
	
	public Uniform1f(int uniformLocation, float value) {
		super(uniformLocation);
		
		this.value = value;
	}
	
	@Override
	public void set(GL4 gl) {
		gl.glUniform1f(super.getUniformLocation(), this.value);
		
		super.checkForErrors(gl, Constants.tag);
	}
}

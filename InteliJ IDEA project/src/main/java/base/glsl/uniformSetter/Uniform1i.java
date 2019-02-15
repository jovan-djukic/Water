package base.glsl.uniformSetter;

import com.jogamp.opengl.GL4;

public class Uniform1i extends UniformSetter {
	private static class Constants {
		public static final String tag = "uniform1iv";
	}
	
	private int value;
	
	public Uniform1i(int uniformLocation, int value) {
		super(uniformLocation);
		
		this.value = value;
	}
	
	@Override
	public void set(GL4 gl) {
		gl.glUniform1i(super.getUniformLocation(), value);
		
		super.checkForErrors(gl, Constants.tag);
	}
}

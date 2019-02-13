package base.glsl.uniformSetter;

import com.jogamp.opengl.GL4;

public class Uniform1iv extends UniformSetter {
	private static class Constants {
		public static final String tag = "uniform1iv";
	}
	
	private int count;
	private int values[];
	private int valueOffset;
	
	public Uniform1iv(int uniformLocation, int count, int values[], int valueOffset) {
		super(uniformLocation);
		
		this.count = count;
		this.values = values;
		this.valueOffset = valueOffset;
	}
	
	@Override
	public void set(GL4 gl) {
		gl.glUniform1iv(super.getUniformLocation(), this.count, this.values, this.valueOffset);
		
		super.checkForErrors(gl, Constants.tag);
	}
}

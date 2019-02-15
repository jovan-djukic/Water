package base.glsl.uniformSetter;

import com.jogamp.opengl.GL4;

public class UniformMatrix4fv extends UniformSetter {
	private static class Constants {
		public static final String tag = "uniform4fv";
	}
	
	private int count;
	private boolean transpose;
	private float values[];
	private int valueOffset;
	
	public UniformMatrix4fv(int uniformLocation, int count, boolean transpose, float[] values, int valueOffset) {
		super(uniformLocation);
		this.count = count;
		this.transpose = transpose;
		this.values = values;
		this.valueOffset = valueOffset;
	}
	
	@Override
	public void set(GL4 gl) {
		gl.glUniformMatrix4fv(super.getUniformLocation(), this.count, this.transpose, this.values, this.valueOffset);
		
		super.checkForErrors(gl, Constants.tag);
	}
}

package base.glsl.uniformSetter;

import com.jogamp.opengl.GL4;

public abstract class UniformSetter {
	private static class Constants {
		public static final String unknownCode        = "unknown code";
		public static final String invalidValue       = "Invalid value";
		public static final String invalidOperation   = "Invalid operation";
		public static final String errorMessageFormat = "[%s][%d]%s";
	}
	
	private int uniformLocation;
	
	public UniformSetter(int uniformLocation) {
		this.uniformLocation = uniformLocation;
	}
	
	protected int getUniformLocation() {
		return uniformLocation;
	}
	
	public abstract void set(GL4 gl);
	
	public void checkForErrors(GL4 gl, String tag) {
		int glErrorCode = gl.glGetError();
		if (glErrorCode != GL4.GL_NO_ERROR) {
			
			String errorMessage = Constants.unknownCode;
			switch (glErrorCode) {
				case GL4.GL_INVALID_VALUE: {
					errorMessage = Constants.invalidValue;
					break;
				}
				case GL4.GL_INVALID_OPERATION: {
					errorMessage = Constants.invalidOperation;
					break;
				}
			}
			
			System.out.println(String.format(Constants.errorMessageFormat, tag, glErrorCode, errorMessage));
		}
	}
}

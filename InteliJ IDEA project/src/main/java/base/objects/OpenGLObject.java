package base.objects;

import com.jogamp.opengl.GL4;

public abstract class OpenGLObject {
	private static class Constants {
		public static final String unknownCode        = "unknown code";
		public static final String invalidValue       = "Invalid value";
		public static final String invalidOperation   = "Invalid operation";
		public static final String invalidEnum        = "Invalid enum";
		public static final String errorMessageFormat = "[%s][%s][%d]%s";
	}
	
	private enum Status	{
		UNINITIALIZED, INITIALIZED, DESTROYED
	}
	
	private String name;
	private Status status;
	
	public OpenGLObject(String name) {
		this.name = name;
		
		this.status = Status.UNINITIALIZED;
	}
	
	public boolean isInitialized() {
		return Status.INITIALIZED.equals(this.status);
	}
	
	public String getName() {
		return name;
	}
	
	public void init(GL4 gl) {
		this.status = Status.INITIALIZED;
	}
	
	public boolean isDestroyed() {
		return Status.DESTROYED.equals(this.status);
	}
	
	public void destroy(GL4 gl) {
		this.status = Status.DESTROYED;
	}
	
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
				case GL4.GL_INVALID_ENUM: {
					errorMessage = Constants.invalidEnum;
					break;
				}
			}
			
			System.out.println(String.format(Constants.errorMessageFormat, this.name, tag, glErrorCode, errorMessage));
		}
	}
}

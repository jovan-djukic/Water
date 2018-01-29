package base.objects.model;

import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public abstract class Loader extends OpenGLObject {
	protected static class Constants {
		public static final String loader     = "loader-";
		public static final String initTag    = Constants.loader + "init";
		public static final String destroyTag = Constants.loader + "destroy";
	}
	
	private int bufferID;
	
	public Loader(String name) {
		super(name);
	}
	
	public int getBufferID() {
		return bufferID;
	}
	
	@Override
	public void init(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		
		gl.glGenBuffers(1, intBuffer);
		this.bufferID = intBuffer.get(0);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.bufferID);
		intBuffer.rewind();
		gl.glDeleteBuffers(1, intBuffer);
		
		this.checkForErrors(gl, Constants.destroyTag);
	}
}

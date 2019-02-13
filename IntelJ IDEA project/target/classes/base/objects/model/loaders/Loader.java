package base.objects.model.loaders;

import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public abstract class Loader extends OpenGLObject {
	protected static class Constants {
		public static final String loader     = "loader-";
		public static final String initTag    = Constants.loader + "init";
		public static final String destroyTag = Constants.loader + "destroy";
	}
	
	private int bufferIDs[];
	
	public Loader(String name, int numberOfBuffers) {
		super(name);
		
		this.bufferIDs = new int[numberOfBuffers];
	}
	
	public int getBufferID() {
		return this.bufferIDs[0];
	}
	
	public int getBufferID(int index) {
		return this.bufferIDs[index];
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		IntBuffer intBuffer = IntBuffer.allocate(this.bufferIDs.length);
		
		gl.glGenBuffers(this.bufferIDs.length, intBuffer);
		intBuffer.get(this.bufferIDs);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		super.destroy(gl);
		IntBuffer intBuffer = IntBuffer.allocate(this.bufferIDs.length);
		intBuffer.put(this.bufferIDs);
		intBuffer.rewind();
		gl.glDeleteBuffers(this.bufferIDs.length, intBuffer);
		
		this.checkForErrors(gl, Constants.destroyTag);
	}
}

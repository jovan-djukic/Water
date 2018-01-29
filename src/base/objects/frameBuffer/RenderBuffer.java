package base.objects.frameBuffer;

import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public class RenderBuffer extends OpenGLObject {
	private static class Constants {
		public static final String renderBuffer           = "renderBuffer-";
		public static final String initTag                = Constants.renderBuffer + "init";
		public static final String destroyTag             = Constants.renderBuffer + "destroy";
		public static final String renderBufferStorageTag = Constants.renderBuffer + "renderBufferStorage";
	}
	
	private int renderBufferID;
	
	public RenderBuffer(String name) {
		super(name);
	}
	
	public int getRenderBufferID() {
		return renderBufferID;
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenRenderbuffers(1, intBuffer);
		this.renderBufferID = intBuffer.get(0);
		
		super.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		super.destroy(gl);
		
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.renderBufferID);
		intBuffer.rewind();
		gl.glDeleteRenderbuffers(1, intBuffer);
		
		super.checkForErrors(gl, Constants.destroyTag);
	}
	
	public RenderBuffer bind(GL4 gl) {
		gl.glBindRenderbuffer(GL4.GL_RENDERBUFFER, this.renderBufferID);
		return this;
	}
	
	public RenderBuffer unbind(GL4 gl) {
		gl.glBindRenderbuffer(GL4.GL_RENDERBUFFER, 0);
		return this;
	}
	
	public RenderBuffer renderBufferStorage(GL4 gl, int internalFormat, int width, int height) {
		gl.glRenderbufferStorage(GL4.GL_RENDERBUFFER, internalFormat, width, height);
		super.checkForErrors(gl, Constants.renderBufferStorageTag);
		return this;
	}
}

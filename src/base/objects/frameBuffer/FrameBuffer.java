package base.objects.frameBuffer;

import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public abstract class FrameBuffer extends OpenGLObject {
	private static class Constants {
		public static final String frameBufferIncompleteAttachment        = "Incomplete attachment";
		public static final String frameBufferIncompleteDimension         = "Incomplete dimension";
		public static final String frameBufferIncompleteMissingAttachment = "Incomplete missing attachment";
		public static final String frameBufferUnsupported                 = "Unsupported";
		public static final String frameBuffer                            = "frameBuffer-";
		public static final String initTag                                = Constants.frameBuffer + "init";
		public static final String destroyTag                             = Constants.frameBuffer + "destroy";
	}
	
	private static final class SuccessMessages {
		public static final String frameBufferComplete = "Frame buffer named %s is complete";
	}
	
	protected int frameBufferID, vertexArrayID;
	
	protected FrameBuffer(String name) {
		super(name);
	}
	
	protected abstract void initializeFrameBuffer(GL4 gl);
	
	@Override
	public void init(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenFramebuffers(1, intBuffer);
		
		this.frameBufferID = intBuffer.get(0);
		
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, this.frameBufferID);
		
		intBuffer.rewind();
		gl.glGenVertexArrays(1, intBuffer);
		this.vertexArrayID = intBuffer.get(0);
		
		gl.glBindVertexArray(this.vertexArrayID);
		
		this.initializeFrameBuffer(gl);
		
		gl.glBindVertexArray(0);
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
		
		System.out.println(this.getStatusMessage(gl));
		this.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.vertexArrayID);
		intBuffer.rewind();
		gl.glDeleteVertexArrays(1, intBuffer);
		
		intBuffer.rewind();
		intBuffer.put(this.frameBufferID);
		intBuffer.rewind();
		gl.glDeleteFramebuffers(1, intBuffer);
		this.checkForErrors(gl, Constants.destroyTag);
	}
	
	public void bind(GL4 gl) {
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, this.frameBufferID);
		gl.glBindVertexArray(this.vertexArrayID);
	}
	
	public void unbind(GL4 gl) {
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
		gl.glBindVertexArray(0);
	}
	public int getStatus(GL4 gl) {
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, this.frameBufferID);
		int frameBufferStatus = gl.glCheckFramebufferStatus(GL4.GL_FRAMEBUFFER);
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
		
		return frameBufferStatus;
	}
	
	public String getStatusMessage(GL4 gl) {
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, this.frameBufferID);
		int frameBufferStatus = gl.glCheckFramebufferStatus(GL4.GL_FRAMEBUFFER);
		String returnMessage = "";
		
		switch (frameBufferStatus) {
			case GL4.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT: {
				returnMessage = Constants.frameBufferIncompleteAttachment;
				break;
			}
			
			case GL4.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS: {
				returnMessage = Constants.frameBufferIncompleteDimension;
				break;
			}
			case GL4.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT: {
				returnMessage = Constants.frameBufferIncompleteMissingAttachment;
				break;
			}
			case GL4.GL_FRAMEBUFFER_UNSUPPORTED: {
				returnMessage = Constants.frameBufferUnsupported;
				break;
			}
			case GL4.GL_FRAMEBUFFER_COMPLETE: {
				returnMessage = String.format(SuccessMessages.frameBufferComplete, this.name);
				break;
			}
		}
		
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
		
		return returnMessage;
	}
}

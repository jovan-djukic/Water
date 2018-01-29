package base.objects.frameBuffer;

import base.objects.OpenGLObject;
import base.objects.textures.TextureBase;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public class FrameBuffer extends OpenGLObject {
	private static class Constants {
		public static final String frameBufferIncompleteAttachment        = "Incomplete attachment";
		public static final String frameBufferIncompleteDimension         = "Incomplete dimension";
		public static final String frameBufferIncompleteMissingAttachment = "Incomplete missing attachment";
		public static final String frameBufferUnsupported                 = "Unsupported";
		public static final String frameBuffer                            = "frameBuffer-";
		public static final String initTag                                = Constants.frameBuffer + "init";
		public static final String destroyTag                             = Constants.frameBuffer + "destroy";
		public static final String addColorAttachmentTag                  = Constants.frameBuffer + "addColorAttachment";
		public static final String drawBuffersTag                         = Constants.frameBuffer + "drawBuffers";
	}
	
	private static final class SuccessMessages {
		public static final String frameBufferComplete = "Frame buffer named %s is complete";
	}
	
	protected int frameBufferID;
	
	public FrameBuffer(String name) {
		super(name);
	}
	
	@Override
	public void init(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenFramebuffers(1, intBuffer);
		
		this.frameBufferID = intBuffer.get(0);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.frameBufferID);
		intBuffer.rewind();
		gl.glDeleteFramebuffers(1, intBuffer);
		
		this.checkForErrors(gl, Constants.destroyTag);
	}
	
	public FrameBuffer bind(GL4 gl) {
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, this.frameBufferID);
		return this;
	}
	
	public FrameBuffer unbind(GL4 gl) {
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
		return this;
	}
	
	public FrameBuffer addColorAttachment(GL4 gl, int colorAtachment, TextureBase textureBase, int level) {
		gl.glFramebufferTexture2D(GL4.GL_FRAMEBUFFER, GL4.GL_COLOR_ATTACHMENT0 + colorAtachment, textureBase.getTarget(), textureBase.getTextureID(), level);
		this.checkForErrors(gl, Constants.addColorAttachmentTag);
		return this;
	}
	
	public FrameBuffer drawBuffers(GL4 gl, int ...drawBuffers) {
		for (int i = 0; i < drawBuffers.length; i++) {
			drawBuffers[i] += GL4.GL_COLOR_ATTACHMENT0;
		}
		
		gl.glDrawBuffers(drawBuffers.length, drawBuffers, 0);
		
		this.checkForErrors(gl, Constants.drawBuffersTag);
		
		return this;
	}
	
	public FrameBuffer status(GL4 gl) {
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, this.frameBufferID);
		int frameBufferStatus = gl.glCheckFramebufferStatus(GL4.GL_FRAMEBUFFER);
		String statusMessage = "";
		
		switch (frameBufferStatus) {
			case GL4.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT: {
				statusMessage = Constants.frameBufferIncompleteAttachment;
				break;
			}
			
			case GL4.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS: {
				statusMessage = Constants.frameBufferIncompleteDimension;
				break;
			}
			case GL4.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT: {
				statusMessage = Constants.frameBufferIncompleteMissingAttachment;
				break;
			}
			case GL4.GL_FRAMEBUFFER_UNSUPPORTED: {
				statusMessage = Constants.frameBufferUnsupported;
				break;
			}
			case GL4.GL_FRAMEBUFFER_COMPLETE: {
				statusMessage = String.format(SuccessMessages.frameBufferComplete, super.getName());
				break;
			}
		}
		
		gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
		
		System.out.println(statusMessage);
		
		return this;
	}
}

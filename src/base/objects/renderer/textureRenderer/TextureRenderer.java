package base.objects.renderer.textureRenderer;

import base.Utilities;
import base.objects.OpenGLObject;
import base.objects.frameBuffer.FrameBuffer;
import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.RendererBase;
import base.objects.textures.Texture;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public abstract class TextureRenderer extends CompositeRenderer {
	private static class Constants {
		public static final int    xIndex          = 0;
		public static final int    yIndex          = 1;
		public static final int    widthIndex      = 2;
		public static final int    heightIndex     = 3;
		public static final String textureRenderer = "textureRenderer-";
		public static final String initTag         = Constants.textureRenderer + "init";
		public static final String preRenderTag    = Constants.textureRenderer + "preRenderTag";
		public static final String postRenderTag   = Constants.textureRenderer + "postRenderTag";
	}
	
	private FrameBuffer frameBuffer;
	private Texture colorAttachment;
	private int width, height;
	private IntBuffer oldViewPort;
	
	protected TextureRenderer(String name, RendererBase[] renderers, FrameBuffer frameBuffer, Texture colorAttachment, int width, int height, OpenGLObject ...openGLObjects) {
		super(name, renderers, Utilities.getInstance().concatenate(OpenGLObject.class, Utilities.getInstance().concatenate(OpenGLObject.class, frameBuffer, colorAttachment),  openGLObjects));
		
		this.frameBuffer = frameBuffer;
		this.colorAttachment = colorAttachment;
		
		this.width = width;
		this.height = height;
		
		this.oldViewPort = IntBuffer.allocate(4);
	}
	
	protected int getWidth() {
		return width;
	}
	
	protected int getHeight() {
		return height;
	}
	
	protected FrameBuffer getFrameBuffer() {
		return this.frameBuffer;
	}
	
	public Texture getColorAttachment() {
		return colorAttachment;
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.colorAttachment.bind(gl)
				.texImage2D(gl, 0, this.width, this.height, null)
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
		
		this.frameBuffer.bind(gl)
				.addTextureColorAttachment(gl, 0, this.colorAttachment, 0)
				.drawBuffers(gl, 0)
				.unbind(gl);
		
		this.frameBuffer.status(gl);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		gl.glGetIntegerv(GL4.GL_VIEWPORT, this.oldViewPort);
		this.oldViewPort.rewind();
		
		this.frameBuffer.bind(gl);
		
		gl.glViewport(0, 0, this.width, this.height);
		gl.glClearColor(0, 0, 0, 0);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		gl.glViewport(
				this.oldViewPort.get(Constants.xIndex),
				this.oldViewPort.get(Constants.yIndex),
				this.oldViewPort.get(Constants.widthIndex),
				this.oldViewPort.get(Constants.heightIndex)
		);
		this.oldViewPort.rewind();
		
		this.frameBuffer.unbind(gl);
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
}

package water.basicWater.textureRenderer;

import base.objects.frameBuffer.FrameBuffer;
import base.objects.renderer.RendererBase;
import base.objects.renderer.textureRenderer.TextureRenderer;
import base.objects.textures.Texture;
import com.jogamp.opengl.GL4;

public class RefractionTextureRenderer extends TextureRenderer {
	private static class Constants {
		public static final String refractionTextureRenderer = "refractionTextureRenderer";
		public static final String frameBuffer               = Constants.refractionTextureRenderer + "frameBuffer";
		public static final String colorAttachment           = Constants.refractionTextureRenderer + "colorAttachment";
		public static final String depthAttachment           = Constants.refractionTextureRenderer + "depthAttachment";
		public static final String preRenderTag              = Constants.refractionTextureRenderer + "-preRender";
		public static final String postRenderTag             = Constants.refractionTextureRenderer + "-postRender";
	}
	
	private Texture depthAttachment;
	
	protected RefractionTextureRenderer(String name, RendererBase[] renderers, FrameBuffer frameBuffer, Texture colorAttachment, int width, int height, Texture depthAttachment) {
		super(name, renderers, frameBuffer, colorAttachment, width, height, depthAttachment);
		
		this.depthAttachment = depthAttachment;
	}
	
	public RefractionTextureRenderer(String name, RendererBase[] renderers, int width, int height) {
		this(name, renderers, new FrameBuffer(Constants.frameBuffer), new Texture(Constants.colorAttachment, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE), width, height, new Texture(Constants.depthAttachment, GL4.GL_DEPTH_COMPONENT32, GL4.GL_DEPTH_COMPONENT, GL4.GL_FLOAT));
	}
	
	public Texture getDepthAttachment() {
		return depthAttachment;
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.depthAttachment.bind(gl)
				.texImage2D(gl, 0, super.getWidth(), super.getHeight(), null)
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR);
		
		super.getFrameBuffer().bind(gl)
				.addTextureDepthAttachment(gl, this.depthAttachment, 0)
				.unbind(gl);
		
		super.getFrameBuffer().status(gl);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glClear(GL4.GL_DEPTH_BUFFER_BIT);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		super.postRender(gl);
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
}

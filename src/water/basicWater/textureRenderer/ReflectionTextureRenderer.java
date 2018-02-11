package water.basicWater.textureRenderer;

import base.objects.frameBuffer.FrameBuffer;
import base.objects.renderer.RendererBase;
import base.objects.renderer.textureRenderer.TextureRenderer;
import base.objects.textures.Texture;
import com.jogamp.opengl.GL4;

public class ReflectionTextureRenderer extends TextureRenderer {
	
	private static class Constants {
		public static final String reflectionTextureRenderer = "reflectionTextureRenderer";
		public static final String frameBuffer               = Constants.reflectionTextureRenderer + "frameBuffer";
		public static final String colorAttachment           = Constants.reflectionTextureRenderer + "colorAttachment";
		public static final String depthAttachment           = Constants.reflectionTextureRenderer + "depthAttachment";
	}
	
	private Texture depthAttachment;
	
	protected ReflectionTextureRenderer(String name, RendererBase[] renderers, FrameBuffer frameBuffer, Texture colorAttachment, int width, int height, Texture depthAttachment) {
		super(name, renderers, frameBuffer, colorAttachment, width, height, depthAttachment);
		
		this.depthAttachment = depthAttachment;
	}
	
	public ReflectionTextureRenderer(String name, RendererBase[] renderers, int width, int height) {
		this(name, renderers, new FrameBuffer(Constants.frameBuffer), new Texture(Constants.colorAttachment, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE), width, height, new Texture(Constants.depthAttachment, GL4.GL_DEPTH_COMPONENT32F, GL4.GL_DEPTH_COMPONENT, GL4.GL_FLOAT));
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
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
		
		
		super.getFrameBuffer().bind(gl)
				.addTextureDepthAttachment(gl, this.depthAttachment, 0)
				.unbind(gl);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glClear(GL4.GL_DEPTH_BUFFER_BIT);
	}
}

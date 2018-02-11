package water.basicWater.textureRenderer;

import base.objects.frameBuffer.FrameBuffer;
import base.objects.frameBuffer.RenderBuffer;
import base.objects.renderer.RendererBase;
import base.objects.renderer.textureRenderer.TextureRenderer;
import base.objects.textures.Texture;
import com.jogamp.opengl.GL4;
import water.basicWater.BasicWaterCamera;
import water.basicWater.basicWaterTerrainScene.basicWaterTerrainShaderProgram.BasicWaterTerrainShaderProgram;

public class ReflectionTextureRenderer extends TextureRenderer {
	private static class Constants {
		public static final String reflectionTextureRenderer = "reflectionTextureRenderer";
		public static final String frameBuffer               = Constants.reflectionTextureRenderer + "frameBuffer";
		public static final String colorAttachment           = Constants.reflectionTextureRenderer + "colorAttachment";
		public static final String depthAttachment           = Constants.reflectionTextureRenderer + "depthAttachment";
		public static final String preRenderTag  = Constants.reflectionTextureRenderer + "-preRender";
		public static final String postRenderTag = Constants.reflectionTextureRenderer + "-postRender";
	}
	
	private RenderBuffer depthAttachment;
	private BasicWaterCamera camera;
	private float distance;
	
	protected ReflectionTextureRenderer(String name, RendererBase[] renderers, FrameBuffer frameBuffer, Texture colorAttachment, int width, int height, RenderBuffer depthAttachment, BasicWaterCamera camera) {
		super(name, renderers, frameBuffer, colorAttachment, width, height, depthAttachment);
		
		this.depthAttachment = depthAttachment;
		this.camera = camera;
	}
	
	public ReflectionTextureRenderer(String name, RendererBase[] renderers, int width, int height, BasicWaterCamera camera) {
		this(name, renderers, new FrameBuffer(Constants.frameBuffer), new Texture(Constants.colorAttachment, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE), width, height, new RenderBuffer(Constants.depthAttachment), camera);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.depthAttachment.bind(gl)
				.renderBufferStorage(gl, GL4.GL_DEPTH_COMPONENT, super.getWidth(), super.getHeight());
		
		super.getFrameBuffer().bind(gl)
							.addRenderBufferDepthAttachment(gl, this.depthAttachment)
							.unbind(gl);
		
		super.getFrameBuffer().status(gl);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glClear(GL4.GL_DEPTH_BUFFER_BIT);
		
		this.distance = 2 * this.camera.getPosition().y;
		this.camera.getPosition().y -= this.distance;
		this.camera.invertPitch();
		this.camera.update();
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		super.postRender(gl);
		
		this.camera.getPosition().y += this.distance;
		this.camera.invertPitch();
		this.camera.update();
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
}

package perlinNoise.perlinNoiseTextureGenerator;

import base.objects.frameBuffer.FrameBuffer;
import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.Renderer;
import base.objects.textures.Texture;
import base.objects.textures.TextureBase;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public class PerlinNoiseTextureGenerator extends CompositeRenderer {
	protected static class Constants {
		public static final int    xIndex                      = 0;
		public static final int    yIndex                      = 1;
		public static final int    widthIndex                  = 2;
		public static final int    heightIndex                 = 3;
		public static final String perlinNoiseTexture          = "-perlinNoiseTexture";
		public static final String perlinNoiseFrameBuffer      = "-perlinNoiseFrameBuffer";
		public static final String perlinNoiseGenerator        = "-perlinNoiseGenerator-";
		public static final String perlinNoiseTextureGenerator = "perlinNoiseTextureGenerator-";
		public static final String initTag                     = Constants.perlinNoiseTextureGenerator + "init";
		public static final String preRenderTag                = Constants.perlinNoiseTextureGenerator + "preRender";
		public static final String postRenderTag               = Constants.perlinNoiseTextureGenerator + "postRender";
	}
	
	private int     textureWidth, textureHeight;
	private IntBuffer oldViewportBuffer, oldPolygonMode;
	private boolean isDepthTestEnabled, isBlendEnabled;
	private Texture perlinNoiseTexture;
	private FrameBuffer perlinNoiseFrameBuffer;
	
	private PerlinNoiseTextureGenerator(String name, int numberOfOctaves, float persistence, float scaleX, float scaleY, int textureWidth, int textureHeight, Texture perlinNoiseTexture, FrameBuffer perlinNoiseFrameBuffer) {
		super(
			name,
			new Renderer[] {new PerlinNoiseRenderer(name + Constants.perlinNoiseGenerator, numberOfOctaves, persistence, scaleX, scaleY)},
			perlinNoiseTexture,
			perlinNoiseFrameBuffer
		);
		
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.oldViewportBuffer = IntBuffer.allocate(4);
		this.oldPolygonMode = IntBuffer.allocate(1);
		this.perlinNoiseTexture = perlinNoiseTexture;
		this.perlinNoiseFrameBuffer = perlinNoiseFrameBuffer;
	}
	
	public PerlinNoiseTextureGenerator(String name, int numberOfOctaves, float persistence, float scaleX, float scaleY, int textureWidth, int textureHeight) {
		this(name, numberOfOctaves, persistence, scaleX, scaleY, textureWidth, textureHeight, new Texture(name + Constants.perlinNoiseTexture), new FrameBuffer(name + Constants.perlinNoiseFrameBuffer));
	}
	
	public TextureBase getPerlinNoiseTexture() {
		return perlinNoiseTexture;
	}
	
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.perlinNoiseTexture.bind(gl)
			.texImage2D(gl, GL4.GL_TEXTURE_2D, 0, GL4.GL_R32F, this.textureWidth, this.textureHeight, GL4.GL_RED, GL4.GL_FLOAT, null)
			.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST)
			.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST)
			.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
			.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT);
		
		this.perlinNoiseFrameBuffer.bind(gl)
				.addTextureColorAttachment(gl, 0, this.perlinNoiseTexture, 0)
				.drawBuffers(gl, 0)
				.unbind(gl);
		
		this.perlinNoiseFrameBuffer.status(gl);
		
		super.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		gl.glGetIntegerv(GL4.GL_VIEWPORT, this.oldViewportBuffer);
		gl.glGetIntegerv(GL4.GL_POLYGON_MODE, this.oldPolygonMode);
		this.isDepthTestEnabled = gl.glIsEnabled(GL4.GL_DEPTH_TEST);
		this.isBlendEnabled = gl.glIsEnabled(GL4.GL_BLEND);
		
		gl.glViewport(0, 0, this.textureWidth, this.textureHeight);
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_FILL);
		gl.glDisable(GL4.GL_DEPTH_TEST);
		gl.glDisable(GL4.GL_BLEND);
		
		this.perlinNoiseFrameBuffer.bind(gl);
		
		gl.glClearColor(0, 0, 0, 0);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		if (this.isDepthTestEnabled == true) {
			gl.glEnable(GL4.GL_DEPTH_TEST);
		}
		
		if (this.isBlendEnabled == true) {
			gl.glEnable(GL4.GL_BLEND);
		}
		
		gl.glViewport(this.oldViewportBuffer.get(Constants.xIndex), this.oldViewportBuffer.get(Constants.yIndex), this.oldViewportBuffer.get(Constants.widthIndex), this.oldViewportBuffer.get(Constants.heightIndex));
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, this.oldPolygonMode.get(0));
		
		
		this.oldViewportBuffer.rewind();
		this.oldPolygonMode.rewind();
		
		this.perlinNoiseFrameBuffer.unbind(gl);
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
}

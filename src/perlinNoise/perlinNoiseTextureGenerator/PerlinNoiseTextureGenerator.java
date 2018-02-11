package perlinNoise.perlinNoiseTextureGenerator;

import base.objects.frameBuffer.FrameBuffer;
import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.Renderer;
import base.objects.renderer.RendererBase;
import base.objects.renderer.textureRenderer.TextureRenderer;
import base.objects.textures.Texture;
import base.objects.textures.TextureBase;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.nio.IntBuffer;

public class PerlinNoiseTextureGenerator extends TextureRenderer {
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
	
	private IntBuffer oldPolygonMode;
	private boolean isDepthTestEnabled, isBlendEnabled;
	private Texture perlinNoiseTexture;
	
	private PerlinNoiseTextureGenerator(String name, int numberOfOctaves, float persistence, float scaleX, float scaleY, FrameBuffer perlinNoiseFrameBuffer, Texture perlinNoiseTexture, int textureWidth, int textureHeight) {
		super(
			name,
			new RendererBase[] {new PerlinNoiseRenderer(name + Constants.perlinNoiseGenerator, numberOfOctaves, persistence, scaleX, scaleY)},
			perlinNoiseFrameBuffer,
			perlinNoiseTexture,
			textureWidth,
			textureHeight
		);
		
		this.oldPolygonMode = IntBuffer.allocate(1);
		this.perlinNoiseTexture = perlinNoiseTexture;
	}
	
	public PerlinNoiseTextureGenerator(String name, int numberOfOctaves, float persistence, float scaleX, float scaleY, int textureWidth, int textureHeight) {
		this(name, numberOfOctaves, persistence, scaleX, scaleY, new FrameBuffer(name + Constants.perlinNoiseFrameBuffer), new Texture(name + Constants.perlinNoiseTexture, GL4.GL_R32F, GL4.GL_RED, GL4.GL_FLOAT), textureWidth, textureHeight);
	}
	
	public TextureBase getPerlinNoiseTexture() {
		return perlinNoiseTexture;
	}
	
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		gl.glGetIntegerv(GL4.GL_POLYGON_MODE, this.oldPolygonMode);
		this.isDepthTestEnabled = gl.glIsEnabled(GL4.GL_DEPTH_TEST);
		this.isBlendEnabled = gl.glIsEnabled(GL4.GL_BLEND);
		
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_FILL);
		gl.glDisable(GL4.GL_DEPTH_TEST);
		gl.glDisable(GL4.GL_BLEND);
		
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
		
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, this.oldPolygonMode.get(0));
		
		this.oldPolygonMode.rewind();
		
		this.checkForErrors(gl, Constants.postRenderTag);
		
		super.postRender(gl);
	}
}

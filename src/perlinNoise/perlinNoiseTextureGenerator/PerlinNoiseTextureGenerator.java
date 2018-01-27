package perlinNoise.perlinNoiseTextureGenerator;

import base.objects.IRenderable;
import base.objects.frameBuffer.FrameBuffer;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public class PerlinNoiseTextureGenerator extends FrameBuffer implements IRenderable {
	protected static class Constants {
		public static final int    xIndex                      = 0;
		public static final int    yIndex                      = 1;
		public static final int    widthIndex                  = 2;
		public static final int    heightIndex                 = 3;
		public static final String perlinNoiseTextureGenerator = "perlinNoiseTextureGenerator";
		public static final String initTag                     = Constants.perlinNoiseTextureGenerator + "init";
		public static final String renderTag                   = Constants.perlinNoiseTextureGenerator + "render";
	}
	
	private int perlinNoiseTextureID;
	private PerlinNoise perlinNoise;
	private int textureWidth, textureHeight;
	private IntBuffer oldViewportBuffer, oldPolygonMode, drawBuffers;
	private boolean isDepthTestEnabled, isBlendEnabled;
	
	public PerlinNoiseTextureGenerator(String name, int numberOfOctaves, float persistence, float scaleX, float scaleY, int textureWidth, int textureHeight) {
		super(name);
		
		this.perlinNoise = new PerlinNoise(numberOfOctaves, persistence, scaleX, scaleY);
		
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.oldViewportBuffer = IntBuffer.allocate(4);
		this.oldPolygonMode = IntBuffer.allocate(1);
	}
	
	public int getPerlinNoiseTextureID() {
		return this.perlinNoiseTextureID;
	}
	
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		 perlinNoise.init(gl);
	}
	
	@Override
	protected void initializeFrameBuffer(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, intBuffer);
		this.perlinNoiseTextureID = intBuffer.get(0);
		gl.glBindTexture(GL4.GL_TEXTURE_2D, this.perlinNoiseTextureID);
		gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, GL4.GL_R32F, this.textureWidth, this.textureHeight, 0, GL4.GL_RED, GL4.GL_FLOAT, null);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT);
		
		gl.glFramebufferTexture2D(GL4.GL_FRAMEBUFFER, GL4.GL_COLOR_ATTACHMENT0, GL4.GL_TEXTURE_2D, this.perlinNoiseTextureID, 0);
		
		this.drawBuffers = IntBuffer.allocate(1);
		this.drawBuffers.put(GL4.GL_COLOR_ATTACHMENT0);
		this.drawBuffers.rewind();
		
		super.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		perlinNoise.destroy(gl);
		IntBuffer intBuffer = IntBuffer.allocate(1);
		
		intBuffer.put(this.perlinNoiseTextureID);
		intBuffer.rewind();
		gl.glDeleteTextures(1, intBuffer);
		
		super.destroy(gl);
	}
	
	@Override
	public void render(GL4 gl) {
		super.bind(gl);
		gl.glBindVertexArray(super.vertexArrayID);
		gl.glGetIntegerv(GL4.GL_VIEWPORT, this.oldViewportBuffer);
		gl.glGetIntegerv(GL4.GL_POLYGON_MODE, this.oldPolygonMode);
		this.isDepthTestEnabled = gl.glIsEnabled(GL4.GL_DEPTH_TEST);
		this.isBlendEnabled = gl.glIsEnabled(GL4.GL_BLEND);
		
		gl.glViewport(0, 0, this.textureWidth, this.textureHeight);
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_FILL);
		gl.glDisable(GL4.GL_DEPTH_TEST);
		gl.glDisable(GL4.GL_BLEND);
		
		gl.glDrawBuffers(1, this.drawBuffers);
		this.drawBuffers.rewind();
		
		gl.glClearColor(0, 0, 0, 0);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
		
		this.perlinNoise.render(gl);
		
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
		super.unbind(gl);
		this.checkForErrors(gl, Constants.renderTag);
		
	}
}

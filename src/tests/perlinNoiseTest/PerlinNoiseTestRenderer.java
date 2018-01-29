package tests.perlinNoiseTest;

import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.Renderer;
import base.objects.renderer.RendererBase;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;
import perlinNoise.perlinNoiseTextureGenerator.PerlinNoiseTextureGenerator;
import tests.perlinNoiseTest.twoDRShaderProgram.TwoDRShaderProgram;

import java.nio.IntBuffer;

public class PerlinNoiseTestRenderer extends CompositeRenderer {
	private static class Constants {
		public static final String name                    = "perlinNoiseTestRenderer";
		public static final String textureGenerator        = "perlinNoiseTextureGenerator";
		public static final int    numberOfOctaves         = 3;
		public static final float  persistence             = 0.3f;
		public static final int    textureWidth            = 256;
		public static final int    textureHeight           = 256;
		public static final float  scaleX                  = 8.0f / textureWidth;
		public static final float  scaleY                  = 8.0f / textureHeight;
		public static final String perlinNoiseTestBuffer   = Constants.name + "-";
		public static final String renderImplementationTag = Constants.perlinNoiseTestBuffer + "renderImplementationTag";
	}
	
	private PerlinNoiseTextureGenerator perlinNoiseTextureGenerator;
	private TwoDRShaderProgram shaderProgram;
	private int vertexArrayID;
	
	public PerlinNoiseTestRenderer(PerlinNoiseTextureGenerator perlinNoiseTextureGenerator, TwoDRShaderProgram twoDRShaderProgram) {
		super(Constants.name, new RendererBase[] {perlinNoiseTextureGenerator}, twoDRShaderProgram);
		
		this.perlinNoiseTextureGenerator = perlinNoiseTextureGenerator;
		this.shaderProgram = twoDRShaderProgram;
	}
	
	public PerlinNoiseTestRenderer() {
		this(
				new PerlinNoiseTextureGenerator(Constants.textureGenerator, Constants.numberOfOctaves, Constants.persistence, Constants.scaleX, Constants.scaleY, Constants.textureWidth, Constants.textureHeight),
				new TwoDRShaderProgram()
		);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenVertexArrays(1, intBuffer);
		this.vertexArrayID = intBuffer.get(0);
	}
	
	@Override
	public void destroy(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.vertexArrayID);
		intBuffer.rewind();
		gl.glDeleteVertexArrays(1, intBuffer);
		super.destroy(gl);
	}
	
	@Override
	protected void preRender(GL4 gl) {
	
	}
	
	@Override
	protected void postRender(GL4 gl) {
	
	}
	
	@Override
	public void renderImplementation(GL4 gl) {
		super.renderImplementation(gl);
		
		gl.glUseProgram(this.shaderProgram.getProgramID());
		gl.glBindVertexArray(this.vertexArrayID);
		
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, this.perlinNoiseTextureGenerator.getPerlinNoiseTexture().getTextureID());
		
		int samplerLocation = this.shaderProgram.getSampleUniformLocation();
		gl.glUniform1i(samplerLocation, 0);
		
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
		
		gl.glBindVertexArray(0);
		gl.glUseProgram(0);
		
		this.checkForErrors(gl, Constants.renderImplementationTag);
	}
}

package tests.perlinNoiseTest;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;
import perlinNoise.perlinNoiseTextureGenerator.PerlinNoiseTextureGenerator;
import tests.perlinNoiseTest.twoDRShaderProgram.TwoDRShaderProgram;

import java.nio.IntBuffer;

public class PerlinNoiseTestFrameBuffer extends PerlinNoiseTextureGenerator {
	private static class Constants {
		public static final String name                  = "perlinNoiseTestFrameBuffer";
		public static final int    numberOfOctaves       = 3;
		public static final float  persistence           = 0.3f;
		public static final int    textureWidth          = 256;
		public static final int    textureHeight         = 256;
		public static final float  scaleX                = 8.0f / textureWidth;
		public static final float  scaleY                = 8.0f / textureHeight;
		public static final String perlinNoiseTestBuffer = Constants.name + "-";
		public static final String renderTag             = Constants.perlinNoiseTestBuffer + "renderTag";
	}
	
	private TwoDRShaderProgram shaderProgram;
	private int vertexArrayID;
	
	public PerlinNoiseTestFrameBuffer() {
		super(Constants.name, Constants.numberOfOctaves, Constants.persistence, Constants.scaleX, Constants.scaleY, Constants.textureWidth, Constants.textureHeight);
		
		this.shaderProgram = new TwoDRShaderProgram();
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		System.out.println(this.shaderProgram.build(gl));
		
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenVertexArrays(1, intBuffer);
		this.vertexArrayID = intBuffer.get(0);
	}
	
	@Override
	public void destroy(GL4 gl) {
		this.shaderProgram.delete(gl);
		
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.vertexArrayID);
		intBuffer.rewind();
		gl.glDeleteVertexArrays(1, intBuffer);
		super.destroy(gl);
	}
	
	@Override
	public void render(GL4 gl) {
		super.render(gl);
		
		gl.glUseProgram(this.shaderProgram.getProgramID());
		gl.glBindVertexArray(this.vertexArrayID);
		
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, super.getPerlinNoiseTextureID());
		
		int samplerLocation = gl.glGetUniformLocation(this.shaderProgram.getProgramID(), TwoDRShaderProgram.Uniforms.sampler);
		gl.glUniform1i(samplerLocation, 0);
		
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
		
		gl.glBindVertexArray(0);
		gl.glUseProgram(0);
		
		this.checkForErrors(gl, Constants.renderTag);
	}
}

package tests.renderTextureTest.textureGenerator;

import com.jogamp.opengl.GL4;
import tests.renderTextureTest.textureGenerator.shaderPrograms.heightMapShaderProgam.HeightMapShaderProgram;
import tests.renderTextureTest.textureGenerator.shaderPrograms.normalMapShaderProgam.NormalMapShaderProgram;
import water.heightMapAndNormalMap.HeightMapAndNormalMapGenerator;

import java.nio.IntBuffer;

public class TextureGenerator extends HeightMapAndNormalMapGenerator {
	protected static class Constants {
		public static final String name                         = "TextureGenerator";
		public static final int    width                        = 256;
		public static final int    height                       = 256;
		public static final String textureGenerator             = "textureGenerator-";
		public static final String initTag                      = Constants.textureGenerator + "init";
		public static final String setupHeightMapUniformsTag    = Constants.textureGenerator + "setupHeightMapUniformsTag";
		public static final String setupHeightMapDrawBuffersTag = Constants.textureGenerator + "setupHeightMapDrawBuffersTag";
		public static final String setupNormalMapUniformsTag    = Constants.textureGenerator + "setupHeightMapUniformsTag";
		public static final String setupNormalMapDrawBuffersTag = Constants.textureGenerator + "setupHeightMapDrawBuffersTag";
	}
	
	private int						heightMapTextureID, normalMapTextureID;
	private IntBuffer				heightMapDrawBuffers, normalMapDrawBuffers;
	private HeightMapShaderProgram	heightMapShaderProgram;
	private NormalMapShaderProgram	normalMapShaderProgram;
	
	public TextureGenerator(HeightMapShaderProgram heightMapShaderProgram, NormalMapShaderProgram normalMapShaderProgram) {
		super(Constants.name, heightMapShaderProgram, normalMapShaderProgram, Constants.width, Constants.height);
		this.heightMapShaderProgram = heightMapShaderProgram;
		this.normalMapShaderProgram = normalMapShaderProgram;
	}
	
	public TextureGenerator() {
		this(new HeightMapShaderProgram(), new NormalMapShaderProgram());
	}
	
	@Override
	protected void initializeFrameBuffer(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, intBuffer);
		this.heightMapTextureID = intBuffer.get(0);
		gl.glBindTexture(GL4.GL_TEXTURE_2D, this.heightMapTextureID);
		gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, GL4.GL_RGBA, this.width, this.height, 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, null);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT);
		
		intBuffer.rewind();
		gl.glGenTextures(1, intBuffer);
		this.normalMapTextureID = intBuffer.get(0);
		gl.glBindTexture(GL4.GL_TEXTURE_2D, this.normalMapTextureID);
		gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, GL4.GL_RGBA, this.width, this.height, 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, null);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT);
		
		gl.glFramebufferTexture2D(GL4.GL_FRAMEBUFFER, GL4.GL_COLOR_ATTACHMENT0, GL4.GL_TEXTURE_2D, this.heightMapTextureID, 0);
		gl.glFramebufferTexture2D(GL4.GL_FRAMEBUFFER, GL4.GL_COLOR_ATTACHMENT1, GL4.GL_TEXTURE_2D, this.normalMapTextureID, 0);
		
		this.heightMapDrawBuffers = IntBuffer.allocate(1);
		this.heightMapDrawBuffers.put(GL4.GL_COLOR_ATTACHMENT0);
		this.heightMapDrawBuffers.rewind();
		
		this.normalMapDrawBuffers = IntBuffer.allocate(1);
		this.normalMapDrawBuffers.put(GL4.GL_COLOR_ATTACHMENT1);
		this.normalMapDrawBuffers.rewind();
		
		super.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(2);
		
		intBuffer.put(this.heightMapTextureID);
		intBuffer.put(this.normalMapTextureID);
		intBuffer.rewind();
		gl.glDeleteTextures(2, intBuffer);
		
		super.destroy(gl);
	}
	
	@Override
	protected void setupHeightMapUniforms(GL4 gl) {
		gl.glUniform1f(gl.glGetUniformLocation(this.heightMapShaderProgram.getProgramID(), HeightMapShaderProgram.Uniforms.width), this.width);
		gl.glUniform1f(gl.glGetUniformLocation(this.heightMapShaderProgram.getProgramID(), HeightMapShaderProgram.Uniforms.height), this.height);
		super.checkForErrors(gl, Constants.setupHeightMapUniformsTag);
	}
	
	@Override
	protected void setupHeightMapDrawBuffers(GL4 gl) {
		gl.glDrawBuffers(1, this.heightMapDrawBuffers);
		this.heightMapDrawBuffers.rewind();
		super.checkForErrors(gl, Constants.setupHeightMapDrawBuffersTag);
	}
	
	@Override
	protected void setupNormalMapUniforms(GL4 gl) {
		gl.glUniform1f(gl.glGetUniformLocation(this.normalMapShaderProgram.getProgramID(), NormalMapShaderProgram.Uniforms.width), this.width);
		gl.glUniform1f(gl.glGetUniformLocation(this.normalMapShaderProgram.getProgramID(), NormalMapShaderProgram.Uniforms.height), this.height);
		super.checkForErrors(gl, Constants.setupNormalMapUniformsTag);
	}
	
	@Override
	protected void setupNormalMapDrawBuffers(GL4 gl) {
		gl.glDrawBuffers(1, this.normalMapDrawBuffers);
		this.normalMapDrawBuffers.rewind();
		super.checkForErrors(gl, Constants.setupNormalMapDrawBuffersTag);
	}
	
	public int getHeightMapTextureID() {
		return this.heightMapTextureID;
	}
	
	public int getNormalTextureID() {
		return this.normalMapTextureID;
	}
}

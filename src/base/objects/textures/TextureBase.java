package base.objects.textures;

import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.nio.Buffer;
import java.nio.IntBuffer;

public abstract class TextureBase extends OpenGLObject {
	private static class Constants {
		public static final String textureBase = "textureBase-";
		public static final String initTag = Constants.textureBase + "init";
		public static final String destroyTag = Constants.textureBase + "destroy";
		public static final String bindTag = Constants.textureBase + "bind";
		public static final String setParameterTag = Constants.textureBase + "setParameter";
		public static final String texImage2DTag = Constants.textureBase + "texImage2D";
	}
	
	
	protected int textureID;
	protected int target;
	
	public TextureBase(String name, int target) {
		super(name);
		this.target = target;
	}
	
	public int getTextureID() {
		return textureID;
	}
	
	@Override
	public void init(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, intBuffer);
		this.textureID = intBuffer.get(0);
		
		super.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.textureID);
		intBuffer.rewind();
		gl.glDeleteTextures(1, intBuffer);
		
		super.checkForErrors(gl, Constants.destroyTag);
	}
	
	public TextureBase bind(GL4 gl) {
		gl.glBindTexture(this.target, this.textureID);
		super.checkForErrors(gl, Constants.bindTag);
		return this;
	}
	
	public TextureBase setTextureParameter(GL4 gl, int parameterName, int parameterValue) {
		gl.glTexParameteri(this.target, parameterName, parameterValue);
		this.checkForErrors(gl, Constants.setParameterTag);
		return this;
	}
	
	protected TextureBase texImage2D(GL4 gl, int target, int level, int internalFormat, int width, int height, int format, int type, Buffer data) {
		gl.glTexImage2D(target, level, internalFormat, width, height, 0, format, type, data);
		this.checkForErrors(gl, Constants.texImage2DTag);
		return this;
	}
	
	protected TextureBase texImage2D(GL4 gl, int target, int level, int internalFormat, int format, TextureData textureData) {
		return this.texImage2D(gl, target, level, internalFormat, textureData.getWidth(), textureData.getHeight(), format, GL4.GL_UNSIGNED_BYTE, textureData.getData());
	}
}

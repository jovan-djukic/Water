package base.objects.textures;

import base.objects.OpenGLObject;
import com.jogamp.opengl.GL4;

import java.nio.Buffer;
import java.nio.IntBuffer;

public abstract class TextureBase extends OpenGLObject {
	private static class Constants {
		public static final String textureBase     = "textureBase";
		public static final String initTag         = Constants.textureBase + "-init";
		public static final String destroyTag      = Constants.textureBase + "-destroy";
		public static final String bindTag         = Constants.textureBase + "-bind";
		public static final String setParameterTag = Constants.textureBase + "-setParameter";
		public static final String texImage2DTag   = Constants.textureBase + "-texImage2D";
	}
	
	
	protected int textureID;
	protected int target;
	private int internalFormat, format, type;
	
	public TextureBase(String name, int target, int internalFormat, int format, int type) {
		super(name);
		this.target = target;
		this.internalFormat = internalFormat;
		this.format = format;
		this.type = type;
	}
	
	public int getTextureID() {
		return textureID;
	}
	
	public int getTarget() {
		return target;
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, intBuffer);
		this.textureID = intBuffer.get(0);
		
		super.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		super.destroy(gl);
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
	
	public TextureBase texParameteri(GL4 gl, int parameterName, int parameterValue) {
		gl.glTexParameteri(this.target, parameterName, parameterValue);
		this.checkForErrors(gl, Constants.setParameterTag);
		return this;
	}
	
	protected TextureBase texImage2D(GL4 gl, int target, int level, int width, int height, Buffer data) {
		gl.glTexImage2D(target, level, this.internalFormat, width, height, 0, this.format, this.type, data);
		this.checkForErrors(gl, Constants.texImage2DTag);
		return this;
	}
	
	protected TextureBase texImage2D(GL4 gl, int target, int level, TextureData textureData) {
		return this.texImage2D(gl, target, level, textureData.getWidth(), textureData.getHeight(), textureData.getData());
	}
}

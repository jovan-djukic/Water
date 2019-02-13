package base.objects.textures;

import com.jogamp.opengl.GL4;

import java.nio.Buffer;

public class CubeMapTexture extends TextureBase {
	
	public CubeMapTexture(String name, int internalFormat, int format, int type) {
		super(name, GL4.GL_TEXTURE_CUBE_MAP, internalFormat, format, type);
	}
	
	public CubeMapTexture bufferXPositive(GL4 gl, int level, int width, int height, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_X, level, width, height, data);
	}
	
	public CubeMapTexture bufferXPositive(GL4 gl, int level,  TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_X, level, textureData);
	}
	
	public CubeMapTexture bufferXNegative(GL4 gl, int level, int width, int height, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, level, width, height, data);
	}
	
	public CubeMapTexture bufferXNegative(GL4 gl, int level, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, level, textureData);
	}
	public CubeMapTexture bufferYPositive(GL4 gl, int level, int width, int height, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, level, width, height, data);
	}
	
	public CubeMapTexture bufferYPositive(GL4 gl, int level, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, level, textureData);
	}
	public CubeMapTexture bufferYNegative(GL4 gl, int level, int width, int height, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, level, width, height, data);
	}
	
	public CubeMapTexture bufferYNegative(GL4 gl, int level, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, level, textureData);
	}
	
	public CubeMapTexture bufferZPositive(GL4 gl, int level, int width, int height, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, level, width, height, data);
	}
	
	public CubeMapTexture bufferZPositive(GL4 gl, int level, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, level, textureData);
	}
	
	public CubeMapTexture bufferZNegative(GL4 gl, int level, int width, int height, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, level, width, height, data);
	}
	
	public CubeMapTexture bufferZNegative(GL4 gl, int level, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, level, textureData);
	}
	
	@Override
	public CubeMapTexture bind(GL4 gl) {
		return (CubeMapTexture) super.bind(gl);
	}
}

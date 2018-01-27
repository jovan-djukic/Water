package base.objects.textures;

import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.nio.Buffer;

public class CubeMapTexture extends TextureBase {
	
	public CubeMapTexture(String name) {
		super(name, GL4.GL_TEXTURE_CUBE_MAP);
	}
	
	public CubeMapTexture bufferXPositive(GL4 gl, int level, int internalFormat, int width, int height, int format, int type, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_X, level, internalFormat, width, height, format, type, data);
	}
	
	public CubeMapTexture bufferXPositive(GL4 gl, int level, int internalFormat, int format, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_X, level, internalFormat, format, textureData);
	}
	
	public CubeMapTexture bufferXNegative(GL4 gl, int level, int internalFormat, int width, int height, int format, int type, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, level, internalFormat, width, height, format, type, data);
	}
	
	public CubeMapTexture bufferXNegative(GL4 gl, int level, int internalFormat, int format, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, level, internalFormat, format, textureData);
	}
	public CubeMapTexture bufferYPositive(GL4 gl, int level, int internalFormat, int width, int height, int format, int type, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, level, internalFormat, width, height, format, type, data);
	}
	
	public CubeMapTexture bufferYPositive(GL4 gl, int level, int internalFormat, int format, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, level, internalFormat, format, textureData);
	}
	public CubeMapTexture bufferYNegative(GL4 gl, int level, int internalFormat, int width, int height, int format, int type, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, level, internalFormat, width, height, format, type, data);
	}
	
	public CubeMapTexture bufferYNegative(GL4 gl, int level, int internalFormat, int format, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, level, internalFormat, format, textureData);
	}
	
	public CubeMapTexture bufferZPositive(GL4 gl, int level, int internalFormat, int width, int height, int format, int type, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, level, internalFormat, width, height, format, type, data);
	}
	
	public CubeMapTexture bufferZPositive(GL4 gl, int level, int internalFormat, int format, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, level, internalFormat, format, textureData);
	}
	
	public CubeMapTexture bufferZNegative(GL4 gl, int level, int internalFormat, int width, int height, int format, int type, Buffer data) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, level, internalFormat, width, height, format, type, data);
	}
	
	public CubeMapTexture bufferZNegative(GL4 gl, int level, int internalFormat, int format, TextureData textureData) {
		return (CubeMapTexture) super.texImage2D(gl, GL4.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, level, internalFormat, format, textureData);
	}
	
	@Override
	public CubeMapTexture bind(GL4 gl) {
		return (CubeMapTexture) super.bind(gl);
	}
}

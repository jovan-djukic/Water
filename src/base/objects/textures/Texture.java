package base.objects.textures;

import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.nio.Buffer;

public class Texture extends TextureBase {
	private int width, height;
	
	public Texture(String name) {
		super(name, GL4.GL_TEXTURE_2D);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	@Override
	public Texture texImage2D(GL4 gl, int target, int level, int internalFormat, int width, int height, int format, int type, Buffer data) {
		this.width = width;
		this.height = height;
		return (Texture) super.texImage2D(gl, target, level, internalFormat, width, height, format, type, data);
	}
	
	@Override
	public Texture texImage2D(GL4 gl, int target, int level, int internalFormat, int format, TextureData textureData) {
		return (Texture) super.texImage2D(gl, target, level, internalFormat, format, textureData);
	}
	
	@Override
	public Texture bind(GL4 gl) {
		return (Texture) super.bind(gl);
	}
}

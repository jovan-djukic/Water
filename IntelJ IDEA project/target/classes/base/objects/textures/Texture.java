package base.objects.textures;

import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.nio.Buffer;

public class Texture extends TextureBase {
	private int width, height;
	
	public Texture(String name, int internalFormat, int format, int type) {
		super(name, GL4.GL_TEXTURE_2D, internalFormat, format, type);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Texture texImage2D(GL4 gl, int level, int width, int height, Buffer data) {
		this.width = width;
		this.height = height;
		return (Texture) super.texImage2D(gl, super.getTarget(), level, width, height, data);
	}
	
	public Texture texImage2D(GL4 gl, int level,  TextureData textureData) {
		return (Texture) super.texImage2D(gl, super.getTarget(), level, textureData);
	}
	
	@Override
	public Texture bind(GL4 gl) {
		return (Texture) super.bind(gl);
	}
}

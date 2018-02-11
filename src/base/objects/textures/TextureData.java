package base.objects.textures;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class TextureData {
	
	public static TextureData decodePngImage(Class scope, String fileName, PNGDecoder.Format format) {
		TextureData textureData = null;
		try (InputStream inputStream = scope.getResourceAsStream(fileName)) {
			PNGDecoder pngDecoder = new PNGDecoder(inputStream);
			int width = pngDecoder.getWidth();
			int height = pngDecoder.getHeight();
			ByteBuffer byteBuffer = ByteBuffer.allocate(4 * width * height);
			pngDecoder.decode(byteBuffer, 4 * width, format);
			byteBuffer.flip();
			
			textureData = new TextureData(width, height, byteBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return textureData;
	}
	
	private int width, height;
	private Buffer data;
	
	public TextureData(int width, int height, Buffer data) {
		this.width = width;
		this.height = height;
		this.data = data;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Buffer getData() {
		return data;
	}
}

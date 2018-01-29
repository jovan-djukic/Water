package base.objects.model.loaders;

import base.objects.model.Loader;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

import java.nio.FloatBuffer;

public abstract class TextureCoordinatesLoader extends Loader {
	protected static class Constants {
		public static final String textureCoordinatesLoader = "vertexPositionLoader-";
		public static final String initTag                  = Constants.textureCoordinatesLoader + "init";
	}
	
	private int textureCoordinatesAttributeLocation;
	
	public TextureCoordinatesLoader(String name, int textureCoordinatesAttributeLocation) {
		super(name);
		
		this.textureCoordinatesAttributeLocation = textureCoordinatesAttributeLocation;
	}
	
	protected abstract float[] getTextureCoordinatesData();
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		float       textureCoordinatesData[]     = this.getTextureCoordinatesData();
		FloatBuffer textureCoordinatesDataBuffer = Buffers.newDirectFloatBuffer(textureCoordinatesData);
		
		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, super.getBufferID());
		gl.glBufferData(GL4.GL_ARRAY_BUFFER, textureCoordinatesData.length * Float.BYTES, textureCoordinatesDataBuffer, GL4.GL_STATIC_DRAW);
		gl.glVertexAttribPointer(this.textureCoordinatesAttributeLocation, 2, GL4.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(this.textureCoordinatesAttributeLocation);
		
		this.checkForErrors(gl, Constants.initTag);
	}
}

package base.objects.model.loaders;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import org.joml.Vector3f;

import java.nio.IntBuffer;

public abstract class IndicesLoader extends VerticesLoader {
	
	private int indexBufferIDPosition;
	
	protected IndicesLoader(String name, int numberOfBuffers, int indexBufferIDPosition, Vector3f position, int vertexPositionAttributeLocation) {
		super(name, numberOfBuffers, position, vertexPositionAttributeLocation);
		
		this.indexBufferIDPosition = indexBufferIDPosition;
	}
	
	protected IndicesLoader(String name, int numberOfBuffers, int indexBufferIDPosition, float x, float y, float z, int vertexPositionAttributeLocation) {
		this(name, numberOfBuffers, indexBufferIDPosition, new Vector3f(x, y, z), vertexPositionAttributeLocation);
	}
	
	protected static class Constants {
		public static final String indicesLoader = "vertexPositionLoader-";
		public static final String initTag       = Constants.indicesLoader + "init";
	}

	protected abstract int[] getIndicesData();
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		int       indexData[]     = this.getIndicesData();
		IntBuffer indexDataBuffer = Buffers.newDirectIntBuffer(indexData);
		
		gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, super.getBufferID(this.indexBufferIDPosition));
		gl.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indexData.length * Integer.BYTES, indexDataBuffer, GL4.GL_STATIC_DRAW);
		
		this.checkForErrors(gl, Constants.initTag);
	}
}

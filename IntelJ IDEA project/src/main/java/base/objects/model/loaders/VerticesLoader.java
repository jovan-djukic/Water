package base.objects.model.loaders;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import org.joml.Vector3f;

import java.nio.FloatBuffer;

public abstract class VerticesLoader extends Loader {
	protected static class Constants {
		public static final String vertexPositionLoader = "vertexPositionLoader-";
		public static final String initTag              = Constants.vertexPositionLoader + "init";
	}
	
	private Vector3f position;
	private int vertexPositionAttributeLocation;
	
	protected VerticesLoader(String name, int numberOfBuffers, Vector3f position, int vertexPositionAttributeLocation) {
		super(name, numberOfBuffers);
		
		this.position = position;
		this.vertexPositionAttributeLocation = vertexPositionAttributeLocation;
	}
	
	protected VerticesLoader(String name, int numberOfBuffers, float x, float y, float z, int vertexPositionAttributeLocation) {
		this(name, numberOfBuffers, new Vector3f(x, y, z), vertexPositionAttributeLocation);
	}
	
	protected VerticesLoader(String name, Vector3f position, int vertexPositionAttributeLocation) {
		this(name, 1, position, vertexPositionAttributeLocation);
	}
	
	protected VerticesLoader(String name, float x, float y, float z, int vertexPositionAttributeLocation) {
		this(name, 1, new Vector3f(x, y, z), vertexPositionAttributeLocation);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	protected abstract float[] getVertexData();
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		float       vertexData[]     = this.getVertexData();
		FloatBuffer vertexDataBuffer = Buffers.newDirectFloatBuffer(vertexData);
		
		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, super.getBufferID());
		gl.glBufferData(GL4.GL_ARRAY_BUFFER, vertexData.length * Float.BYTES, vertexDataBuffer, GL4.GL_STATIC_DRAW);
		gl.glVertexAttribPointer(this.vertexPositionAttributeLocation, 3, GL4.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(this.vertexPositionAttributeLocation);
		
		this.checkForErrors(gl, Constants.initTag);
	}
}

package shapes;

import base.objects.model.loaders.Loader;
import base.objects.model.Model;
import base.objects.model.loaders.IndicesLoader;
import base.objects.model.loaders.VerticesLoader;
import com.jogamp.opengl.GL4;
import org.joml.Vector3f;

import java.nio.IntBuffer;

public class Box extends Model {
	
	private static class Constants {
		public static final String box                  = "box-";
		public static final String drawCommandTag       = Constants.box + "drawCommand";
		public static final String verticesLoader = "-verticesLoader";
		public static final String indicesLoader        = "-indicesLoader";
	}
	
	protected static class BoxVerticesLoader extends IndicesLoader {
		private static class Constants {
			public static final int numberOfVertices      = 8;
			public static final int numberOfBuffers       = 2;
			public static final int indexBufferIDPosition = 1;
		}
		
		private float width, height, depth;
		
		protected BoxVerticesLoader(String name, Vector3f position, int vertexPositionAttributeLocation, float width, float height, float depth) {
			super(name, Constants.numberOfBuffers, Constants.indexBufferIDPosition, position, vertexPositionAttributeLocation);
			
			this.width = width;
			this.height = height;
			this.depth = depth;
		}
		
		@Override
		protected float[] getVertexData() {
			float vertexData[]  = new float[Constants.numberOfVertices * 3];
			int   verticesIndex = 0;
			
			float x = super.getPosition().x;
			float y = super.getPosition().y;
			float z = super.getPosition().z;
			
			vertexData[verticesIndex++] = x + this.width / 2;
			vertexData[verticesIndex++] = y - this.height / 2;
			vertexData[verticesIndex++] = z + this.depth / 2;
			
			vertexData[verticesIndex++] = x + this.width / 2;
			vertexData[verticesIndex++] = y - this.height / 2;
			vertexData[verticesIndex++] = z - this.depth / 2;
			
			vertexData[verticesIndex++] = x - this.width / 2;
			vertexData[verticesIndex++] = y - this.height / 2;
			vertexData[verticesIndex++] = z - this.depth / 2;
			
			vertexData[verticesIndex++] = x - this.width / 2;
			vertexData[verticesIndex++] = y - this.height / 2;
			vertexData[verticesIndex++] = z + this.depth / 2;
			
			vertexData[verticesIndex++] = x + this.width / 2;
			vertexData[verticesIndex++] = y + this.height / 2;
			vertexData[verticesIndex++] = z + this.depth / 2;
			
			vertexData[verticesIndex++] = x + this.width / 2;
			vertexData[verticesIndex++] = y + this.height / 2;
			vertexData[verticesIndex++] = z - this.depth / 2;
			
			vertexData[verticesIndex++] = x - this.width / 2;
			vertexData[verticesIndex++] = y + this.height / 2;
			vertexData[verticesIndex++] = z - this.depth / 2;
			
			vertexData[verticesIndex++] = x - this.width / 2;
			vertexData[verticesIndex++] = y + this.height / 2;
			vertexData[verticesIndex++] = z + this.depth / 2;
			
			return vertexData;
		}
		
		@Override
		protected int[] getIndicesData() {
			int indexData[] = {
					0, 3, 1,
					1, 3, 2,
					0, 1, 5,
					0, 5, 4,
					1, 2, 6,
					1, 6, 5,
					2, 3, 7,
					2, 7, 6,
					3, 0, 4,
					3, 4, 7,
					7, 4, 5,
					7, 5, 6
			};
			
			return indexData;
		}
		
	}
	
	private float width, height, depth;
	private boolean   isCullFaceEnabled;
	private IntBuffer cullFaceMode;
	
	protected Box(String name, Loader... loaders) {
		super(name, loaders);
	}
	
	public Box(String name, Vector3f position, float width, float height, float depth, int vertexPositionAttributeLocation) {
		super(
				name,
				new BoxVerticesLoader(name + Constants.verticesLoader, position, vertexPositionAttributeLocation, width, height, depth)
		);
	}
	
	public Box(String name, float x, float y, float z, float width, float height, float depth, int vertexPositionAttributeLocation) {
		this(name, new Vector3f(x, y, z), width, height, depth, vertexPositionAttributeLocation);
	}
	
	public Box(String name, float width, float height, float depth, int vertexPositionAttributeLocation) {
		this(name, 0, 0, 0, width, height, depth, vertexPositionAttributeLocation);
	}
	
	@Override
	protected void drawCommand(GL4 gl) {
		gl.glDrawElements(GL4.GL_TRIANGLES, 36, GL4.GL_UNSIGNED_INT, 0);
		
		this.checkForErrors(gl, Constants.drawCommandTag);
	}
	
}

package shapes;

import base.objects.model.Loader;
import base.objects.model.Model;
import base.objects.model.loaders.IndicesLoader;
import base.objects.model.loaders.TextureCoordinatesLoader;
import base.objects.model.loaders.VertexPositionLoader;
import com.jogamp.opengl.GL4;

import org.joml.Vector3f;

public class Mesh extends Model {
	private static class Constants {
		public static final String dash                     = "-";
		public static final String vertexPositionLoader     = Constants.dash + "vertexPositionLoader";
		public static final String indicesLoader            = Constants.dash + "indicesLoader";
		public static final String textureCoordinatesLoader = Constants.dash + "textureCoordinatesLoader";
		public static final String mesh                     = "mesh" + Constants.dash;
		public static final String drawCommandTag           = Constants.mesh + "drawCommand";
	}
	
	protected static class MeshVertexPositionLoader extends VertexPositionLoader {
		
		private float width, height;
		private int rows, columns;
		
		public MeshVertexPositionLoader(String name, float x, float z, int vertexPositionAttributeLocation, float width, float height, int rows, int columns) {
			super(name, new Vector3f(x, 0, z), vertexPositionAttributeLocation);
			
			this.width = width;
			this.height = height;
			this.rows = rows + 1;
			this.columns = columns + 1;
		}
		
		public float getWidth() {
			return width;
		}
		
		public float getHeight() {
			return height;
		}
		
		public int getRows() {
			return rows;
		}
		
		public int getColumns() {
			return columns;
		}
		
		protected float getX(int row, int column) {
			return super.getPosition().x + this.width / (this.columns - 1) * column;
		}
		
		protected float getY(int row, int column) {
			return 0;
		}
		
		protected float getZ(int row, int column) {
			return super.getPosition().z - this.height / (this.rows - 1) * row;
		}
		
		@Override
		protected float[] getVertexData() {
			float vertices[]    = new float[this.rows * this.columns * 3];
			int   verticesIndex = 0;
			
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.columns; j++) {
					vertices[verticesIndex++] = this.getX(i, j);
					vertices[verticesIndex++] = this.getY(i, j);
					vertices[verticesIndex++] = this.getZ(i, j);
				}
			}
			
			return vertices;
		}
	}
	
	protected static class MeshIndicesLoader extends IndicesLoader {
		
		private int rows, columns;
		
		public MeshIndicesLoader(String name, int rows, int columns) {
			super(name);
			
			this.rows = rows;
			this.columns = columns;
		}
		
		public int getIndicesCount() {
			return rows * columns;
		}
		
		@Override
		protected int[] getIndicesData() {
			int triangleCount = this.rows * this.columns * 2;
			int indicesCount = triangleCount * 3;
			int indices[] = new int[indicesCount];
			int indicesIndex = 0;
			
			for (int i = 0; i < this.rows; i++) {
				boolean topDown = (i & 1) == 0 ? true : false;
				
				for (int j = 0; j < this.columns; j++) {
					int v0 = i * (this.columns + 1) + j;
					int v1 = v0 + 1;
					int v2 = v0 + this.columns + 1;
					int v3 = v2 + 1;
					
					if (topDown == true) {
						indices[indicesIndex++] = v0;
						indices[indicesIndex++] = v1;
						indices[indicesIndex++] = v3;
						
						indices[indicesIndex++] = v0;
						indices[indicesIndex++] = v3;
						indices[indicesIndex++] = v2;
					} else {
						indices[indicesIndex++] = v0;
						indices[indicesIndex++] = v1;
						indices[indicesIndex++] = v2;
						
						indices[indicesIndex++] = v1;
						indices[indicesIndex++] = v3;
						indices[indicesIndex++] = v2;
					}
					
					topDown = !topDown;
				}
			}
			
			return indices;
		}
	}
	
	protected static class MeshTextureCoordinatesLoader extends TextureCoordinatesLoader {
		
		private int rows, columns;
		private float scaleU, scaleV;
		
		public MeshTextureCoordinatesLoader(String name, int textureCoordinatesAttributeLocation, int rows, int columns, float scaleU, float scaleV) {
			super(name, textureCoordinatesAttributeLocation);
			
			this.rows = rows + 1;
			this.columns = columns + 1;
			this.scaleU = scaleU;
			this.scaleV = scaleV;
		}
		
		@Override
		protected float[] getTextureCoordinatesData() {
			float textureCoordinates[]    = new float[this.rows * this.columns * 2];
			int   textureCoordinatesIndex = 0;
			
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.columns; j++) {
					float u = (float) i / (this.rows - 1) * this.scaleU;
					float v = (float) j / (this.columns - 1) * this.scaleV;
					
					textureCoordinates[textureCoordinatesIndex++] = u;
					textureCoordinates[textureCoordinatesIndex++] = v;
				}
			}
			
			return textureCoordinates;
		}
	}
	
	private int indicesCount;
	
	protected Mesh(String name, int indicesCount, Loader...loaders) {
		super(name, loaders);
		
		this.indicesCount = indicesCount;
	}
	
	public Mesh(String name, float x, float z, float width, float height, int rows, int columns, float scaleU, float scaleV, int vertexPositionAttributeLocation, int textureCoordinatesAttributeLocation) {
		super(
				name,
				new MeshVertexPositionLoader(name + Constants.vertexPositionLoader, x, z, vertexPositionAttributeLocation, width, height, rows, columns),
				new MeshIndicesLoader(name + Constants.indicesLoader, rows, columns),
				new MeshTextureCoordinatesLoader(name + Constants.textureCoordinatesLoader, textureCoordinatesAttributeLocation, rows, columns, scaleU, scaleV)
		);
		
		this.indicesCount = rows * columns * 6;
	}
	
	public Mesh(String name, float x, float z, float width, float height, int rows, int columns, int vertexPositionAttributeLocation) {
		super(
				name,
				new MeshVertexPositionLoader(name + Constants.vertexPositionLoader, x, z, vertexPositionAttributeLocation, width, height, rows, columns),
				new MeshIndicesLoader(name + Constants.indicesLoader, rows, columns)
		);
		
		this.indicesCount = rows * columns * 6;
	}
	
	@Override
	public void drawCommand(GL4 gl) {
		gl.glDrawElements(GL4.GL_TRIANGLES, this.indicesCount, GL4.GL_UNSIGNED_INT, 0);
		this.checkForErrors(gl, Constants.drawCommandTag);
	}
}

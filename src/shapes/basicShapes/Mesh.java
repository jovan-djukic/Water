package shapes.basicShapes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;

import base.objects.scene.SceneObject;
import base.objects.scene.SceneShaderProgram;

public class Mesh extends SceneObject {
	
	private static class Constants {
		public static final String mesh                         = "mesh-";
		public static final String bufferTextureCoordinatesTag  = Constants.mesh + "bufferTextureCoordinates";
		public static final String preRenderTag                 = Constants.mesh + "preRender";
		public static final String setPolygonModeTag            = Constants.mesh + "polygonMode";
		public static final String drawCommandTag               = Constants.mesh + "drawCommand";
		public static final String destroyTextureCoordinatesTag = Constants.mesh + "destroyTextureCoordinates";
	}
	
	protected float					width, height, x, z;
	protected int					rows, columns;
	protected int					textureCoordinateBufferID;
	protected int					triangleCount, indicesCount;
	protected SceneShaderProgram	shaderProgram;
	
	public Mesh(String name, SceneShaderProgram shaderProgram, float x, float z, float width, float height, int rows, int columns) {
		super(name, shaderProgram, 0, 0, 0);
		
		this.x = x;
		this.z = z;
		
		this.shaderProgram = shaderProgram;
		
		this.width = width;
		this.height = height;
		this.rows = rows + 1;
		this.columns = columns + 1;
		
	}
	
	@Override
	protected void bufferTextureCoordinates(GL4 gl) {
		float textureCoordinates[] = new float[this.rows * this.columns * 2];
		int textureCoordinatesIndex = 0;
		
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				float x = this.x + this.width / (this.columns - 1) * j;
				float z = this.z + this.height / (this.rows - 1) * i;
				
				float u = Math.abs(x - this.x) / this.width;
				float v = Math.abs(z - this.z) / this.height;
				
				textureCoordinates[textureCoordinatesIndex++] = u;
				textureCoordinates[textureCoordinatesIndex++] = v;
			}
		}
		
		// Generate buffers
		FloatBuffer heightMapCoordinatesBuffer = Buffers.newDirectFloatBuffer(textureCoordinates);
		
		// buffer height map data
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenBuffers(1, intBuffer);
		this.textureCoordinateBufferID = intBuffer.get(0);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, this.textureCoordinateBufferID);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, textureCoordinates.length * Float.BYTES, heightMapCoordinatesBuffer, GL.GL_STATIC_DRAW);
		gl.glVertexAttribPointer(super.shaderProgram.getTextureCoordinateAttributePosition(), 2, GL4.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(super.shaderProgram.getTextureCoordinateAttributePosition());
		
		this.checkForErrors(gl, Constants.bufferTextureCoordinatesTag);
	}
	
	protected float getX(int row, int column) {
		return this.x + this.width / (this.columns - 1) * column;
	}
	
	protected float getY(int row, int column) {
		return 0;
	}
	
	protected float getZ(int row, int column) {
		return this.z + this.height / (this.rows - 1) * row;
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
	
	@Override
	protected boolean hasIndices() {
		return true;
	}
	
	@Override
	protected int[] getIndexData() {
		this.triangleCount = (this.rows - 1) * (this.columns - 1) * 2;
		this.indicesCount = this.triangleCount * 3;
		int indices[] = new int[indicesCount];
		int indicesIndex = 0;
		
		for (int i = 0; i < (this.rows - 1); i++) {
			boolean topDown = (i & 1) == 0 ? true : false;
			
			for (int j = 0; j < (this.columns - 1); j++) {
				int v0 = i * this.columns + j;
				int v1 = v0 + 1;
				int v2 = v0 + this.columns;
				int v3 = v2 + 1;
				
				if (topDown == true) {
					indices[indicesIndex++] = v0;
					indices[indicesIndex++] = v3;
					indices[indicesIndex++] = v1;
					
					indices[indicesIndex++] = v0;
					indices[indicesIndex++] = v2;
					indices[indicesIndex++] = v3;
				} else {
					indices[indicesIndex++] = v0;
					indices[indicesIndex++] = v2;
					indices[indicesIndex++] = v1;
					
					indices[indicesIndex++] = v1;
					indices[indicesIndex++] = v2;
					indices[indicesIndex++] = v3;
				}
				
				topDown = !topDown;
			}
		}
		
		return indices;
	}
	
	protected void setPolygonMode(GL4 gl) {
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_LINE);
		
		this.checkForErrors(gl, Constants.setPolygonModeTag);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.setPolygonMode(gl);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void drawCommand(GL4 gl) {
		gl.glDrawElements(GL4.GL_TRIANGLES, this.indicesCount, GL4.GL_UNSIGNED_INT, 0);
		this.checkForErrors(gl, Constants.drawCommandTag);
	}
	
	@Override
	protected void destroyTextureCoordinates(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.textureCoordinateBufferID);
		intBuffer.rewind();
		gl.glDeleteBuffers(1, intBuffer);
		
		this.checkForErrors(gl, Constants.destroyTextureCoordinatesTag);
		
		super.destroyTextureCoordinates(gl);
	}
	
	@Override
	public void update() {
	
	}
}

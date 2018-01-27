package base.objects.scene;

import base.objects.GraphicsObject;
import base.objects.IUpdateable;
import base.objects.OpenGLObject;
import com.jogamp.common.nio.Buffers;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.jogamp.opengl.GL4;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class SceneObject extends GraphicsObject implements IUpdateable {
	protected static class Constants {
		public static final String sceneObject                  = "sceneObject-";
		public static final String createDataStructuresTag      = Constants.sceneObject + "createDataStructures";
		public static final String bindDataStructuresTag        = Constants.sceneObject + "bindDataStructuresTag";
		public static final String unbindDataStructuresTag      = Constants.sceneObject + "unbindDataStructuresTag";
		public static final String bufferVerticesTag            = Constants.sceneObject + "bufferVertices";
		public static final String bufferIndicesTag             = Constants.sceneObject + "bufferIndices";
		public static final String preRenderTag                 = Constants.sceneObject + "preRender";
		public static final String postRenderTag                = Constants.sceneObject + "postRender";
		public static final String destroyDataStructuresTag     = Constants.sceneObject + "destroyDataStructures";
		public static final String destroyVerticesTag           = Constants.sceneObject + "destroyVertices";
		public static final String destroyIndicesTag            = Constants.sceneObject + "destroyIndices";
	}
	
	protected SceneShaderProgram shaderProgram;
	protected int                vertexArrayObjectID, vertexPositionBufferID, vertexIndexBufferID;
	protected Vector3f           position;
	protected Matrix4f           transform, resultingTransform;
	
	protected SceneObject(String name, SceneShaderProgram shaderProgram, Vector3f position) {
		super(name, shaderProgram);
		this.shaderProgram = shaderProgram;
		this.position = position;
		this.transform = new Matrix4f();
		this.transform.identity()
			.translate(this.position.x, this.position.y, this.position.z);
		
		this.resultingTransform = new Matrix4f();
		this.resultingTransform.identity();
	}
	
	protected SceneObject(String name, SceneShaderProgram shaderProgram, float x, float y, float z) {
		this(name, shaderProgram, new Vector3f(x, y, z));
	}
	
	protected void createDataStructures(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		
		gl.glGenVertexArrays(1, intBuffer);
		this.vertexArrayObjectID = intBuffer.get(0);
		
		this.checkForErrors(gl, Constants.createDataStructuresTag);
	}
	
	protected void bindDataStructures(GL4 gl) {
		gl.glBindVertexArray(this.vertexArrayObjectID);
		
		this.checkForErrors(gl, Constants.bindDataStructuresTag);
	}
	
	protected void unbindDataStructures(GL4 gl) {
		gl.glBindVertexArray(0);
		
		this.checkForErrors(gl, Constants.unbindDataStructuresTag);
	}
	
	protected boolean hasVertices() {
		return true;
	}
	
	protected float[] getVertexData() {
		return null;
	}
	
	protected boolean hasIndices() {
		return false;
	}
	
	protected int[] getIndexData() {
		return null;
	}
	
	protected void bufferVertices(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		
		gl.glGenBuffers(1, intBuffer);
		this.vertexPositionBufferID = intBuffer.get(0);
		
		float       vertexData[]     = this.getVertexData();
		FloatBuffer vertexDataBuffer = Buffers.newDirectFloatBuffer(vertexData);
		
		
		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, this.vertexPositionBufferID);
		gl.glBufferData(GL4.GL_ARRAY_BUFFER, vertexData.length * Float.BYTES, vertexDataBuffer, GL4.GL_STATIC_DRAW);
		gl.glVertexAttribPointer(this.shaderProgram.getVertexAttributePosition(), 3, GL4.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(this.shaderProgram.getVertexAttributePosition());
		
		this.checkForErrors(gl, Constants.bufferVerticesTag);
	}
	
	protected void bufferIndices(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenBuffers(1, intBuffer);
		this.vertexIndexBufferID = intBuffer.get(0);
		
		int       indexData[]     = this.getIndexData();
		IntBuffer indexDataBuffer = Buffers.newDirectIntBuffer(indexData);
		
		gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, this.vertexIndexBufferID);
		gl.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indexData.length * Integer.BYTES, indexDataBuffer, GL4.GL_STATIC_DRAW);
		
		this.checkForErrors(gl, Constants.bufferIndicesTag);
	}
	
	protected void bufferTextureCoordinates(GL4 gl) { }
	
	protected void bufferColor(GL4 gl) { }
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.createDataStructures(gl);
		
		this.bindDataStructures(gl);
		
		if (this.hasVertices() == true) {
			this.bufferVertices(gl);
		}
		
		if (this.hasIndices() == true) {
			this.bufferIndices(gl);
		}
		
		if (this.shaderProgram.hasColor() == true) {
			this.bufferColor(gl);
		}
		
		if (this.shaderProgram.hasTextureCoordinates() == true) {
			this.bufferTextureCoordinates(gl);
		}
		
		this.unbindDataStructures(gl);
	}
	
	protected void destroyVertices(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.vertexPositionBufferID);
		intBuffer.rewind();
		gl.glDeleteBuffers(1, intBuffer);
		
		this.checkForErrors(gl, Constants.destroyVerticesTag);
	}
	
	protected void destroyIndices(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.vertexIndexBufferID);
		intBuffer.rewind();
		gl.glDeleteBuffers(1, intBuffer);
		
		this.checkForErrors(gl, Constants.destroyIndicesTag);
	}
	
	protected void destroyColor(GL4 gl) { }
	
	protected void destroyTextureCoordinates(GL4 gl) { }
	
	protected void destroyDataStructures(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.vertexArrayObjectID);
		intBuffer.rewind();
		
		gl.glDeleteVertexArrays(1, intBuffer);
		
		this.checkForErrors(gl, Constants.destroyDataStructuresTag);
	}
	
	
	@Override
	public void destroy(GL4 gl) {
		
		if (this.hasVertices() == true) {
			this.destroyVertices(gl);
		}
		
		if (this.hasIndices() == true) {
			this.destroyIndices(gl);
		}
		
		if (this.shaderProgram.hasColor() == true) {
			this.destroyColor(gl);
		}
		
		if (this.shaderProgram.hasTextureCoordinates() == true) {
			this.destroyTextureCoordinates(gl);
		}
		
		this.destroyDataStructures(gl);
		
		super.destroy(gl);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.shaderProgram.applyTransform(gl, this.resultingTransform);
		gl.glBindVertexArray(this.vertexArrayObjectID);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		gl.glBindVertexArray(0);
		this.checkForErrors(gl, Constants.postRenderTag);
		super.postRender(gl);
	}
	
	public void render(GL4 gl, Matrix4f parentTransform) {
		if (super.shaderProgram != null) {
			parentTransform.mul(this.transform, this.resultingTransform);
			super.render(gl);
			this.resultingTransform.identity();
		}
	}
	
	public Matrix4f getTransform() {
		return transform;
	}
}

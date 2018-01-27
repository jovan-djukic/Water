package shapes.basicShapes;

import base.objects.scene.SceneObject;
import base.objects.scene.SceneShaderProgram;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import org.joml.Vector3f;

import java.nio.IntBuffer;

public class Box extends SceneObject {
	
	private static class Constants {
		public static final int NUMBER_OF_VERTICES = 8;
		public static final String box                          = "box-";
		public static final String preRenderTag                 = Constants.box + "preRender";
		public static final String drawCommandTag               = Constants.box + "drawCommand";
		public static final String postRenderTag                = Constants.box + "postRender";
	}
	
	private float width, height, depth;
	private boolean isCullFaceEnabled;
	private IntBuffer cullFaceMode;
	
	public Box(String name, SceneShaderProgram shaderProgram, Vector3f position, float width, float height, float depth) {
		super(name, shaderProgram, position);
		
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		this.cullFaceMode = IntBuffer.allocate(1);
	}
	
	public Box(String name, SceneShaderProgram shaderProgram, float x, float y, float z, float width, float height, float depth) {
		this(name, shaderProgram, new Vector3f(x, y, z), width, height, depth);
	}
	
	public Box(String name, SceneShaderProgram shaderProgram, float width, float height, float depth) {
		this(name, shaderProgram, 0, 0, 0, width, height, depth);
	}
	
	@Override
	protected float[] getVertexData() {
		float vertexData[] = new float[Constants.NUMBER_OF_VERTICES * 3];
		int verticesIndex = 0;
		
		vertexData[verticesIndex++] = +this.width / 2;
		vertexData[verticesIndex++] = -this.height / 2;
		vertexData[verticesIndex++] = +this.depth / 2;
		
		vertexData[verticesIndex++] = +this.width / 2;
		vertexData[verticesIndex++] = -this.height / 2;
		vertexData[verticesIndex++] = -this.depth / 2;
		
		vertexData[verticesIndex++] = -this.width / 2;
		vertexData[verticesIndex++] = -this.height / 2;
		vertexData[verticesIndex++] = -this.depth / 2;
		
		vertexData[verticesIndex++] = -this.width / 2;
		vertexData[verticesIndex++] = -this.height / 2;
		vertexData[verticesIndex++] = +this.depth / 2;
		
		vertexData[verticesIndex++] = +this.width / 2;
		vertexData[verticesIndex++] = +this.height / 2;
		vertexData[verticesIndex++] = +this.depth / 2;
		
		vertexData[verticesIndex++] = +this.width / 2;
		vertexData[verticesIndex++] = +this.height / 2;
		vertexData[verticesIndex++] = -this.depth / 2;
		
		vertexData[verticesIndex++] = -this.width / 2;
		vertexData[verticesIndex++] = +this.height / 2;
		vertexData[verticesIndex++] = -this.depth / 2;
		
		vertexData[verticesIndex++] = -this.width / 2;
		vertexData[verticesIndex++] = +this.height / 2;
		vertexData[verticesIndex++] = +this.depth / 2;
		
		return vertexData;
	}
	
	@Override
	protected boolean hasIndices() {
		return true;
	}
	
	@Override
	protected int[] getIndexData() {
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
	
	protected void setCullFace(GL4 gl) {
		gl.glCullFace(GL.GL_BACK);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.isCullFaceEnabled = gl.glIsEnabled(GL4.GL_CULL_FACE);
		gl.glGetIntegerv(GL4.GL_CULL_FACE, this.cullFaceMode);
		this.cullFaceMode.rewind();
		
		gl.glEnable(GL4.GL_CULL_FACE);
		this.setCullFace(gl);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void drawCommand(GL4 gl) {
		gl.glDrawElements(GL4.GL_TRIANGLES, 36, GL4.GL_UNSIGNED_INT, 0);
		
		this.checkForErrors(gl, Constants.drawCommandTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		if (!this.isCullFaceEnabled) {
			gl.glDisable(GL4.GL_CULL_FACE);
		} else {
			gl.glCullFace(this.cullFaceMode.get(0));
			this.cullFaceMode.rewind();
		}
		
		this.checkForErrors(gl, Constants.postRenderTag);
		
		super.postRender(gl);
	}
	
	@Override
	public void update() {
	
	}
}

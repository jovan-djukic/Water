package base.objects.model;

import base.objects.model.loaders.Loader;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.nio.IntBuffer;

public abstract class Model extends ModelBase {
	protected static class Constants {
		public static final String model           = "model-";
		public static final String initTag         = Constants.model + "init";
		public static final String destroyTag      = Constants.model + "destroy";
		public static final String preRenderTag    = Constants.model + "preRender";
		public static final String postRenderTag   = Constants.model + "postRender";
		public static final int    transformLength = 16;
	}
	
	private int   vertexArrayObjectID;
	private float transform[];
	
	public Model(String name, Loader...loaders) {
		super(name, loaders);
		
		this.transform = new float[Constants.transformLength];
	}
	
	@Override
	public void init(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		
		gl.glGenVertexArrays(1, intBuffer);
		this.vertexArrayObjectID = intBuffer.get(0);
		
		gl.glBindVertexArray(this.vertexArrayObjectID);
		
		super.init(gl);
		
		gl.glBindVertexArray(0);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		super.destroy(gl);
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.vertexArrayObjectID);
		intBuffer.rewind();
		gl.glDeleteVertexArrays(1, intBuffer);
		
		this.checkForErrors(gl, Constants.destroyTag);
	}
	
	protected void preRender(GL4 gl) {
		gl.glBindVertexArray(this.vertexArrayObjectID);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	protected void postRender(GL4 gl) {
		gl.glBindVertexArray(0);
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
	
	protected abstract void drawCommand(GL4 gl);
	
	@Override
	public void render(GL4 gl, Matrix4f parentTransform, int transformUniformLocation) {
		if (parentTransform != null && transformUniformLocation >= 0) {
			super.applyTransform(parentTransform).get(this.transform);
			gl.glUniformMatrix4fv(transformUniformLocation, 1, false, this.transform, 0);
		}
		this.preRender(gl);
		this.drawCommand(gl);
		this.postRender(gl);
	}
}

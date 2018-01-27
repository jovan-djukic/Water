package base.objects.scene;

import base.objects.scene.camera.Camera;
import org.joml.Matrix4f;

import com.jogamp.opengl.GL4;

import base.glsl.ShaderProgramWrapper;

public abstract class SceneShaderProgram extends ShaderProgramWrapper {
	
	protected static class Constants {
		public static final int transformArrayLength = 16;
	}
	
	protected Camera   camera;
	protected Matrix4f transform;
	protected float    transformArray[];
	
	public SceneShaderProgram(String name, Camera camera) {
		super(name);
		
		this.camera = camera;
		
		this.transform = new Matrix4f();
		this.transformArray = new float[Constants.transformArrayLength];
		this.transform.identity()
				.mul(this.camera.getViewProjection())
				.get(this.transformArray);
	}
	
	public void applyTransform(GL4 gl, Matrix4f transform) {
		this.transform.identity()
				.mul(this.camera.getViewProjection())
				.mul(transform)
				.get(this.transformArray);
	}
	
	public abstract int getVertexAttributePosition();
	
	public boolean hasColor() {
		return false;
	}
	
	public int getColorAttributePosition() {
		return 0;
	}
	
	public boolean hasTextureCoordinates() {
		return false;
	}
	
	public int getTextureCoordinateAttributePosition() {
		return 0;
	}
}

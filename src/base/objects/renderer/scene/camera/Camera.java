package base.objects.renderer.scene.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Camera {
	protected Vector3f	position;
	protected Matrix4f	view, projection;
	protected float nearClippingPlane, farClippingPlane;
	
	public Camera(Vector3f position, float nearClippingPlane, float farClippingPlane) {
		this.position = position;
		this.view = new Matrix4f();
		this.projection = new Matrix4f();
		this.nearClippingPlane = nearClippingPlane;
		this.farClippingPlane = farClippingPlane;
	}
	
	public Camera(float x, float y, float z, float nearClippingPlane, float farClippingPlane) {
		this(new Vector3f(x, y, z), nearClippingPlane, farClippingPlane);
	}
	
	protected void setView() {
		this.view.identity()
			.translate(-this.position.x, -this.position.y, -this.position.z);
	}
	
	protected abstract void setProjection();
	
	public Matrix4f getProjection() {
		return projection;
	}
	
	public Matrix4f getView() {
		return view;
	}
	
	public synchronized Matrix4f getViewProjection() {
		return new Matrix4f().mul(projection).mul(view);
	}
	
	public void update() {
		this.setView();
		this.setProjection();
	}
	
	public void init() {
		this.setView();
		this.setProjection();
	}
}

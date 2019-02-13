package base.objects.renderer.scene.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Camera {
	private Vector3f eye, center, up;
	private Matrix4f view, projection;
	private float nearClippingPlane, farClippingPlane;
	
	public Camera(Vector3f eye, Vector3f center, Vector3f up, float nearClippingPlane, float farClippingPlane) {
		this.eye = eye;
		this.center = center;
		this.up = up;
		this.view = new Matrix4f();
		this.projection = new Matrix4f();
		this.nearClippingPlane = nearClippingPlane;
		this.farClippingPlane = farClippingPlane;
	}
	
	protected float getNearClippingPlane() {
		return nearClippingPlane;
	}
	
	protected float getFarClippingPlane() {
		return this.farClippingPlane;
	}
	
	public  Vector3f getEye() {
		return this.eye;
	}
	
	protected Vector3f getUp() {
		return up;
	}
	
	protected void setView() {
		this.view.identity()
				.lookAt(this.eye, this.center, this.up);
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

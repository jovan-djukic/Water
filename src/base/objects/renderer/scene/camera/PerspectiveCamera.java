package base.objects.renderer.scene.camera;

import org.joml.Vector3f;

public class PerspectiveCamera extends Camera {
	
	private float fieldOfView, aspectRation;
	
	public PerspectiveCamera(Vector3f position, float fieldOfView, float aspectRation, float nearClippingPlane, float farClippingPlane) {
		super(position, nearClippingPlane, farClippingPlane);
		
		this.fieldOfView = fieldOfView;
		this.aspectRation = aspectRation;
	}
	
	public PerspectiveCamera(float x, float y, float z, float fieldOfView, float aspectRation, float nearClippingPlane, float farClippingPlane) {
		this(new Vector3f(x, y, z), fieldOfView, aspectRation, nearClippingPlane, farClippingPlane);
	}
	
	@Override
	protected void setProjection() {
		super.getProjection().identity()
				.perspective(this.fieldOfView, this.aspectRation, super.getNearClippingPlane(), super.getFarClippingPlane());
	}
	
	public void setAspectRation(float aspectRation) {
		this.aspectRation = aspectRation;
	}
	
	public void setAspectRation(float width, float height) {
		this.setAspectRation(width / height);
	}
}

package base.objects.renderer.scene.camera;

import org.joml.Vector3f;

public class PerspectiveCamera extends Camera {
	
	private float fieldOfView, aspectRation;
	
	public PerspectiveCamera(
			Vector3f eye,
			Vector3f center,
			Vector3f up,
			float fieldOfView,
			float aspectRation,
			float nearClippingPlane,
			float farClippingPlane
	) {
		super(eye, center, up, nearClippingPlane, farClippingPlane);
		
		this.fieldOfView = fieldOfView;
		this.aspectRation = aspectRation;
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

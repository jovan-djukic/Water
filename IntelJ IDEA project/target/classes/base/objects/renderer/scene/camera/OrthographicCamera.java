package base.objects.renderer.scene.camera;

import org.joml.Vector3f;

public class OrthographicCamera extends Camera {
	private float left, right, bottom, top;
	
	public OrthographicCamera(
			Vector3f eye,
			Vector3f center,
			Vector3f up,
			float left,
			float right,
			float bottom,
			float top,
			float nearClippingPlane,
			float farClippingPlane
	) {
		super(eye, center, up, nearClippingPlane, farClippingPlane);
		
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		this.top = top;
	}
	
	@Override
	protected void setProjection() {
		super.getProjection().identity().ortho(this.left, this.right, this.bottom, this.top, super.getNearClippingPlane(), super.getFarClippingPlane());
	}
}

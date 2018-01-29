package base.objects.renderer.scene.camera;

public class OrthographicCamera extends Camera {
	protected float left, right, bottom, top;
	
	public OrthographicCamera(float left, float right, float bottom, float top, float nearClippingPlane, float farClippingPlane) {
		super(0, 0, 0, nearClippingPlane, farClippingPlane);
		
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

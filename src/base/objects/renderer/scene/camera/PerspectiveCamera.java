package base.objects.renderer.scene.camera;

import org.joml.Vector3f;

public class PerspectiveCamera extends Camera {
	
	private static class Constants {
		public static final float	fieldOfView			= (float) Math.toRadians(45);
		public static final float	aspectRation		= 1;
		public static final float	nearClippingPlane	= 0.1f;
		public static final float	farClippingPlane	= 1000;
	}
	
	private float fieldOfView, aspectRation;
	
	public PerspectiveCamera(Vector3f position, float fieldOfView, float aspectRation, float nearClippingPlane, float farClippingPlane) {
		super(position, nearClippingPlane, farClippingPlane);
		
		this.fieldOfView = fieldOfView;
		this.aspectRation = aspectRation;
	}
	
	public PerspectiveCamera(float x, float y, float z, float fieldOfView, float aspectRation, float nearClippingPlane, float farClippingPlane) {
		this(new Vector3f(x, y, z), fieldOfView, aspectRation, nearClippingPlane, farClippingPlane);
	}
	
	public PerspectiveCamera(float x, float y, float z) {
		this(new Vector3f(x, y, z), Constants.fieldOfView, Constants.aspectRation, Constants.nearClippingPlane, Constants.farClippingPlane);
	}
	
	@Override
	protected void setProjection() {
		super.projection.identity()
				.perspective(this.fieldOfView, this.aspectRation, super.nearClippingPlane, super.farClippingPlane);
	}
	
	public void setAspectRation(float aspectRation) {
		this.aspectRation = aspectRation;
	}
	
	public void setAspectRation(float width, float height) {
		this.setAspectRation(width / height);
	}
}

package base.objects.renderer.scene.camera.orbitingCamera;

import base.objects.renderer.scene.camera.PerspectiveCamera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class OrbitingCamera extends PerspectiveCamera {
	
	private Matrix4f transform;
	private Vector4f eye, up;
	private float    distance;
	private float    pitch, yaw, roll;
	
	public OrbitingCamera(
			float distance,
			float pitch,
			float yaw,
			float roll,
			float fieldOfView,
			float aspectRation,
			float nearClippingPlane,
			float farClippingPlane
	) {
		super(
				new Vector3f(0, 0, 0),
				new Vector3f(0, 0, 0),
				new Vector3f(0, 1, 0),
				fieldOfView,
				aspectRation,
				nearClippingPlane,
				farClippingPlane
		);
		
		this.distance = distance;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		
		this.eye = new Vector4f(0, 0, 0, 1);
		this.up = new Vector4f(0, 1, 0, 1);
		
		this.transform = new Matrix4f();
		this.transform.identity();
		
		this.calculatePosition();
	}
	
	private void calculatePosition() {
		this.eye.set(0, 0, 0, 1);
		this.up.set(0, 1, 0, 1);
		this.transform.identity()
				.rotate((float) Math.toRadians(this.yaw), 0, 1,0)
				.rotate((float) Math.toRadians(this.pitch), 1, 0,0)
				.rotate((float) Math.toRadians(-this.yaw), 0, 1,0)
				.rotate((float) Math.toRadians(this.yaw), 0, 1,0)
				.rotate((float) Math.toRadians(this.roll), 0, 0,1)
				.translate(0, 0, this.distance);
		
		this.transform.transform(this.eye);
		super.getEye().set(this.eye.x, this.eye.y, this.eye.z);
		
		this.transform.transform(this.up);
		super.getUp().set(this.up.x, this.up.y, this.up.z);
	}
	
	@Override
	public void update() {
		this.calculatePosition();
		super.update();
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getRoll() {
		return roll;
	}
	
	public void changeDistanceBy(float d) {
		this.distance += d;
	}
	
	protected void changePitchBy(float byAngle) {
		this.pitch += byAngle;
	}
	
	protected void changeYawBy(float byAngle) {
		this.yaw += byAngle;
	}
	
	protected void changeRollBy(float byAngle) {
		this.roll += byAngle;
	}
}

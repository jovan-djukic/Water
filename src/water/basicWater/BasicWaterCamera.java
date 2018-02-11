package water.basicWater;

import base.objects.renderer.scene.camera.PerspectiveCamera;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

public class BasicWaterCamera extends PerspectiveCamera implements MouseListener {
	
	private static class Constants {
		public static final int		rotationIndex	= 1;
		public static final int		mouseWheelUp	= 1;
		public static final int		mouseWheelDown	= -1;
	}
	
	private float zStep;
	private float pitch, yaw, roll;
	private float oldX, oldY;
	
	public BasicWaterCamera(float x, float y, float z, float fieldOfView, float aspectRatio, float nearClippingPlane, float farClippingPlane, float pitch, float yaw, float roll, float zStep) {
		super(x, y, z, fieldOfView, aspectRatio, nearClippingPlane, farClippingPlane);
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll =  roll;
		this.zStep 	= zStep;
	}
	
	protected void setView() {
		super.setView();
		super.getView().rotate((float) Math.toRadians(this.pitch), 1, 0, 0)
				.rotate((float) Math.toRadians(this.yaw), 0, 1, 0)
				.rotate((float) Math.toRadians(this.roll), 0, 0, 1);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {
		this.oldX = e.getX();
		this.oldY = e.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		float dx = e.getX() - this.oldX;
		float dy = e.getY() - this.oldY;
		
		this.pitch += dy;
		this.yaw += dx;
		
		this.oldX = e.getX();
		this.oldY = e.getY();
	}
	
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		if (e.getRotation()[Constants.rotationIndex] == Constants.mouseWheelUp) {
			super.getPosition().z -= this.zStep;
		} else if (e.getRotation()[Constants.rotationIndex] == Constants.mouseWheelDown) {
			super.getPosition().z += this.zStep;
		}
	}
	
	public void invertPitch() {
		this.pitch = -this.pitch;
	}
}

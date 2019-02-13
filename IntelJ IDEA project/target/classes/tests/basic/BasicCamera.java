package tests.basic;

import base.objects.renderer.scene.camera.orbitingCamera.OrbitingCamera;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

public class BasicCamera extends OrbitingCamera implements MouseListener {
	private static class Constants {
		public static final int		rotationIndex	= 1;
		public static final int		mouseWheelUp	= 1;
		public static final int		mouseWheelDown	= -1;
	}
	
	private float zStep;
	private float oldX, oldY;
	
	public BasicCamera(float distance, float pitch, float yaw, float roll, float fieldOfView, float aspectRation, float nearClippingPlane, float farClippingPlane, float zStep) {
		super(distance, pitch, yaw, roll, fieldOfView, aspectRation, nearClippingPlane, farClippingPlane);
		
		this.zStep = zStep;
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
		
		super.changePitchBy(-dy);
		super.changeYawBy(dx);
		
		this.oldX = e.getX();
		this.oldY = e.getY();
	}
	
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		if (e.getRotation()[Constants.rotationIndex] == Constants.mouseWheelUp) {
			super.changeDistanceBy(-this.zStep);
		} else if (e.getRotation()[Constants.rotationIndex] == Constants.mouseWheelDown) {
			super.changeDistanceBy(this.zStep);
		}
	}
}

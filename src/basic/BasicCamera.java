package basic;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import base.objects.renderer.scene.camera.PerspectiveCamera;

public class BasicCamera extends PerspectiveCamera implements MouseListener {
	
	private static class Constants {
		public static final float	x				= 0;
		public static final float	y				= 0;
		public static final float	z				= 10;
		public static final float	xAngle			= 30;
		public static final float	yAngle			= 0;
		public static final float	zAngle			= 0;
		
		public static final int		rotationIndex	= 1;
		public static final int		mouseWheelUp	= 1;
		public static final int		mouseWheelDown	= -1;
		public static final float	zStep			= 0.5f;
	}
	
	protected float	xAngle, yAngle, zAngle;
	protected float	oldX, oldY;
	
	public BasicCamera(float x, float y, float z, float xAngle, float yAngle, float zAngle) {
		super(x, y, z);
		this.xAngle = xAngle;
		this.yAngle = yAngle;
		this.zAngle = zAngle;
	}
	
	public BasicCamera() {
		this(Constants.x, Constants.y, Constants.z, Constants.xAngle, Constants.yAngle, Constants.zAngle);
	}
	
	protected void setView() {
		super.setView();
		super.view.rotate((float) Math.toRadians(this.xAngle), 1, 0, 0)
				.rotate((float) Math.toRadians(this.yAngle), 0, 1, 0)
				.rotate((float) Math.toRadians(this.zAngle), 0, 0, 1);
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
		
		this.xAngle += dy;
		this.yAngle += dx;
		
		this.oldX = e.getX();
		this.oldY = e.getY();
	}
	
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		if (e.getRotation()[Constants.rotationIndex] == Constants.mouseWheelUp) {
			this.position.z -= Constants.zStep;
		} else if (e.getRotation()[Constants.rotationIndex] == Constants.mouseWheelDown) {
			this.position.z += Constants.zStep;
		}
	}
}

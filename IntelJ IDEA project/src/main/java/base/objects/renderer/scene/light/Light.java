package base.objects.renderer.scene.light;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Light {
	private Vector3f position;
	private Vector4f color;
	
	public Light(Vector3f position, Vector4f color) {
		this.position = position;
		this.color = color;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector4f getColor() {
		return color;
	}
}

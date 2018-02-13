package water.basicWater.waterTile;

import org.joml.Vector3f;
import shapes.Quad;

public class WaterTileModel extends Quad {
	private float height;
	
	public WaterTileModel(String name, Vector3f topLeft, float width, float height, int vertexAttributeLocation, int texelAttributeLocation) {
		super(
				name,
				topLeft,
				new Vector3f(topLeft.x + width, topLeft.y, topLeft.z),
				new Vector3f(topLeft.x, topLeft.y, topLeft.z + height),
				new Vector3f(topLeft.x + width, topLeft.y, topLeft.z + height),
				vertexAttributeLocation,
				texelAttributeLocation
		);
		
		this.height = topLeft.y;
	}
	
	public float getHeight() {
		return height;
	}
}

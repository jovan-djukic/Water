package water.basicWater.waterTile;

import org.joml.Vector3f;
import shapes.Quad;

public class WaterTileModel extends Quad {
	private float height;
	
	public WaterTileModel(String name, float x, float y, float z, float width, float height, int vertexPositionAttributeLocation) {
		super(
				name,
				new Vector3f(x, y, z),
				new Vector3f(x + width, y, z),
				new Vector3f(x, y, z - height),
				new Vector3f(x + width, y, z - height),
				vertexPositionAttributeLocation
		);
		
		this.height = y;
	}
	
	public float getHeight() {
		return height;
	}
}

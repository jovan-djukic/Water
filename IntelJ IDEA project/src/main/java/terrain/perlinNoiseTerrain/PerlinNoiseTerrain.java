package terrain.perlinNoiseTerrain;

import org.joml.Vector3f;
import org.joml.Vector4f;
import perlinNoise.perliNoiseGenerator.PerlinNoiseGenerator;
import shapes.Mesh;

public class PerlinNoiseTerrain extends Mesh {
	
	private static class Constants {
		public static final String perlinNoiseTerrainVertexPositionLoader     = "-perlinNoiseTerrainVertexPositionLoader";
		public static final String perlinNoiseTerrainTextureCoordinatesLoader = "-textureCoordinatesLoader";
	}
	
	protected static class PerlinNoiseTerrainVerticesLoader extends MeshVerticesLoader {
		private int numberOfOctaves;
		private float amplitude, power, persistence, scaleX, scaleY;
		private float width, height;
		
		public PerlinNoiseTerrainVerticesLoader(String name, Vector3f topLeft, float width, float height, int rows, int columns,  int vertexPositionAttributeLocation, int numberOfOctaves, float persistence, float scaleX, float scaleY, float amplitude, float power) {
			super(
					name,
					topLeft,
					new Vector3f(topLeft.x + width, topLeft.y, topLeft.z),
					new Vector3f(topLeft.x, topLeft.y, topLeft.z + height),
					new Vector3f(topLeft.x + width, topLeft.y, topLeft.z + height),
					rows,
					columns,
					vertexPositionAttributeLocation
			);
			
			this.width = width;
			this.height = height;
			
			this.numberOfOctaves = numberOfOctaves;
			this.amplitude = amplitude;
			this.power = power;
			this.persistence = persistence;
			this.scaleX = scaleX;
			this.scaleY = scaleY;
		}
		
		@Override
		protected float getY(int row, int column) {
			float x = this.width * this.scaleX / (super.getColumns() - 1) * column;
			float z = this.height * this.scaleY / (super.getRows() - 1) * row;
			
			float noise = PerlinNoiseGenerator.getInstance().perlinNoise(x, z, this.numberOfOctaves, this.persistence);
			
			noise = (float) Math.pow(noise, this.power);
			
			return (2 * noise - 1) * amplitude + super.getY(row, column);
		}
	}
	
	
	public PerlinNoiseTerrain(String name, Vector3f topLeft,  float width, float height, int rows, int columns, int vertexPositionAttributeLocation, int numberOfOctaves, float persistence, float scaleX, float scaleY, float amplitude, float power, int textureCoordinatesAttributeLocation) {
		super(
				name,
				new PerlinNoiseTerrainVerticesLoader(
						name + Constants.perlinNoiseTerrainVertexPositionLoader,
						topLeft,
						width,
						height,
						rows,
						columns,
						vertexPositionAttributeLocation,
						numberOfOctaves,
						persistence,
						scaleX,
						scaleY,
						amplitude,
						power
				),
				new MeshTexelsLoader(
						name + Constants.perlinNoiseTerrainTextureCoordinatesLoader,
						textureCoordinatesAttributeLocation,
						rows,
						columns,
						1,
						1
				)
		);
	}
}


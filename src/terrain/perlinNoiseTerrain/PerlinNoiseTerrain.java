package terrain.perlinNoiseTerrain;

import org.joml.Vector3f;
import perlinNoise.perliNoiseGenerator.PerlinNoiseGenerator;
import shapes.Mesh;

public class PerlinNoiseTerrain extends Mesh {
	
	private static class Constants {
		public static final String perlinNoiseTerrainVertexPositionLoader     = "-perlinNoiseTerrainVertexPositionLoader";
		public static final String perlinNoiseTerrainIndicesLoader            = "-perlinNoiseTerrainIndicesLoader";
		public static final String perlinNoiseTerrainTextureCoordinatesLoader = "-textureCoordinatesLoader";
	}
	
	protected static class PerlinNoiseTerrainVerticesLoader extends MeshVerticesLoader {
		private int numberOfOctaves;
		private float amplitude, power, persistence, scaleX, scaleY;
		private float width, height;
		
		public PerlinNoiseTerrainVerticesLoader(String name, float x, float z, int vertexPositionAttributeLocation, float width, float height, int rows, int columns, int numberOfOctaves, float persistence, float scaleX, float scaleY, float amplitude, float power) {
			super(
					name,
					new Vector3f(x, 0, z - height),
					new Vector3f(x + width, 0, z - height),
					new Vector3f(x, 0, z),
					new Vector3f(x + width, 0, z),
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
			
			return (2 * noise - 1) * amplitude;
		}
	}
	
	
	public PerlinNoiseTerrain(String name, float x, float z, int vertexPositionAttributeLocation, float width, float height, int rows, int columns, int numberOfOctaves, float persistence, float scaleX, float scaleY, float amplitude, float power, int textureCoordinatesAttributeLocation) {
		super(
				name,
				rows * columns * 6,
				new PerlinNoiseTerrainVerticesLoader(
						name + Constants.perlinNoiseTerrainVertexPositionLoader,
						x,
						z,
						vertexPositionAttributeLocation,
						width,
						height,
						rows,
						columns,
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


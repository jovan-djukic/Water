package terrain.perlinNoiseTerrain;

import perlinNoise.perliNoiseGenerator.PerlinNoiseGenerator;
import shapes.Mesh;

public class PerlinNoiseTerrain extends Mesh {
	
	private static class Constants {
		public static final String perlinNoiseTerrainVertexPositionLoader     = "-perlinNoiseTerrainVertexPositionLoader";
		public static final String perlinNoiseTerrainIndicesLoader            = "-perlinNoiseTerrainIndicesLoader";
		public static final String perlinNoiseTerrainTextureCoordinatesLoader = "-textureCoordinatesLoader";
	}
	
	protected static class PerlinNoiseTerrainVertexPositionLoader extends MeshVertexPositionLoader {
		private int numberOfOctaves;
		private float amplitude, power, persistence, scaleX, scaleY;
		
		public PerlinNoiseTerrainVertexPositionLoader(String name, float x, float z, int vertexPositionAttributeLocation, float width, float height, int rows, int columns, int numberOfOctaves, float persistence, float scaleX, float scaleY, float amplitude, float power) {
			super(name, x, z, vertexPositionAttributeLocation, width, height, rows, columns);
			
			this.numberOfOctaves = numberOfOctaves;
			this.amplitude = amplitude;
			this.power = power;
			this.persistence = persistence;
			this.scaleX = scaleX;
			this.scaleY = scaleY;
		}
		
		@Override
		protected float getY(int row, int column) {
			float x = super.getWidth() * this.scaleX / (super.getColumns() - 1) * column;
			float z = super.getHeight() * this.scaleY / (super.getRows() - 1) * row;
			
			float noise = PerlinNoiseGenerator.getInstance().perlinNoise(x, z, this.numberOfOctaves, this.persistence);
			
			noise = (float) Math.pow(noise, this.power);
			
			return (2 * noise - 1) * amplitude;
		}
	}
	
	
	public PerlinNoiseTerrain(String name, float x, float z, int vertexPositionAttributeLocation, float width, float height, int rows, int columns, int numberOfOctaves, float persistence, float scaleX, float scaleY, float amplitude, float power, int textureCoordinatesAttributeLocation) {
		super(
				name,
				rows * columns * 6,
				new PerlinNoiseTerrainVertexPositionLoader(
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
				new MeshIndicesLoader(
						Constants.perlinNoiseTerrainIndicesLoader,
						rows,
						columns
				),
				new MeshTextureCoordinatesLoader(
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


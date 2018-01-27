package terrain.perlinNoiseTerrain;

import base.objects.scene.SceneShaderProgram;
import perlinNoise.perliNoiseGenerator.PerlinNoiseGenerator;
import shapes.basicShapes.Mesh;

public class PerlinNoiseTerrain extends Mesh {
	
	private int numberOfOctaves;
	private float amplitude, power, persistence, scaleX, scaleY;
	
	public PerlinNoiseTerrain(String name, SceneShaderProgram shaderProgram, float x, float z, float width, float height, int rows, int columns, int numberOfOctaves, float persistence, float scaleX, float scaleY, float amplitude, float power) {
		super(name, shaderProgram, x, z, width, height, rows, columns);
		
		this.numberOfOctaves = numberOfOctaves;
		this.persistence = persistence;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.amplitude = amplitude;
		this.power = power;
	}
	
	@Override
	protected float getY(int row, int column) {
		float x = this.width * this.scaleX / (this.columns - 1) * column;
		float z = this.height * this.scaleY / (this.rows - 1) * row;
		
		float noise = PerlinNoiseGenerator.getInstance().perlinNoise(x, z, this.numberOfOctaves, this.persistence);
		
		noise = (float) Math.pow(noise, this.power);
		
		return (2 * noise - 1) * amplitude;
	}
}


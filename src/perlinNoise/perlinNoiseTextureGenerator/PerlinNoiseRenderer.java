package perlinNoise.perlinNoiseTextureGenerator;

import base.objects.model.ModelBase;
import base.objects.renderer.Renderer;
import com.jogamp.opengl.GL4;
import perlinNoise.perlinNoiseTextureGenerator.shaderProgram.PerlinNoiseShaderProgram;

public class PerlinNoiseRenderer extends Renderer {
	private static class Constatns {
		public static final String name = "perlinNoise";
		public static final int permutation[] = { 151,160,137,91,90,15,
				131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
				190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
				88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
				77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
				102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
				135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
				5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
				223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
				129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
				251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
				49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
				138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
		};
		
		public static final String perlinNoise  = Constatns.name + "-";
		public static final String initTag      = Constatns.perlinNoise + "init";
		public static final String preRenderTag = Constatns.perlinNoise + "preRender";
	}
	
	private int permutation[];
	private int numberOfOctaves;
	private float persistence, scaleX, scaleY;
	private int permutationLocation, numberOfOctavesLocation, persistenceLocation, scaleXLocation, scaleYLocation;
	private PerlinNoiseShaderProgram perlinNoiseShaderProgram;
	
	private PerlinNoiseRenderer(String name, int numberOfOctaves, float persistence, float scaleX, float scaleY, PerlinNoiseShaderProgram perlinNoiseShaderProgram, ModelBase modelBases[]) {
		super(name, perlinNoiseShaderProgram, modelBases);
		this.numberOfOctaves = numberOfOctaves;
		this.persistence = persistence;
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		this.perlinNoiseShaderProgram = perlinNoiseShaderProgram;
	}
	
	public PerlinNoiseRenderer(String name, int numberOfOctaves, float persistence, float scaleX, float scaleY) {
		this(name, numberOfOctaves, persistence, scaleX, scaleY, new PerlinNoiseShaderProgram(), new ModelBase[] {new PerlinNoiseModel()});
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.permutation = new int[Constatns.permutation.length * 2];
		
		for (int i = 0; i < Constatns.permutation.length; i++) {
			this.permutation[i] = Constatns.permutation[i];
			this.permutation[Constatns.permutation.length + i] = Constatns.permutation[i];
		}
		
		this.permutationLocation = this.perlinNoiseShaderProgram.getPermutationLocation();
		this.numberOfOctavesLocation = this.perlinNoiseShaderProgram.getNumberOfOctavesLocation();
		this.persistenceLocation = this.perlinNoiseShaderProgram.getPersistenceLocation();
		this.scaleXLocation = this.perlinNoiseShaderProgram.getScaleXLocation();
		this.scaleXLocation = this.perlinNoiseShaderProgram.getScaleXLocation();
		this.scaleYLocation = this.perlinNoiseShaderProgram.getScaleYLocation();
		
		this.checkForErrors(gl, Constatns.initTag);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glUniform1iv(this.permutationLocation, PerlinNoiseShaderProgram.Uniforms.permutationLength, this.permutation, 0);
		gl.glUniform1i(this.numberOfOctavesLocation, this.numberOfOctaves);
		gl.glUniform1f(this.persistenceLocation, this.persistence);
		gl.glUniform1f(this.scaleXLocation, this.scaleX);
		gl.glUniform1f(this.scaleYLocation, this.scaleY);
		
		this.checkForErrors(gl, Constatns.preRenderTag);
	}
}

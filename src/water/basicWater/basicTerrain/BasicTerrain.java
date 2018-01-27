package water.basicWater.basicTerrain;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import terrain.perlinNoiseTerrain.PerlinNoiseTerrain;
import water.basicWater.basicTerrain.basicTerrainShaderProgram.BasicTerrainShaderProgram;

import java.io.IOException;

public class BasicTerrain extends PerlinNoiseTerrain {
	private static class Constants {
		public static final String jpg                     = ".jpg";
		public static final String grassTexture            = "grass" + Constants.jpg;
		public static final String sandTexture             = "sand" + Constants.jpg;
		public static final String basicTerrain            = "basicTerrain";
		public static final String createDataStructuresTag = Constants.basicTerrain + "createDataStructures";
		public static final String preRenderTag            = Constants.basicTerrain + "preRender";
		public static final String setPolygonModeTag       = Constants.basicTerrain + "setPolygonMode";
		public static final String destroyTag              = Constants.basicTerrain + "destroy";
	}
	
	private int grassTextureLocation, sandTextureLocation;
	private BasicTerrainShaderProgram shaderProgram;
	private Texture sandTexture, grassTexture;
	
	public BasicTerrain(String name, BasicTerrainShaderProgram shaderProgram, float x, float z, float width, float height, int rows, int columns, int numberOfOctaves, float persistence, float scaleX, float scaleY, float amplitude, float power) {
		super(name, shaderProgram, x, z, width, height, rows, columns, numberOfOctaves, persistence, scaleX, scaleY, amplitude, power);
		
		this.shaderProgram = shaderProgram;
	}
	
	@Override
	protected void createDataStructures(GL4 gl) {
		super.createDataStructures(gl);
		
		this.grassTextureLocation = gl.glGetUniformLocation(this.shaderProgram.getProgramID(), BasicTerrainShaderProgram.Uniforms.grassTexture);
		this.sandTextureLocation = gl.glGetUniformLocation(this.shaderProgram.getProgramID(), BasicTerrainShaderProgram.Uniforms.sandTexture);
		
		try {
			this.grassTexture = TextureIO.newTexture(this.getClass().getResourceAsStream(Constants.grassTexture), false, Constants.jpg);
			this.sandTexture = TextureIO.newTexture(this.getClass().getResourceAsStream(Constants.sandTexture), false, Constants.jpg);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		this.checkForErrors(gl, Constants.createDataStructuresTag);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		this.grassTexture.bind(gl);
		gl.glActiveTexture(GL4.GL_TEXTURE1);
		this.sandTexture.bind(gl);
		
		this.shaderProgram.setGrassTexture(gl, 0);
		this.shaderProgram.setSandTexture(gl,1);
		
		gl.glEnable(GL4.GL_CULL_FACE);
		gl.glCullFace(GL4.GL_BACK);
		gl.glEnable(GL4.GL_DEPTH_TEST);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void setPolygonMode(GL4 gl) {
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_FILL);
		
		this.checkForErrors(gl, Constants.setPolygonModeTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		gl.glDisable(GL4.GL_CULL_FACE);
		gl.glDisable(GL4.GL_DEPTH_TEST);
		
		super.postRender(gl);
	}
	
	@Override
	protected void destroyDataStructures(GL4 gl) {
		if (this.grassTexture != null) {
			this.grassTexture.destroy(gl);
		}
		
		if (this.sandTexture != null) {
			this.sandTexture.destroy(gl);
		}
		
		this.checkForErrors(gl, Constants.destroyTag);
		
		super.destroyDataStructures(gl);
	}
}

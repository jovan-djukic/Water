package water.basicWater.waterTile;

import base.objects.renderer.scene.Scene;
import base.objects.renderer.scene.camera.Camera;
import base.objects.renderer.scene.sceneModel.SceneModel;
import base.objects.textures.Texture;
import base.objects.textures.TextureData;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;
import water.basicWater.waterTile.waterTileShaderProgram.WaterTileShaderProgram;

public class WaterTileRenderer extends Scene {
	private static class Constants {
		public static final String waterTileRenderer   = "waterTileRenderer";
		public static final String dudvTexture         = Constants.waterTileRenderer + "-dudvTexture";
		public static final String preRenderTag        = Constants.waterTileRenderer + "-preRender";
		public static final String postRenderTag       = Constants.waterTileRenderer + "-postRender";
		public static final float  milisecondsInSecond = 1000;
	}
	
	private boolean isDepthTestEnable;
	private Class   scope;
	private String  dudvTextureFileName;
	private Texture dudvTexture;
	private float     scaleX, scaleY;
	private Texture reflectionTexture, refractionTexture;
	private WaterTileShaderProgram shaderProgram;
	private float                  waveStrength;
	private float                  waveSpeed, moveFactor;
	private long lastTime;
	
	public WaterTileRenderer(
			String name,
			WaterTileShaderProgram shaderProgram,
			WaterTileModel waterTileModel,
			Camera camera,
			Texture reflectionTexture,
			Texture refractionTexture,
			Class scope,
			String dudvTextureFileName,
			Texture dudvTexture,
			float scaleX,
			float scaleY,
			float waveStrength,
			float waveSpeed
	) {
		super(name, shaderProgram, camera, new SceneModel[]{waterTileModel}, dudvTexture, reflectionTexture, refractionTexture);
		
		this.shaderProgram = shaderProgram;
		this.reflectionTexture = reflectionTexture;
		this.refractionTexture = refractionTexture;
		this.scope = scope;
		this.dudvTextureFileName = dudvTextureFileName;
		this.dudvTexture = dudvTexture;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.waveStrength = waveStrength;
		this.waveSpeed = waveSpeed;
	}
	
	public WaterTileRenderer(
			String name,
			WaterTileShaderProgram shaderProgram,
			WaterTileModel waterTileModel,
			Camera camera,
			Texture reflectionTexture,
			Texture refractionTexture,
			Class scope,
			String dudvTextureFileName,
			float scaleX,
			float scaleY,
			float waveStrength,
			float waveSpeed
	) {
		this(
				name,
				shaderProgram,
				waterTileModel,
				camera,
				reflectionTexture,
				refractionTexture,
				scope,
				dudvTextureFileName,
				new Texture(Constants.dudvTexture, GL4.GL_RGBA, GL4.GL_RG, GL4.GL_UNSIGNED_BYTE),
				scaleX,
				scaleY,
				waveStrength,
				waveSpeed
		);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.dudvTexture.bind(gl)
				.texImage2D(gl, 0, TextureData.decodePngImage(this.scope, this.dudvTextureFileName, PNGDecoder.Format.RGBA))
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.isDepthTestEnable = gl.glIsEnabled(GL4.GL_DEPTH_TEST);
		if (!this.isDepthTestEnable) {
			gl.glEnable(GL4.GL_DEPTH_TEST);
		}
		
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		this.reflectionTexture.bind(gl);
		this.shaderProgram.setReflectionTextureUniform(gl, 0);
		
		gl.glActiveTexture(GL4.GL_TEXTURE1);
		this.refractionTexture.bind(gl);
		this.shaderProgram.setRefractionTextureUniform(gl, 1);
		
		gl.glActiveTexture(GL4.GL_TEXTURE2);
		this.dudvTexture.bind(gl);
		this.shaderProgram.setDudvTextureUniform(gl, 2);
		
		this.shaderProgram.setScaleXUniform(gl, this.scaleX);
		this.shaderProgram.setScaleYUniform(gl, this.scaleY);
		
		this.shaderProgram.setWaveStrengthUniform(gl, this.waveStrength);
		
		this.shaderProgram.setMoveFactorUniform(gl, this.moveFactor);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		super.postRender(gl);
		
		if (!this.isDepthTestEnable) {
			gl.glDisable(GL4.GL_DEPTH_TEST);
		}
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
	
	@Override
	public void update() {
		super.update();
		
		if (this.lastTime == 0) {
			this.lastTime = System.currentTimeMillis();
		} else {
			long currentTime = System.currentTimeMillis();
			float passedTime = (currentTime - this.lastTime) / Constants.milisecondsInSecond;
			this.lastTime = currentTime;
			
			this.moveFactor += this.waveSpeed * passedTime;
			this.moveFactor %= 1;
			
		}
	}
}

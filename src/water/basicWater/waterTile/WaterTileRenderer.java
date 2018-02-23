package water.basicWater.waterTile;

import base.objects.renderer.scene.Scene;
import base.objects.renderer.scene.camera.Camera;
import base.objects.renderer.scene.light.Light;
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
		public static final String normalMapTexture    = Constants.waterTileRenderer + "-normalMapTexture";
		public static final String preRenderTag        = Constants.waterTileRenderer + "-preRender";
		public static final String postRenderTag       = Constants.waterTileRenderer + "-postRender";
		public static final float  milisecondsInSecond = 1000;
	}
	
	private boolean isDepthTestEnable;
	private Class   scope;
	private String  dudvTextureFileName;
	private Texture dudvTexture;
	private String  normalMapTextureFileName;
	private Texture normalMapTexture;
	private float   scaleX, scaleY;
	private Texture reflectionTexture, refractionTexture;
	private WaterTileShaderProgram shaderProgram;
	private float                  waveStrength;
	private float                  waveSpeed, moveFactor;
	private long  lastTime;
	private float distortionStrength;
	private float waterReflectivity;
	private Light light;
	private float shineDamper, lightReflectivity;
	private float normalEqualizationFactor;
	
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
			String normalMapTextureFileName,
			Texture normalMapTexture,
			float scaleX,
			float scaleY,
			float waveStrength,
			float waveSpeed,
			float distortionStrength,
			float waterReflectivity,
			Light light,
			float shineDamper,
			float lightReflectivity,
			float normalEqualizationFactor
	) {
		super(name, shaderProgram, camera, new SceneModel[]{waterTileModel}, dudvTexture, normalMapTexture, reflectionTexture, refractionTexture);
		
		this.shaderProgram = shaderProgram;
		this.reflectionTexture = reflectionTexture;
		this.refractionTexture = refractionTexture;
		this.scope = scope;
		this.dudvTextureFileName = dudvTextureFileName;
		this.dudvTexture = dudvTexture;
		this.normalMapTextureFileName = normalMapTextureFileName;
		this.normalMapTexture = normalMapTexture;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.waveStrength = waveStrength;
		this.waveSpeed = waveSpeed;
		this.distortionStrength = distortionStrength;
		this.waterReflectivity = waterReflectivity;
		this.light = light;
		this.shineDamper = shineDamper;
		this.lightReflectivity = lightReflectivity;
		this.normalEqualizationFactor = normalEqualizationFactor;
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
			String normalMapTextureFileName,
			float scaleX,
			float scaleY,
			float waveStrength,
			float waveSpeed,
			float distortionStrength,
			float waterReflectivity,
			Light light,
			float shineDamper,
			float lightReflectivity,
			float normalEqualizationFactor
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
				new Texture(Constants.dudvTexture, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE),
				normalMapTextureFileName,
				new Texture(Constants.normalMapTexture, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE),
				scaleX,
				scaleY,
				waveStrength,
				waveSpeed,
				distortionStrength,
				waterReflectivity,
				light,
				shineDamper,
				lightReflectivity,
				normalEqualizationFactor
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
		
		this.normalMapTexture.bind(gl)
				.texImage2D(gl, 0, TextureData.decodePngImage(this.scope, this.normalMapTextureFileName, PNGDecoder.Format.RGBA))
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
		
		this.shaderProgram.setDistortionStrengthUniform(gl, this.distortionStrength);
		
		this.shaderProgram.setCameraPositionUniform(gl, super.getCamera().getEye());
		
		this.shaderProgram.setWaterReflectivityUniform(gl, this.waterReflectivity);
		
		gl.glActiveTexture(GL4.GL_TEXTURE3);
		this.normalMapTexture.bind(gl);
		this.shaderProgram.setNormalMapTextureUniform(gl, 3);
		
		this.shaderProgram.setLightPositionUniform(gl, this.light.getPosition());
		this.shaderProgram.setLightColorUniform(gl, this.light.getColor());
		this.shaderProgram.setShineDamperUniform(gl, this.shineDamper);
		this.shaderProgram.setLightReflectivityUniform(gl, this.lightReflectivity);
		
		this.shaderProgram.setNormalEqualizationFactorUniform(gl, this.normalEqualizationFactor);
		
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
		}
	}
}

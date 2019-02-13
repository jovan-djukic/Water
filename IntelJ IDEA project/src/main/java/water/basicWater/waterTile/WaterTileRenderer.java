package water.basicWater.waterTile;

import base.objects.renderer.scene.Scene;
import base.objects.renderer.scene.camera.Camera;
import base.objects.renderer.scene.light.Light;
import base.objects.renderer.scene.sceneModel.SceneModel;
import base.objects.textures.Texture;
import base.objects.textures.TextureData;
import base.objects.textures.TextureUnitManager;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;
import water.basicWater.waterTile.waterTileShaderProgram.WaterTileShaderProgram;

public class WaterTileRenderer extends Scene {
	private static class Constants {
		public static final String waterTileRenderer   = "waterTileRenderer";
		public static final String preRenderTag        = Constants.waterTileRenderer + "-preRender";
		public static final String postRenderTag       = Constants.waterTileRenderer + "-postRender";
	}
	
	private boolean isDepthTestEnable;
	private Texture reflectionTexture, refractionTexture;
	private int reflectionTextureUnit, refractionTextureUnit;
	private WaterTileShaderProgram shaderProgram;
	private float                  waveStrength;
	private float waterReflectivity;
	private float shineDamper, lightReflectivity;
	
	public WaterTileRenderer(
			String name,
			WaterTileShaderProgram shaderProgram,
			WaterTileModel waterTileModel,
			Camera camera,
			Texture reflectionTexture,
			Texture refractionTexture,
			float waveStrength,
			float waterReflectivity,
			float shineDamper,
			float lightReflectivity
	) {
		super(name, shaderProgram, camera, new SceneModel[]{waterTileModel}, reflectionTexture, refractionTexture);
		
		this.shaderProgram = shaderProgram;
		this.reflectionTexture = reflectionTexture;
		this.refractionTexture = refractionTexture;
		this.waveStrength = waveStrength;
		this.waterReflectivity = waterReflectivity;
		this.shineDamper = shineDamper;
		this.lightReflectivity = lightReflectivity;
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.isDepthTestEnable = gl.glIsEnabled(GL4.GL_DEPTH_TEST);
		if (!this.isDepthTestEnable) {
			gl.glEnable(GL4.GL_DEPTH_TEST);
		}
		
		this.reflectionTextureUnit = TextureUnitManager.getInstance().getTextureUnit();
		gl.glActiveTexture(GL4.GL_TEXTURE0 + this.reflectionTextureUnit);
		this.reflectionTexture.bind(gl);
		this.shaderProgram.setReflectionTextureUniform(gl, this.reflectionTextureUnit);
		
		this.refractionTextureUnit = TextureUnitManager.getInstance().getTextureUnit();
		gl.glActiveTexture(GL4.GL_TEXTURE0 + this.refractionTextureUnit);
		this.refractionTexture.bind(gl);
		this.shaderProgram.setRefractionTextureUniform(gl, this.refractionTextureUnit);
		
		this.shaderProgram.setWaveStrengthUniform(gl, this.waveStrength);
		
		this.shaderProgram.setCameraPositionUniform(gl, super.getCamera().getEye());
		
		this.shaderProgram.setWaterReflectivityUniform(gl, this.waterReflectivity);
		
		this.shaderProgram.setShineDamperUniform(gl, this.shineDamper);
		this.shaderProgram.setLightReflectivityUniform(gl, this.lightReflectivity);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		super.postRender(gl);
		
		if (!this.isDepthTestEnable) {
			gl.glDisable(GL4.GL_DEPTH_TEST);
		}
		
		TextureUnitManager.getInstance().freeTextureUnit(this.reflectionTextureUnit);
		TextureUnitManager.getInstance().freeTextureUnit(this.refractionTextureUnit);
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
}

package water.basicWater.waterTile;

import base.objects.renderer.scene.Scene;
import base.objects.renderer.scene.camera.Camera;
import base.objects.renderer.scene.sceneModel.SceneModel;
import base.objects.textures.Texture;
import com.jogamp.opengl.GL4;
import water.basicWater.waterTile.waterTileShaderProgram.WaterTileShaderProgram;

public class WaterTileRenderer extends Scene {
	private static class Constants {
		public static final String waterTileRenderer = "waterTileRenderer";
		public static final String preRenderTag = Constants.waterTileRenderer + "-preRender";
		public static final String postRenderTag = Constants.waterTileRenderer + "-postRender";
	}
	
	private boolean isDepthTestEnable;
	private Texture reflectionTexture, refractionTexture;
	private WaterTileShaderProgram shaderProgram;
	
	public WaterTileRenderer(String name, WaterTileShaderProgram shaderProgram, WaterTileModel waterTileModel, Camera camera, Texture reflectionTexture, Texture refractionTexture) {
		super(name, shaderProgram, camera, new SceneModel[] {waterTileModel }, reflectionTexture, refractionTexture);
		
		this.reflectionTexture = reflectionTexture;
		this.refractionTexture = refractionTexture;
		this.shaderProgram = shaderProgram;
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
}

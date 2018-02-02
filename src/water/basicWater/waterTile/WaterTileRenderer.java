package water.basicWater.waterTile;

import base.objects.model.ModelBase;
import base.objects.renderer.scene.Scene;
import base.objects.renderer.scene.camera.Camera;
import com.jogamp.opengl.GL4;
import water.basicWater.waterTile.waterTileShaderProgram.WaterTileShaderProgram;

public class WaterTileRenderer extends Scene {
	private static class Constants {
		public static final String waterTileRenderer = "waterTileRenderer";
		public static final String preRenderTag = Constants.waterTileRenderer + "-preRender";
		public static final String postRenderTag = Constants.waterTileRenderer + "-postRender";
	}
	
	private boolean isBlendEnabled;
	
	public WaterTileRenderer(String name, WaterTileShaderProgram shaderProgram, WaterTileModel waterTileModel, Camera camera) {
		super(name, shaderProgram, new ModelBase[] { waterTileModel }, camera);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.isBlendEnabled = gl.glIsEnabled(GL4.GL_BLEND);
		if (!this.isBlendEnabled) {
			gl.glEnable(GL4.GL_BLEND);
		}
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		super.postRender(gl);
		
		if (!this.isBlendEnabled) {
			gl.glDisable(GL4.GL_BLEND);
		}
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
}

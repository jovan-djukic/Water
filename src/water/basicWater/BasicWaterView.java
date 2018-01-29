package water.basicWater;

import base.objects.renderer.RendererBase;
import base.objects.renderer.scene.Scene;
import base.objects.textures.CubeMapTexture;
import base.views.GLView;
import basic.BasicCamera;
import com.jogamp.newt.opengl.GLWindow;
import skybox.Skybox;
import water.basicWater.basicTerrain.BasicTerrainScene;
import water.basicWater.basicTerrain.basicTerrainShaderProgram.BasicTerrainShaderProgram;

import java.util.ArrayList;

public class BasicWaterView extends GLView {
	
	private static class Constants {
		public static final String basicTerrain    = "basicTerrain";
		public static final float  x               = -5;
		public static final float  z               = 5;
		public static final float  width           = 10;
		public static final float  height          = 10;
		public static final int    rows            = 400;
		public static final int    columns         = 400;
		public static final int    numberOfOctaves = 4;
		public static final float  persistence     = 0.5f;
		public static final float  scaleX          = 2.0f / Constants.width;
		public static final float  scaleY          = 2.0f / Constants.height;
		public static final float  amplitude       = 5;
		public static final float  power           = 1;
		
		
		public static final String positiveX = "skyboxImages/right.png";
		public static final String negativeX = "skyboxImages/left.png";
		public static final String positiveY = "skyboxImages/top.png";
		public static final String negativeY = "skyboxImages/bottom.png";
		public static final String positiveZ = "skyboxImages/back.png";
		public static final String negativeZ = "skyboxImages/front.png";
		
		public static final String skybox = "skybox";
		public static final float skyboxSize = 100;
	}
	
	protected BasicTerrainShaderProgram basicTerrainShaderProgram;
	protected BasicTerrainScene         basicTerrain;
	protected BasicCamera               basicCamera;
	protected Scene                     scene;
	
	protected CubeMapTexture cubeMapTexture;
	protected Skybox skybox;
	
	@Override
	protected ArrayList<RendererBase> getRenderers() {
		ArrayList<RendererBase> rendererBases = super.getRenderers();
		
		this.basicCamera = new BasicCamera();
		this.skybox = new Skybox(
				Constants.skybox, this.basicCamera, Constants.skyboxSize, this.getClass(),
				Constants.positiveX,
				Constants.negativeX,
				Constants.positiveY,
				Constants.negativeY,
				Constants.positiveZ,
				Constants.negativeZ
		);
		
		rendererBases.add(this.skybox);
		
		this.basicTerrain = new BasicTerrainScene(Constants.basicTerrain, basicCamera, Constants.x, Constants.z,
				Constants.width, Constants.height, Constants.rows, Constants.columns, Constants.numberOfOctaves,
				Constants.persistence, Constants.scaleX, Constants.scaleY, Constants.amplitude, Constants.power);
		
		rendererBases.add(this.basicTerrain);
		
		return rendererBases;
	}
	
	@Override
	protected void setListeners(GLWindow window) {
		window.addMouseListener(this.basicCamera);
	}
	
	@Override
	protected void onReshape(int width, int height) {
		this.basicCamera.setAspectRation(width, height);
	}
}

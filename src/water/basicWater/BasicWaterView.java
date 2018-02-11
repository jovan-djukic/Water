package water.basicWater;

import base.objects.renderer.RendererBase;
import base.views.GLView;
import com.jogamp.newt.opengl.GLWindow;
import skybox.Skybox;
import terrain.perlinNoiseTerrain.PerlinNoiseTerrain;
import tests.texturePreviewRenderer.TexturePreviewRenderer;
import tests.texturePreviewRenderer.depthTexturePreviewRenderer.DepthTexturePreviewRenderer;
import tests.texturePreviewRenderer.rgbaTexturePreviewRenderer.RGBATexturePreviewRenderer;
import water.basicWater.basicWaterTerrainScene.BasicWaterTerrainScene;
import water.basicWater.basicWaterTerrainScene.basicWaterTerrainShaderProgram.BasicWaterTerrainShaderProgram;
import water.basicWater.textureRenderer.ReflectionTextureRenderer;
import water.basicWater.waterTile.WaterTileModel;
import water.basicWater.waterTile.WaterTileRenderer;
import water.basicWater.waterTile.waterTileShaderProgram.WaterTileShaderProgram;

import java.util.ArrayList;

public class BasicWaterView extends GLView {
	
	private static class Constants {
		public static final String imagesDirectory = "images/";
		
		public static class BasicWaterCameraConstants {
			public static final float x                 = 0;
			public static final float y                 = 0;
			public static final float z                 = 10;
			public static final float fieldOfView       = (float) Math.toRadians(45);
			public static final float aspectRation      = 1;
			public static final float nearClippingPlane = 0.1f;
			public static final float farClippingPlane  = 100;
			public static final float xAngle            = 30;
			public static final float yAngle            = 0;
			public static final float zAngle            = 0;
			public static final float zStep             = 0.5f;
		}
		
		public static class SkyboxConstants {
			public static final String skybox          = "skybox";
			public static final float  skyboxSize      = 100;
			public static final String imagesDirectory = Constants.imagesDirectory + "skyboxImages/";
			public static final String positiveX       = SkyboxConstants.imagesDirectory + "right.png";
			public static final String negativeX       = SkyboxConstants.imagesDirectory + "left.png";
			public static final String positiveY       = SkyboxConstants.imagesDirectory + "top.png";
			public static final String negativeY       = SkyboxConstants.imagesDirectory + "bottom.png";
			public static final String positiveZ       = SkyboxConstants.imagesDirectory + "back.png";
			public static final String negativeZ       = SkyboxConstants.imagesDirectory + "front.png";
		}
		
		public static class PerlinNoiseTerrainConstants {
			public static final String perlinNoiseTerrain = "perlinNoiseTerrain";
			public static final float  x                  = -5;
			public static final float  z                  = 5;
			public static final float  width              = 10;
			public static final float  height             = 10;
			public static final int    rows               = 400;
			public static final int    columns            = 400;
			public static final int    numberOfOctaves    = 4;
			public static final float  persistence        = 0.5f;
			public static final float  scaleX             = 2.0f / PerlinNoiseTerrainConstants.width;
			public static final float  scaleY             = 2.0f / PerlinNoiseTerrainConstants.height;
			public static final float  amplitude          = 5;
			public static final float  power              = 1;
		}
		
		public static class BasicTerrainConstants {
			public static final String basicTerrain    = "basicWaterTerrainScene";
			public static final String imagesDirectory = Constants.imagesDirectory + "terrainImages/";
			public static final String sandTexture     = BasicTerrainConstants.imagesDirectory + "sand.png";
			public static final String grassTexture    = BasicTerrainConstants.imagesDirectory + "grass.png";
		}
		
		public static class WaterTileConstants {
			public static final String waterTile = "waterTile";
			public static final String waterTileRenderer = "waterTileRenderer";
			public static final float  x                  = -5;
			public static final float  z                  = 5;
			public static final float  width              = 10;
			public static final float  height             = 10;
			public static final int    rows               = 400;
			public static final int    columns            = 400;
			public static final int    numberOfOctaves    = 4;
			public static final float  persistence        = 0.5f;
			public static final float  scaleX             = 2.0f / PerlinNoiseTerrainConstants.width;
			public static final float  scaleY             = 2.0f / PerlinNoiseTerrainConstants.height;
			public static final float  amplitude          = 5;
			public static final float  power              = 1;
		}
		
		public static class ReflectionTextureRendererConstants {
			public static final String reflecionTextureRenderer = "reflectionTextureRenderer";
			public static final int    width                    = 800;
			public static final int    height                   = 800;
		}
	}
	
	private BasicWaterTerrainScene    basicTerrain;
	private PerlinNoiseTerrain        perlinNoiseTerrain;
	private BasicWaterCamera          basicWaterCamera;
	private Skybox                    skybox;
	private ReflectionTextureRenderer reflectionTextureRenderer;
	
	private WaterTileShaderProgram waterTileShaderProgram;
	private WaterTileModel         waterTileModel;
	private WaterTileRenderer      waterTileRenderer;
	
	@Override
	protected ArrayList<RendererBase> getRenderers() {
		ArrayList<RendererBase> rendererBases = super.getRenderers();
		
		this.basicWaterCamera = new BasicWaterCamera(
				Constants.BasicWaterCameraConstants.x,
				Constants.BasicWaterCameraConstants.y,
				Constants.BasicWaterCameraConstants.z,
				Constants.BasicWaterCameraConstants.fieldOfView,
				Constants.BasicWaterCameraConstants.aspectRation,
				Constants.BasicWaterCameraConstants.nearClippingPlane,
				Constants.BasicWaterCameraConstants.farClippingPlane,
				Constants.BasicWaterCameraConstants.xAngle,
				Constants.BasicWaterCameraConstants.yAngle,
				Constants.BasicWaterCameraConstants.zAngle,
				Constants.BasicWaterCameraConstants.zStep
		);
		
		this.skybox = new Skybox(
				Constants.SkyboxConstants.skybox,
				this.basicWaterCamera,
				Constants.SkyboxConstants.skyboxSize,
				this.getClass(),
				Constants.SkyboxConstants.positiveX,
				Constants.SkyboxConstants.negativeX,
				Constants.SkyboxConstants.positiveY,
				Constants.SkyboxConstants.negativeY,
				Constants.SkyboxConstants.positiveZ,
				Constants.SkyboxConstants.negativeZ
		);
		
		BasicWaterTerrainShaderProgram basicWaterTerrainShaderProgram = new BasicWaterTerrainShaderProgram();
		
		this.perlinNoiseTerrain = new PerlinNoiseTerrain(
				Constants.PerlinNoiseTerrainConstants.perlinNoiseTerrain,
				Constants.PerlinNoiseTerrainConstants.x,
				Constants.PerlinNoiseTerrainConstants.z,
				basicWaterTerrainShaderProgram.getVertexPositionAttributeLocation(),
				Constants.PerlinNoiseTerrainConstants.width,
				Constants.PerlinNoiseTerrainConstants.height,
				Constants.PerlinNoiseTerrainConstants.rows,
				Constants.PerlinNoiseTerrainConstants.columns,
				Constants.PerlinNoiseTerrainConstants.numberOfOctaves,
				Constants.PerlinNoiseTerrainConstants.persistence,
				Constants.PerlinNoiseTerrainConstants.scaleX,
				Constants.PerlinNoiseTerrainConstants.scaleY,
				Constants.PerlinNoiseTerrainConstants.amplitude,
				Constants.PerlinNoiseTerrainConstants.power,
				basicWaterTerrainShaderProgram.getTextureCoordinateAttributeLocation()
		);
		
		this.basicTerrain = new BasicWaterTerrainScene(
				Constants.BasicTerrainConstants.basicTerrain,
				basicWaterCamera,
				basicWaterTerrainShaderProgram,
				this.perlinNoiseTerrain,
				this.getClass(),
				Constants.BasicTerrainConstants.grassTexture,
				Constants.BasicTerrainConstants.sandTexture
		);
		
		this.waterTileShaderProgram = new WaterTileShaderProgram();
		this.waterTileModel = new WaterTileModel(
				Constants.WaterTileConstants.waterTile,
				Constants.WaterTileConstants.x,
				0,
				Constants.WaterTileConstants.z,
				Constants.WaterTileConstants.width,
				Constants.WaterTileConstants.height,
				this.waterTileShaderProgram.getVertexPositionAttributeLocation()
		);
		this.waterTileRenderer = new WaterTileRenderer(
				Constants.WaterTileConstants.waterTileRenderer,
				this.waterTileShaderProgram,
				this.waterTileModel,
				this.basicWaterCamera
		);
		
		this.reflectionTextureRenderer = new ReflectionTextureRenderer(
				Constants.ReflectionTextureRendererConstants.reflecionTextureRenderer,
				new RendererBase[] {
						this.skybox,
						this.basicTerrain,
				},
				Constants.ReflectionTextureRendererConstants.width,
				Constants.ReflectionTextureRendererConstants.height
		);
		
		rendererBases.add(this.reflectionTextureRenderer);
		
		TexturePreviewRenderer texturePreviewRenderer = new RGBATexturePreviewRenderer(this.reflectionTextureRenderer.getColorAttachment());
		//TexturePreviewRenderer texturePreviewRenderer = new DepthTexturePreviewRenderer(this.reflectionTextureRenderer.getDepthAttachment(), Constants.BasicWaterCameraConstants.nearClippingPlane, Constants.BasicWaterCameraConstants.farClippingPlane);
		
		rendererBases.add(texturePreviewRenderer);
		
		return rendererBases;
	}
	
	@Override
	protected void setListeners(GLWindow window) {
		window.addMouseListener(this.basicWaterCamera);
	}
	
	@Override
	protected void onReshape(int width, int height) {
		this.basicWaterCamera.setAspectRation(width, height);
	}
}

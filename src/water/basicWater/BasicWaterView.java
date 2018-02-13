package water.basicWater;

import base.objects.renderer.RendererBase;
import base.views.GLView;
import com.jogamp.newt.opengl.GLWindow;
import org.joml.Vector3f;
import org.joml.Vector4f;
import skybox.Skybox;
import terrain.perlinNoiseTerrain.PerlinNoiseTerrain;
import water.basicWater.basicWaterTerrainScene.BasicWaterTerrainScene;
import water.basicWater.basicWaterTerrainScene.basicWaterTerrainShaderProgram.BasicWaterTerrainShaderProgram;
import water.basicWater.textureRenderer.ClipDistanceRenderer;
import water.basicWater.textureRenderer.ReflectionTextureRenderer;
import water.basicWater.textureRenderer.RefractionTextureRenderer;
import water.basicWater.waterTile.WaterTileModel;
import water.basicWater.waterTile.WaterTileRenderer;
import water.basicWater.waterTile.waterTileShaderProgram.WaterTileShaderProgram;

import java.util.ArrayList;

public class BasicWaterView extends GLView {
	
	private static class Constants {
		public static final String imagesDirectory = "images/";
		
		public static class BasicWaterCamera {
			public static final float x                 = 0;
			public static final float y                 = 0;
			public static final float z                 = 10;
			public static final float fieldOfView       = (float) Math.toRadians(45);
			public static final float aspectRation      = 1;
			public static final float nearClippingPlane = 0.1f;
			public static final float farClippingPlane  = 100;
			public static final float pitch             = 30;
			public static final float yaw               = 0;
			public static final float roll              = 0;
			public static final float zStep             = 0.5f;
		}
		
		public static class Skybox {
			public static final String skybox          = "skybox";
			public static final float  skyboxSize      = 100;
			public static final String imagesDirectory = Constants.imagesDirectory + "skyboxImages/";
			public static final String positiveX       = Skybox.imagesDirectory + "right.png";
			public static final String negativeX       = Skybox.imagesDirectory + "left.png";
			public static final String positiveY       = Skybox.imagesDirectory + "top.png";
			public static final String negativeY       = Skybox.imagesDirectory + "bottom.png";
			public static final String positiveZ       = Skybox.imagesDirectory + "back.png";
			public static final String negativeZ       = Skybox.imagesDirectory + "front.png";
		}
		
		public static class PerlinNoiseTerrain {
			public static final String   perlinNoiseTerrain = "perlinNoiseTerrain";
			public static final Vector3f topLeft            = new Vector3f(-5, 0, -5);
			public static final float    width              = 10;
			public static final float    height             = 10;
			public static final int      rows               = 400;
			public static final int      columns            = 400;
			public static final int      numberOfOctaves    = 4;
			public static final float    persistence        = 0.5f;
			public static final float    scaleX             = 2.0f / PerlinNoiseTerrain.width;
			public static final float    scaleY             = 2.0f / PerlinNoiseTerrain.height;
			public static final float    amplitude          = 5;
			public static final float    power              = 1;
		}
		
		public static class BasicWaterTerrainScene {
			public static final String basicTerrain    = "basicWaterTerrainScene";
			public static final String imagesDirectory = Constants.imagesDirectory + "terrainImages/";
			public static final String sandTexture     = BasicWaterTerrainScene.imagesDirectory + "sand.png";
			public static final String grassTexture    = BasicWaterTerrainScene.imagesDirectory + "grass.png";
		}
		
		public static class ReflectionTextureRenderer {
			public static final String   reflecionTextureRenderer = "reflectionTextureRenderer";
			public static final int      width                    = 800;
			public static final int      height                   = 800;
		}
		
		public static class RefractionTextureRenderer {
			public static final String   refractionTextureRenderer = "refractionTextureRenderer";
			public static final int      width                    = 800;
			public static final int      height                   = 800;
		}
		
		public static class ClipDistanceRenderer {
			public static final String   name                    = "clipDistanceRenderer";
			public static final Vector4f reflectionClippingPlane = new Vector4f(0, 1, 0, 0);
			public static final Vector4f refractionClippingPlane = new Vector4f(0, -1, 0, 0);
		}
		
		public static class WaterTile {
			public static final String   name            = "waterTile";
			public static final Vector3f topLeft         = PerlinNoiseTerrain.topLeft;
			public static final float    width           = PerlinNoiseTerrain.width;
			public static final float    height          = PerlinNoiseTerrain.height;
			public static final String   imagesDirectory = Constants.imagesDirectory + "waterTileImages/";
			public static final String   dudvTexture     = WaterTile.imagesDirectory + "dudvTexture.png";
			public static final float    scaleX          = 0.1f;
			public static final float    scaleY          = 0.1f;
			public static final float    waveStrength    = 0.01f;
			public static final float    waveSpeed       = 0.001f;
		}
		
		public static class WaterTileRenderer {
			public static final String name = "waterTileRenderer";
		}
	}
	
	private BasicWaterTerrainShaderProgram shaderProgram;
	private BasicWaterTerrainScene         basicTerrainScene;
	private PerlinNoiseTerrain             perlinNoiseTerrain;
	private BasicWaterCamera               basicWaterCamera;
	private Skybox                         skybox;
	private ReflectionTextureRenderer      reflectionTextureRenderer;
	private RefractionTextureRenderer      refractionTextureRenderer;
	private WaterTileShaderProgram         waterTileShaderProgram;
	private WaterTileRenderer              waterTileRenderer;
	
	
	@Override
	protected ArrayList<RendererBase> getRenderers() {
		ArrayList<RendererBase> rendererBases = super.getRenderers();
		
		this.basicWaterCamera = new BasicWaterCamera(
				Constants.BasicWaterCamera.x,
				Constants.BasicWaterCamera.y,
				Constants.BasicWaterCamera.z,
				Constants.BasicWaterCamera.fieldOfView,
				Constants.BasicWaterCamera.aspectRation,
				Constants.BasicWaterCamera.nearClippingPlane,
				Constants.BasicWaterCamera.farClippingPlane,
				Constants.BasicWaterCamera.pitch,
				Constants.BasicWaterCamera.yaw,
				Constants.BasicWaterCamera.roll,
				Constants.BasicWaterCamera.zStep
		);
		
		this.skybox = new Skybox(
				Constants.Skybox.skybox,
				this.basicWaterCamera,
				Constants.Skybox.skyboxSize,
				this.getClass(),
				Constants.Skybox.positiveX,
				Constants.Skybox.negativeX,
				Constants.Skybox.positiveY,
				Constants.Skybox.negativeY,
				Constants.Skybox.positiveZ,
				Constants.Skybox.negativeZ
		);
		
		this.shaderProgram = new BasicWaterTerrainShaderProgram();
		
		this.perlinNoiseTerrain = new PerlinNoiseTerrain(
				Constants.PerlinNoiseTerrain.perlinNoiseTerrain,
				Constants.PerlinNoiseTerrain.topLeft,
				Constants.PerlinNoiseTerrain.width,
				Constants.PerlinNoiseTerrain.height,
				Constants.PerlinNoiseTerrain.rows,
				Constants.PerlinNoiseTerrain.columns,
				this.shaderProgram.getVertexAttributeLocation(),
				Constants.PerlinNoiseTerrain.numberOfOctaves,
				Constants.PerlinNoiseTerrain.persistence,
				Constants.PerlinNoiseTerrain.scaleX,
				Constants.PerlinNoiseTerrain.scaleY,
				Constants.PerlinNoiseTerrain.amplitude,
				Constants.PerlinNoiseTerrain.power,
				this.shaderProgram.getTexelAttributeLocation()
		);
		
		this.basicTerrainScene = new BasicWaterTerrainScene(
				Constants.BasicWaterTerrainScene.basicTerrain,
				basicWaterCamera,
				this.shaderProgram,
				this.perlinNoiseTerrain,
				this.getClass(),
				Constants.BasicWaterTerrainScene.grassTexture,
				Constants.BasicWaterTerrainScene.sandTexture
		);
		
		this.reflectionTextureRenderer = new ReflectionTextureRenderer(
				Constants.ReflectionTextureRenderer.reflecionTextureRenderer,
				new RendererBase[] {
						this.skybox,
						new ClipDistanceRenderer(
							Constants.ClipDistanceRenderer.name,
							new RendererBase[]{
									this.basicTerrainScene
							},
							this.shaderProgram,
							Constants.ClipDistanceRenderer.reflectionClippingPlane
						)
				},
				Constants.ReflectionTextureRenderer.width,
				Constants.ReflectionTextureRenderer.height,
				this.basicWaterCamera
		);
		
		rendererBases.add(this.reflectionTextureRenderer);
		
		this.refractionTextureRenderer = new RefractionTextureRenderer(
				Constants.RefractionTextureRenderer.refractionTextureRenderer,
				new RendererBase[] {
						new ClipDistanceRenderer(
								Constants.ClipDistanceRenderer.name,
								new RendererBase[] {
										this.basicTerrainScene,
								},
								this.shaderProgram,
								Constants.ClipDistanceRenderer.refractionClippingPlane
						)
				},
				Constants.RefractionTextureRenderer.width,
				Constants.RefractionTextureRenderer.height
		);
		
		
		rendererBases.add(this.refractionTextureRenderer);
		
		this.waterTileShaderProgram = new WaterTileShaderProgram();
		
		this.waterTileRenderer = new WaterTileRenderer(
				Constants.WaterTileRenderer.name,
				this.waterTileShaderProgram,
				new WaterTileModel(
						Constants.WaterTile.name,
						Constants.WaterTile.topLeft,
						Constants.WaterTile.width,
						Constants.WaterTile.height,
						this.waterTileShaderProgram.getVertexAttributeLocation(),
						this.waterTileShaderProgram.getTexelAttributeLocation()
				),
				this.basicWaterCamera,
				this.reflectionTextureRenderer.getColorAttachment(),
				this.refractionTextureRenderer.getColorAttachment(),
				this.getClass(),
				Constants.WaterTile.dudvTexture,
				Constants.WaterTile.scaleX,
				Constants.WaterTile.scaleY,
				Constants.WaterTile.waveStrength,
				Constants.WaterTile.waveSpeed
		);
		
		rendererBases.add(this.skybox);
		rendererBases.add(this.basicTerrainScene);
		rendererBases.add(this.waterTileRenderer);
		
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

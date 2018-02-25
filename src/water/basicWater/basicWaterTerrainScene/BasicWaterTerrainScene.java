package water.basicWater.basicWaterTerrainScene;

import base.objects.renderer.scene.Scene;
import base.objects.renderer.scene.camera.Camera;
import base.objects.renderer.scene.sceneModel.SceneModel;
import base.objects.textures.Texture;
import base.objects.textures.TextureData;
import base.objects.textures.TextureUnitManager;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;
import shapes.Mesh;
import water.basicWater.basicWaterTerrainScene.basicWaterTerrainShaderProgram.BasicWaterTerrainShaderProgram;

import java.nio.IntBuffer;

public class BasicWaterTerrainScene extends Scene {
	private static class Constants {
		public static final String terrainTexture = "terrainTexture";
		public static final String basicTerrain   = "basicWaterTerrainScene";
		public static final String initTag        = Constants.basicTerrain + "init";
		public static final String preRenderTag   = Constants.basicTerrain + "preRender";
	}
	
	private BasicWaterTerrainShaderProgram shaderProgram;
	private Texture                        terrainTexture;
	private String                         terrainTextureFileName;
	private Class                          scope;
	private boolean                        isDepthTestEnabled;
	private IntBuffer                      polygonMode;
	private int                            textureUnit;
	
	private BasicWaterTerrainScene(String name, BasicWaterTerrainShaderProgram shaderProgram, Camera camera, Mesh terrain, Texture terrainTexture, Class scope, String terrainTextureFileName) {
		super(
				name,
				shaderProgram,
				camera,
				new SceneModel[] {
						terrain
				},
				terrainTexture
		);
		
		this.shaderProgram = shaderProgram;
		this.terrainTexture = terrainTexture;
		this.terrainTextureFileName = terrainTextureFileName;
		this.scope = scope;
		
		this.polygonMode = IntBuffer.allocate(1);
	}
	
	public BasicWaterTerrainScene(String name, Camera camera, BasicWaterTerrainShaderProgram basicWaterTerrainShaderProgram, Mesh terrain, Class scope, String sandTextureFileName) {
		this(name, basicWaterTerrainShaderProgram, camera, terrain, new Texture(Constants.terrainTexture, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE), scope, sandTextureFileName);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		
		this.terrainTexture.bind(gl)
				.texImage2D(gl, 0, TextureData.decodePngImage(this.scope, this.terrainTextureFileName, PNGDecoder.Format.RGBA))
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.textureUnit = TextureUnitManager.getInstance().getTextureUnit();
		gl.glActiveTexture(GL4.GL_TEXTURE0 + this.textureUnit);
		this.terrainTexture.bind(gl);
		this.shaderProgram.setTerrainTextureUniform(gl, this.textureUnit);
		
		this.isDepthTestEnabled = gl.glIsEnabled(GL4.GL_DEPTH_TEST);
		
		if (!this.isDepthTestEnabled) {
			gl.glEnable(GL4.GL_DEPTH_TEST);
		}
		
		gl.glGetIntegerv(GL4.GL_POLYGON_MODE, this.polygonMode);
		this.polygonMode.rewind();
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_FILL);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	
	@Override
	protected void postRender(GL4 gl) {
		TextureUnitManager.getInstance().freeTextureUnit(this.textureUnit);
		
		if (!this.isDepthTestEnabled) {
			gl.glDisable(GL4.GL_DEPTH_TEST);
		}
		
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, this.polygonMode.get(0));
		this.polygonMode.rewind();
		
		super.postRender(gl);
	}
}

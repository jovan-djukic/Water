package water.basicWater.basicWaterTerrainScene;

import base.objects.renderer.scene.Scene;
import base.objects.renderer.scene.camera.Camera;
import base.objects.renderer.scene.sceneModel.SceneModel;
import base.objects.textures.Texture;
import base.objects.textures.TextureData;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;
import shapes.Mesh;
import water.basicWater.basicWaterTerrainScene.basicWaterTerrainShaderProgram.BasicWaterTerrainShaderProgram;

import java.nio.IntBuffer;

public class BasicWaterTerrainScene extends Scene {
	private static class Constants {
		public static final String grassTexture = "grass";
		public static final String sandTexture  = "sand";
		public static final String basicTerrain = "basicWaterTerrainScene";
		public static final String initTag      = Constants.basicTerrain + "init";
		public static final String preRenderTag = Constants.basicTerrain + "preRender";
	}
	
	private BasicWaterTerrainShaderProgram shaderProgram;
	private Texture                        sandTexture, grassTexture;
	private String sandTextureFileName, grassTextureFileName;
	private Class   scope;
	private boolean isCullFaceEnabled, isDepthTestEnabled;
	private IntBuffer cullFace, polygonMode;
	
	private BasicWaterTerrainScene(String name, BasicWaterTerrainShaderProgram shaderProgram, Camera camera, Mesh terrain, Texture grassTexture, Texture sandTexture, Class scope, String grassTextureFileName, String sandTextureFileName) {
		super(
				name,
				shaderProgram,
				camera,
				new SceneModel[] {
						terrain
				},
				sandTexture,
				grassTexture
		);
		
		this.shaderProgram = shaderProgram;
		this.grassTexture = grassTexture;
		this.sandTexture = sandTexture;
		this.sandTextureFileName = sandTextureFileName;
		this.grassTextureFileName = grassTextureFileName;
		this.scope = scope;
		
		this.cullFace = IntBuffer.allocate(1);
		this.polygonMode = IntBuffer.allocate(1);
	}
	
	public BasicWaterTerrainScene(String name, Camera camera, BasicWaterTerrainShaderProgram basicWaterTerrainShaderProgram, Mesh terrain, Class scope, String grassTextureFileName, String sandTextureFileName) {
		this(name, basicWaterTerrainShaderProgram, camera, terrain, new Texture(Constants.grassTexture, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE), new Texture(Constants.sandTexture, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE), scope, grassTextureFileName, sandTextureFileName);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.grassTexture.bind(gl)
				.texImage2D(gl, 0,TextureData.decodePngImage(this.scope, this.grassTextureFileName, PNGDecoder.Format.RGBA))
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
		
		this.sandTexture.bind(gl)
				.texImage2D(gl, 0, TextureData.decodePngImage(this.scope, this.sandTextureFileName, PNGDecoder.Format.RGBA))
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	protected void setGrassTexture(GL4 gl) {
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		this.grassTexture.bind(gl);
		this.shaderProgram.setGrassTextureUniform(gl, 0);
	}
	
	protected void setSandTexture(GL4 gl) {
		gl.glActiveTexture(GL4.GL_TEXTURE1);
		this.sandTexture.bind(gl);
		this.shaderProgram.setSandTextureUniform(gl, 1);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.setGrassTexture(gl);
		this.setSandTexture(gl);
		
		this.isCullFaceEnabled = gl.glIsEnabled(GL4.GL_CULL_FACE);
		
		if (this.isCullFaceEnabled) {
			gl.glGetIntegerv(GL4.GL_CULL_FACE, this.cullFace);
			this.cullFace.rewind();
		} else {
			gl.glEnable(GL4.GL_CULL_FACE);
		}
		
		gl.glCullFace(GL4.GL_BACK);
		
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
		if (!this.isCullFaceEnabled) {
			gl.glDisable(GL4.GL_CULL_FACE);
		} else {
			gl.glCullFace(this.cullFace.get(0));
			this.cullFace.rewind();
		}
		
		if (!this.isDepthTestEnabled) {
			gl.glDisable(GL4.GL_DEPTH_TEST);
		}
		
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, this.polygonMode.get(0));
		this.polygonMode.rewind();
		
		super.postRender(gl);
	}
}

package water.basicWater.basicTerrain;

import base.objects.model.ModelBase;
import base.objects.renderer.scene.Scene;
import base.objects.renderer.scene.camera.Camera;
import base.objects.textures.Texture;
import base.objects.textures.TextureData;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;
import shapes.Mesh;
import terrain.perlinNoiseTerrain.PerlinNoiseTerrain;
import water.basicWater.basicTerrain.basicTerrainShaderProgram.BasicTerrainShaderProgram;

import java.nio.IntBuffer;

public class BasicTerrainScene extends Scene {
	private static class Constants {
		public static final String grassTexture = "grass";
		public static final String sandTexture  = "sand";
		public static final String basicTerrain = "basicTerrain";
		public static final String initTag      = Constants.basicTerrain + "init";
		public static final String preRenderTag = Constants.basicTerrain + "preRender";
	}
	
	private BasicTerrainShaderProgram shaderProgram;
	private Texture                   sandTexture, grassTexture;
	private String sandTextureFileName, grassTextureFileName;
	private Class scope;
	private boolean isCullFaceEnabled, isDepthTestEnabled;
	private IntBuffer cullFace, polygonMode;
	
	private BasicTerrainScene(String name, BasicTerrainShaderProgram shaderProgram, Camera camera, Mesh terrain, Texture grassTexture, Texture sandTexture, Class scope, String grassTextureFileName, String sandTextureFileName) {
		super(
				name,
				shaderProgram,
				new ModelBase[] {
						terrain
				},
				camera, sandTexture, grassTexture
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
	
	public BasicTerrainScene(String name, Camera camera, BasicTerrainShaderProgram basicTerrainShaderProgram, Mesh terrain, Class scope, String grassTextureFileName, String sandTextureFileName) {
		this(name, basicTerrainShaderProgram, camera, terrain, new Texture(Constants.grassTexture), new Texture(Constants.sandTexture), scope, grassTextureFileName, sandTextureFileName);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.grassTexture.bind(gl)
				.texImage2D(gl, GL4.GL_TEXTURE_2D, 0, GL4.GL_RGBA, GL4.GL_RGBA, TextureData.decodePngImage(this.scope, this.grassTextureFileName, PNGDecoder.Format.RGBA))
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
		
		this.sandTexture.bind(gl)
				.texImage2D(gl, GL4.GL_TEXTURE_2D, 0, GL4.GL_RGBA, GL4.GL_RGBA, TextureData.decodePngImage(this.scope, this.sandTextureFileName, PNGDecoder.Format.RGBA))
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		this.grassTexture.bind(gl);
		gl.glActiveTexture(GL4.GL_TEXTURE1);
		this.sandTexture.bind(gl);
		
		gl.glUniform1i(this.shaderProgram.getGrassTextureUniformLocation(), 0);
		gl.glUniform1i(this.shaderProgram.getSandTextureUniformLocation(), 1);
		
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

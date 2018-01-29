package skybox;

import base.objects.model.ModelBase;
import base.objects.renderer.Renderer;
import base.objects.renderer.scene.camera.Camera;
import base.objects.textures.CubeMapTexture;
import base.objects.textures.TextureData;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.joml.Matrix4f;
import shapes.Box;
import skybox.shaderProgram.SkyboxShaderProgram;

import java.nio.IntBuffer;


public class Skybox extends Renderer {
	private static class Constants {
		public static final String cubeMapTexture         = "-cubeMapTexture";
		public static final String box         = "-box";
		public static final String skybox         = "skybox-";
		public static final String initTag        = Constants.skybox + "init";
		public static final String preRenderTag   = Constants.skybox + "preRender";
		public static final String postRenderTag = Constants.skybox + "postRender";
		public static final int transformArraylength = 16;
	}
	
	private Camera camera;
	private CubeMapTexture cubeMapTexture;
	private SkyboxShaderProgram skyboxShaderProgram;
	private Class scope;
	private String xPositiveFileName, xNegativeFileName;
	private String yPositiveFileName, yNegativeFileName;
	private String zPositiveFileName, zNegativeFileName;
	private boolean isCullFaceEnabled;
	private IntBuffer cullFace;
	private Matrix4f transform;
	private float transformArray[];
	
	protected Skybox(
			String name, Camera camera, SkyboxShaderProgram shaderProgram,
			float size, CubeMapTexture cubeMapTexture, Class scope,
			String xPositiveFileName, String xNegativeFileName,
			String yPositiveFileName, String yNegativeFileName,
			String zPositiveFileName, String zNegativeFileName
	) {
		super(
			name,
			shaderProgram,
			new ModelBase[] {
					new Box(name + Constants.box, size, size, size, shaderProgram.getVertexPositionAttributeLocation())
			},
			cubeMapTexture
		);
		
		this.camera = camera;
		this.cubeMapTexture = cubeMapTexture;
		this.skyboxShaderProgram = shaderProgram;
		this.scope = scope;
		
		this.xPositiveFileName = xPositiveFileName;
		this.xNegativeFileName = xNegativeFileName;
		this.yPositiveFileName = yPositiveFileName;
		this.yNegativeFileName = yNegativeFileName;
		this.zPositiveFileName = zPositiveFileName;
		this.zNegativeFileName = zNegativeFileName;
		
		this.cullFace = IntBuffer.allocate(1);
		
		this.transform = new Matrix4f();
		this.transformArray = new float[Constants.transformArraylength];
	}
	
	public Skybox(
			String name, Camera camera, float size, Class scope,
			String xPositiveFileName, String xNegativeFileName,
			String yPositiveFileName, String yNegativeFileName,
			String zPositiveFileName, String zNegativeFileName
			) {
		this(
			name, camera, new SkyboxShaderProgram(),
			size, new CubeMapTexture(name + Constants.cubeMapTexture), scope,
			xPositiveFileName, xNegativeFileName,
			yPositiveFileName, yNegativeFileName,
			zPositiveFileName, zNegativeFileName
		);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.cubeMapTexture.init(gl);
		
		this.cubeMapTexture.bind(gl)
			.bufferXPositive(gl, 0, GL4.GL_RGBA, GL4.GL_RGBA, TextureData.decodePngImage(this.scope, this.xPositiveFileName, PNGDecoder.Format.RGBA))
			.bufferYPositive(gl, 0, GL4.GL_RGBA, GL4.GL_RGBA, TextureData.decodePngImage(this.scope, this.yPositiveFileName, PNGDecoder.Format.RGBA))
			.bufferZPositive(gl, 0, GL4.GL_RGBA, GL4.GL_RGBA, TextureData.decodePngImage(this.scope, this.zPositiveFileName, PNGDecoder.Format.RGBA))
			.bufferXNegative(gl, 0, GL4.GL_RGBA, GL4.GL_RGBA, TextureData.decodePngImage(this.scope, this.xNegativeFileName, PNGDecoder.Format.RGBA))
			.bufferYNegative(gl, 0, GL4.GL_RGBA, GL4.GL_RGBA, TextureData.decodePngImage(this.scope, this.yNegativeFileName, PNGDecoder.Format.RGBA))
			.bufferZNegative(gl, 0, GL4.GL_RGBA, GL4.GL_RGBA, TextureData.decodePngImage(this.scope, this.zNegativeFileName, PNGDecoder.Format.RGBA))
			.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
			.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
			.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE)
			.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE)
			.texParameteri(gl, GL4.GL_TEXTURE_WRAP_R, GL4.GL_CLAMP_TO_EDGE);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		this.cubeMapTexture.bind(gl);
		gl.glUniform1i(this.skyboxShaderProgram.getCubeMapUniformLocation(), 0);
		
		int projectionLocation = this.skyboxShaderProgram.getProjectionUniformLocation();
		this.camera.getProjection().get(this.transformArray);
		gl.glUniformMatrix4fv(projectionLocation, 1, false, this.transformArray, 0);
		
		camera.getView().get(this.transform);
		this.transform.m30(0)
				.m31(0)
				.m32(0)
				.get(this.transformArray);
		int viewLocation = this.skyboxShaderProgram.getViewUniformLocation();
		gl.glUniformMatrix4fv(viewLocation, 1, false, this.transformArray, 0);
		
		this.isCullFaceEnabled = gl.glIsEnabled(GL4.GL_CULL_FACE);
		
		if (!this.isCullFaceEnabled) {
			gl.glEnable(GL4.GL_CULL_FACE);
		} else {
			gl.glGetIntegerv(GL4.GL_CULL_FACE, this.cullFace);
			this.cullFace.rewind();
		}
		
		gl.glCullFace(GL4.GL_FRONT);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		super.postRender(gl);
		
		if (!this.isCullFaceEnabled) {
			gl.glDisable(GL4.GL_CULL_FACE);
		} else {
			gl.glCullFace(this.cullFace.get(0));
			this.cullFace.rewind();
		}
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
}

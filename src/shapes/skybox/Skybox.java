package shapes.skybox;

import base.objects.scene.camera.Camera;
import base.objects.textures.CubeMapTexture;
import base.objects.textures.TextureData;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.joml.Vector3f;
import shapes.basicShapes.Box;
import shapes.skybox.shaderProgram.SkyboxShaderProgram;


public class Skybox extends Box {
	private static class Constants {
		public static final String skybox         = "skybox-";
		public static final String initTag        = Constants.skybox + "init";
		public static final String preRenderTag   = Constants.skybox + "preRender";
		public static final String setCullFaceTag = Constants.skybox + "setCullFace";
		public static final String cubeMapTexture = "cubeMapTexture";
	}
	
	protected CubeMapTexture cubeMapTexture;
	protected SkyboxShaderProgram skyboxShaderProgram;
	protected Class scope;
	protected String xPositiveFileName, xNegativeFileName;
	protected String yPositiveFileName, yNegativeFileName;
	protected String zPositiveFileName, zNegativeFileName;
	
	public Skybox(
			String name, SkyboxShaderProgram shaderProgram, float size, Class scope,
			String xPositiveFileName, String xNegativeFileName,
			String yPositiveFileName, String yNegativeFileName,
			String zPositiveFileName, String zNegativeFileName
	) {
		super(name, shaderProgram, new Vector3f(0, 0, 0), size, size, size);
		
		this.cubeMapTexture = new CubeMapTexture(name + Constants.cubeMapTexture);
		this.skyboxShaderProgram = shaderProgram;
		this.scope = scope;
		
		this.xPositiveFileName = xPositiveFileName;
		this.xNegativeFileName = xNegativeFileName;
		this.yPositiveFileName = yPositiveFileName;
		this.yNegativeFileName = yNegativeFileName;
		this.zPositiveFileName = zPositiveFileName;
		this.zNegativeFileName = zNegativeFileName;
	}
	
	public Skybox(
			String name, Camera camera, float size, Class scope,
			String xPositiveFileName, String xNegativeFileName,
			String yPositiveFileName, String yNegativeFileName,
			String zPositiveFileName, String zNegativeFileName
			) {
		this(name, new SkyboxShaderProgram(camera), size, scope, xPositiveFileName, xNegativeFileName, yPositiveFileName, yNegativeFileName, zPositiveFileName, zNegativeFileName);
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
			.setTextureParameter(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
			.setTextureParameter(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
			.setTextureParameter(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE)
			.setTextureParameter(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE)
			.setTextureParameter(gl, GL4.GL_TEXTURE_WRAP_R, GL4.GL_CLAMP_TO_EDGE);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		this.cubeMapTexture.bind(gl);
		
		this.skyboxShaderProgram.setCubeMap(gl, 0);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	
	@Override
	protected void setCullFace(GL4 gl) {
		gl.glCullFace(GL4.GL_FRONT);
		
		this.checkForErrors(gl, Constants.setCullFaceTag);
	}
}

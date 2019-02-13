package water.basicWater.rendererDecorators;

import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.RendererBase;
import base.objects.textures.Texture;
import base.objects.textures.TextureData;
import base.objects.textures.TextureUnitManager;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;

public class DistortionRenderDecorator extends CompositeRenderer {
	public static class Constants {
		public static final String distortionRenderDecorator   = "distortionRenderDecorator";
		public static final String dudvTexture         = Constants.distortionRenderDecorator + "-dudvTexture";
		public static final String preRenderTag        = Constants.distortionRenderDecorator + "-preRender";
		public static final String postRenderTag       = Constants.distortionRenderDecorator + "-postRender";
		public static final float  milisecondsInSecond = 1000;
	}
	
	public static interface IDistortionShaderProgram {
		void setDudvTextureUniform(GL4 gl, int textureUnit);
		void setMoveFactorUniform(GL4 gl, float moveFactor);
		void setDistortionStrengthUniform(GL4 gl, float distortionStrength);
	}
	
	private Class   scope;
	private String  dudvTextureFileName;
	private Texture dudvTexture;
	private int     dudvTextureUnit;
	private long    lastTime;
	private float   distortionSpeed, moveFactor;
	private float distortionStrength;
	private IDistortionShaderProgram shaderPrograms[];
	
	public DistortionRenderDecorator(
			String name,
			RendererBase[] rendererBases,
			Class scope,
			String dudvTextureFileName,
			Texture dudvTexture,
			float distortionSpeed,
			float distortionStrength,
			IDistortionShaderProgram ...shaderPrograms
	) {
		super(name, rendererBases, dudvTexture);
		
		this.scope = scope;
		this.dudvTextureFileName = dudvTextureFileName;
		this.dudvTexture = dudvTexture;
		this.distortionSpeed = distortionSpeed;
		this.distortionStrength = distortionStrength;
		
		this.shaderPrograms = new IDistortionShaderProgram[shaderPrograms.length];
		for (int i = 0; i < this.shaderPrograms.length; i++) {
			this.shaderPrograms[i] = shaderPrograms[i];
		}
	}
	
	public DistortionRenderDecorator(
			String name,
			RendererBase[] rendererBases,
			Class scope,
			String dudvTextureFileName,
			float distortionSpeed,
			float distortionStrength,
			IDistortionShaderProgram ...shaderPrograms
	) {
		this(
				name,
				rendererBases,
				scope,
				dudvTextureFileName,
				new Texture(Constants.dudvTexture, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE),
				distortionSpeed,
				distortionStrength,
				shaderPrograms
		);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.dudvTexture.bind(gl)
				.texImage2D(gl, 0, TextureData.decodePngImage(this.scope, this.dudvTextureFileName, PNGDecoder.Format.RGBA))
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		this.dudvTextureUnit = TextureUnitManager.getInstance().getTextureUnit();
		gl.glActiveTexture(GL4.GL_TEXTURE0 + this.dudvTextureUnit);
		this.dudvTexture.bind(gl);
		
		for (IDistortionShaderProgram shaderProgram : this.shaderPrograms) {
			shaderProgram.setDudvTextureUniform(gl, this.dudvTextureUnit);
			shaderProgram.setMoveFactorUniform(gl, this.moveFactor);
			shaderProgram.setDistortionStrengthUniform(gl, this.distortionStrength);
		}
		
		super.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		TextureUnitManager.getInstance().freeTextureUnit(this.dudvTextureUnit);
		
		super.checkForErrors(gl, Constants.postRenderTag);
	}
	
	@Override
	public void update() {
		super.update();
		
		if (this.lastTime == 0) {
			this.lastTime = System.currentTimeMillis();
		} else {
			long  currentTime = System.currentTimeMillis();
			float passedTime  = (currentTime - this.lastTime) / Constants.milisecondsInSecond;
			this.lastTime = currentTime;
			
			this.moveFactor += this.distortionSpeed * passedTime;
		}
	}
}

package water.basicWater.rendererDecorators;

import base.objects.OpenGLObject;
import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.RendererBase;
import base.objects.textures.Texture;
import base.objects.textures.TextureData;
import base.objects.textures.TextureUnitManager;
import com.jogamp.opengl.GL4;
import de.matthiasmann.twl.utils.PNGDecoder;

public class NormalMapRenderDecorator extends CompositeRenderer {
	private static class Constants {
		public static final String NormalMapRenderDecorator = "normalMapRenderDecorator";
		public static final String normalMapTexture         = Constants.NormalMapRenderDecorator + "-normalMapTexture";
		public static final String preRenderTag             = Constants.NormalMapRenderDecorator + "-preRender";
		public static final String postRenderTag            = Constants.NormalMapRenderDecorator + "-postRender";
	}
	
	public static interface INormalMapShaderProgram {
		void setNormalMapTextureUniform(GL4 gl, int textureUnit);
		void setNormalEqualizationFactorUniform(GL4 gl, float normalEqualizationFactor);
	}
	
	private Class                   scope;
	private String                  normalMapTextureFileName;
	private Texture                 normalMapTexture;
	private float                   normalEqualizationFactor;
	private int                     normalMapTextureUnit;
	private INormalMapShaderProgram shaderProgams[];
	
	public NormalMapRenderDecorator(
			String name,
			RendererBase[] rendererBases,
			Class scope,
			String normalMapTextureFileName,
			Texture normalMapTexture,
			float normalEqualizationFactor,
			INormalMapShaderProgram ...shaderPrograms
	) {
		super(name, rendererBases, normalMapTexture);
		
		this.scope = scope;
		this.normalMapTextureFileName = normalMapTextureFileName;
		this.normalMapTexture = normalMapTexture;
		this.normalEqualizationFactor = normalEqualizationFactor;
		
		this.shaderProgams = new INormalMapShaderProgram[shaderPrograms.length];
		for (int i = 0; i < this.shaderProgams.length; i++) {
			this.shaderProgams[i] = shaderPrograms[i];
		}
	}
	
	public NormalMapRenderDecorator(
			String name,
			RendererBase[] rendererBases,
			Class scope,
			String normalMapTextureFileName,
			float normalEqualizationFactor,
			INormalMapShaderProgram ...shaderPrograms
	) {
		this(
				name,
				rendererBases,
				scope,
				normalMapTextureFileName,
				new Texture(Constants.normalMapTexture, GL4.GL_RGBA, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE),
				normalEqualizationFactor,
				shaderPrograms
		);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.normalMapTexture.bind(gl)
				.texImage2D(gl, 0, TextureData.decodePngImage(this.scope, this.normalMapTextureFileName, PNGDecoder.Format.RGBA))
				.texParameteri(gl, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_S, GL4.GL_MIRRORED_REPEAT)
				.texParameteri(gl, GL4.GL_TEXTURE_WRAP_T, GL4.GL_MIRRORED_REPEAT);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		this.normalMapTextureUnit = TextureUnitManager.getInstance().getTextureUnit();
		gl.glActiveTexture(GL4.GL_TEXTURE0 + this.normalMapTextureUnit);
		this.normalMapTexture.bind(gl);
		
		for (INormalMapShaderProgram shaderProgram : this.shaderProgams) {
			shaderProgram.setNormalMapTextureUniform(gl, this.normalMapTextureUnit);
			shaderProgram.setNormalEqualizationFactorUniform(gl, this.normalEqualizationFactor);
		}
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		TextureUnitManager.getInstance().freeTextureUnit(this.normalMapTextureUnit);
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
}

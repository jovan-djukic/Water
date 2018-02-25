package tests.perlinNoiseTest;

import base.objects.model.Model;
import base.objects.renderer.Renderer;
import base.objects.textures.Texture;
import base.objects.textures.TextureUnitManager;
import com.jogamp.opengl.GL4;
import perlinNoise.perlinNoiseTextureGenerator.PerlinNoiseModel;
import tests.perlinNoiseTest.twoDRShaderProgram.TwoDRShaderProgram;


public class PerlinNoiseTextureRenderer extends Renderer {
	private static class Constants {
		public static final String name                    = "perlinNoiseTestRenderer";
	}
	
	private TwoDRShaderProgram shaderProgram;
	private Texture perlinNoiseTexture;
	private int perlinNoiseTextureUnit;
	
	public PerlinNoiseTextureRenderer(TwoDRShaderProgram twoDRShaderProgram, PerlinNoiseModel perlinNoiseModel, Texture perliNoiseTexture) {
		super(Constants.name, twoDRShaderProgram, new Model[] {perlinNoiseModel}, perliNoiseTexture);
		
		this.shaderProgram = twoDRShaderProgram;
		this.perlinNoiseTexture = perliNoiseTexture;
	}
	
	public PerlinNoiseTextureRenderer(Texture perlinNoiseTexture) {
		this(
				new TwoDRShaderProgram(),
				new PerlinNoiseModel(),
				perlinNoiseTexture
		);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.perlinNoiseTextureUnit = TextureUnitManager.getInstance().getTextureUnit();
		
		gl.glActiveTexture(this.perlinNoiseTextureUnit);
		this.perlinNoiseTexture.bind(gl);
		this.shaderProgram.setSamplerUniform(gl, this.perlinNoiseTextureUnit);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		super.postRender(gl);
		
		TextureUnitManager.getInstance().freeTextureUnit(this.perlinNoiseTextureUnit);
	}
	
	@Override
	public void update() {
	
	}
}

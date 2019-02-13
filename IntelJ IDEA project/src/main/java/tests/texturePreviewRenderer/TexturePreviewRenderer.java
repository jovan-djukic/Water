package tests.texturePreviewRenderer;

import base.objects.model.Model;
import base.objects.renderer.Renderer;
import base.objects.textures.Texture;
import base.objects.textures.TextureUnitManager;
import com.jogamp.opengl.GL4;
import tests.texturePreviewRenderer.texturePreviewShaderProgram.TexturePreviewShaderProgram;

public abstract class TexturePreviewRenderer extends Renderer {
	private static class Constants {
		public static final String texturePreviewRenderer = "texturePreviewRenderer";
		public static final String texturePreviewModel    = Constants.texturePreviewRenderer + "-texturePreviewModel";
	}
	
	private Texture texture;
	private TexturePreviewShaderProgram shaderProgram;
	private int texturePreviewTextureUnit;
	
	protected TexturePreviewRenderer(TexturePreviewShaderProgram shaderProgram, Texture texture) {
		super(
				Constants.texturePreviewRenderer,
				shaderProgram,
				new Model[] {
					new TexturePreviewModel(Constants.texturePreviewModel)
				}
			);
		
		this.shaderProgram = shaderProgram;
		this.texture = texture;
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		this.texturePreviewTextureUnit = TextureUnitManager.getInstance().getTextureUnit();
		gl.glActiveTexture(GL4.GL_TEXTURE0 + this.texturePreviewTextureUnit);
		this.texture.bind(gl);
		this.shaderProgram.setSamplerUniform(gl, this.texturePreviewTextureUnit);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		super.postRender(gl);
		
		TextureUnitManager.getInstance().freeTextureUnit(this.texturePreviewTextureUnit);
	}
	
	@Override
	public void update() {
	
	}
}

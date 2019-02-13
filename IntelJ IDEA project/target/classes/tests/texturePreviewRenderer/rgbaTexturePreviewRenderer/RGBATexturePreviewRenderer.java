package tests.texturePreviewRenderer.rgbaTexturePreviewRenderer;

import base.objects.textures.Texture;
import tests.texturePreviewRenderer.TexturePreviewRenderer;
import tests.texturePreviewRenderer.rgbaTexturePreviewRenderer.rgbaTexturePreviewShaderProgram.RGBATexturePreviewShaderProgram;

public class RGBATexturePreviewRenderer extends TexturePreviewRenderer {
	
	public RGBATexturePreviewRenderer(Texture texture) {
		super(new RGBATexturePreviewShaderProgram(), texture);
	}
}

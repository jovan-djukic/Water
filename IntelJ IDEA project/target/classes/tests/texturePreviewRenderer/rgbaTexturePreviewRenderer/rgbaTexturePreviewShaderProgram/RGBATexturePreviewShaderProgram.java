package tests.texturePreviewRenderer.rgbaTexturePreviewRenderer.rgbaTexturePreviewShaderProgram;

import base.glsl.Shader;
import tests.texturePreviewRenderer.texturePreviewShaderProgram.TexturePreviewShaderProgram;

public class RGBATexturePreviewShaderProgram extends TexturePreviewShaderProgram {
	private static class Constants {
		public static final String fragmentShaderSourceFileName = "rgbaFragmentShader.glsl";
	}
	
	public RGBATexturePreviewShaderProgram() {
		super(Shader.getShaderSource(RGBATexturePreviewShaderProgram.class, Constants.fragmentShaderSourceFileName));
	}
}

package tests.texturePreviewRenderer.depthTexturePreviewRenderer.depthTexturePreviewShaderProgram;

import base.glsl.Shader;
import tests.texturePreviewRenderer.texturePreviewShaderProgram.TexturePreviewShaderProgram;

public class DepthTexturePreviewShaderProgram extends TexturePreviewShaderProgram {
	private static class Constants {
		public static final String fragmentShaderSourceFileName = "depthFragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String nearClippingPlane = "nearClippingPlane";
		public static final String farClippingPlane  = "farClippingPlane";
		public static final String uniforms[]        = {
				Uniforms.nearClippingPlane,
				Uniforms.farClippingPlane
		};
	}
	
	public DepthTexturePreviewShaderProgram() {
		super(Shader.getShaderSource(DepthTexturePreviewShaderProgram.class, Constants.fragmentShaderSourceFileName), Uniforms.uniforms);
	}
	
	public int getNearClippingPlaneUniformLocation() {
		return super.getUniformLocation(Uniforms.nearClippingPlane);
	}
	
	public int getFarClippingPlaneUniformLocation() {
		return super.getUniformLocation(Uniforms.farClippingPlane);
	}
}

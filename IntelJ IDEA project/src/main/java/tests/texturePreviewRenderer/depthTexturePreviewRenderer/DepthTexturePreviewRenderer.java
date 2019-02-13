package tests.texturePreviewRenderer.depthTexturePreviewRenderer;

import base.objects.textures.Texture;
import com.jogamp.opengl.GL4;
import tests.texturePreviewRenderer.TexturePreviewRenderer;
import tests.texturePreviewRenderer.depthTexturePreviewRenderer.depthTexturePreviewShaderProgram.DepthTexturePreviewShaderProgram;

public class DepthTexturePreviewRenderer extends TexturePreviewRenderer {
	
	private static class Constants {
		public static final String depthTexturePreviewRender = "depthTexturePreviewRender";
		public static final String preRenderTag = Constants.depthTexturePreviewRender + "-preRender";
	}
	
	private float nearClippingPlane, farClippingPlane;
	private DepthTexturePreviewShaderProgram shaderProgram;
	
	private DepthTexturePreviewRenderer(Texture texture, DepthTexturePreviewShaderProgram shaderProgram, float nearClippingPlane, float farClippingPlane) {
		super(shaderProgram, texture);
		
		this.nearClippingPlane = nearClippingPlane;
		this.farClippingPlane = farClippingPlane;
		this.shaderProgram = shaderProgram;
	}
	
	public DepthTexturePreviewRenderer(Texture texture, float nearClippingPlane, float farClippingPlane) {
		this(texture, new DepthTexturePreviewShaderProgram(), nearClippingPlane, farClippingPlane);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glUniform1f(this.shaderProgram.getNearClippingPlaneUniformLocation(), this.nearClippingPlane);
		gl.glUniform1f(this.shaderProgram.getFarClippingPlaneUniformLocation(), this.farClippingPlane);
	}
}

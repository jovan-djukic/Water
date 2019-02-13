package water.basicWater.rendererDecorators;

import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.RendererBase;
import com.jogamp.opengl.GL4;
import org.joml.Vector4f;
import water.basicWater.waterTerrain.basicWaterTerrainScene.basicWaterTerrainShaderProgram.BasicWaterTerrainShaderProgram;

public class ClippingPlaneRenderDecorator extends CompositeRenderer {
	public static interface IClippingPlaneShaderProgram {
		void setClippingPlaneUniform(GL4 gl, Vector4f clippingPlane);
	}
	
	private Vector4f clippingPlane;
	private boolean isClipDistance0Enabled;
	private IClippingPlaneShaderProgram shaderPrograms[];
	
	public ClippingPlaneRenderDecorator(String name, RendererBase[] rendererBases, Vector4f clippingPlane, IClippingPlaneShaderProgram ...shaderPrograms) {
		super(name, rendererBases);
		
		this.clippingPlane = clippingPlane;
		
		this.shaderPrograms = new IClippingPlaneShaderProgram[shaderPrograms.length];
		for (int i = 0; i < this.shaderPrograms.length; i++) {
			this.shaderPrograms[i] = shaderPrograms[i];
		}
	}
	
	@Override
	protected void preRender(GL4 gl) {
		this.isClipDistance0Enabled = gl.glIsEnabled(GL4.GL_CLIP_DISTANCE0);
		
		if (!this.isClipDistance0Enabled) {
			gl.glEnable(GL4.GL_CLIP_DISTANCE0);
		}
		
		for (IClippingPlaneShaderProgram shaderProgram : this.shaderPrograms) {
			shaderProgram.setClippingPlaneUniform(gl, this.clippingPlane);
		}
	}
	
	@Override
	protected void postRender(GL4 gl) {
		if (!this.isClipDistance0Enabled) {
			gl.glDisable(GL4.GL_CLIP_DISTANCE0);
		}
	}
}

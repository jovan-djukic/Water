package water.basicWater.basicWaterTerrainScene;

import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.RendererBase;
import com.jogamp.opengl.GL4;
import org.joml.Vector4f;
import water.basicWater.basicWaterTerrainScene.basicWaterTerrainShaderProgram.BasicWaterTerrainShaderProgram;

public class ClippingPlaneRenderer extends CompositeRenderer {
	
	private BasicWaterTerrainShaderProgram shaderProgram;
	private Vector4f clippingPlane;
	private boolean isClipDistance0Enabled;
	
	public ClippingPlaneRenderer(String name, RendererBase[] rendererBases, BasicWaterTerrainShaderProgram shaderProgram, Vector4f clippingPlane) {
		super(name, rendererBases, shaderProgram);
		
		this.shaderProgram = shaderProgram;
		this.clippingPlane = clippingPlane;
	}
	
	@Override
	protected void preRender(GL4 gl) {
		this.isClipDistance0Enabled = gl.glIsEnabled(GL4.GL_CLIP_DISTANCE0);
		
		if (!this.isClipDistance0Enabled) {
			gl.glEnable(GL4.GL_CLIP_DISTANCE0);
		}
		
		this.shaderProgram.setClippingPlaneUniform(gl, this.clippingPlane);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		if (!this.isClipDistance0Enabled) {
			gl.glDisable(GL4.GL_CLIP_DISTANCE0);
		}
	}
}

package water.basicWater.rendererDecorators;

import base.objects.OpenGLObject;
import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.RendererBase;
import base.objects.renderer.scene.light.Light;
import com.jogamp.opengl.GL4;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LightRenderDecorator extends CompositeRenderer {
	private static class Constants {
		public static final String lightRenderDecorator = "lightRenderDecorator";
		public static final String preRenderTag         = Constants.lightRenderDecorator + "-preRender";
		public static final String postRenderTag        = Constants.lightRenderDecorator + "-postRender";
	}
	
	public static interface ILightShaderProgram {
		void setLightPositionUniform(GL4 gl, Vector3f lightPosition);
		void setLightColorUniform(GL4 gl, Vector4f lightColor);
	}
	
	private Light light;
	private ILightShaderProgram shaderPrograms[];
	
	public LightRenderDecorator(String name, RendererBase[] rendererBases, Light light, ILightShaderProgram ...shaderPrograms) {
		super(name, rendererBases);
		
		this.light = light;
		
		this.shaderPrograms = new ILightShaderProgram[shaderPrograms.length];
		for (int i = 0; i < this.shaderPrograms.length; i++) {
			this.shaderPrograms[i] = shaderPrograms[i];
		}
	}
	
	@Override
	protected void preRender(GL4 gl) {
		for (ILightShaderProgram shaderProgram : this.shaderPrograms) {
			shaderProgram.setLightPositionUniform(gl, this.light.getPosition());
			shaderProgram.setLightColorUniform(gl, this.light.getColor());
		}
	}
	
	@Override
	protected void postRender(GL4 gl) {
	
	}
}

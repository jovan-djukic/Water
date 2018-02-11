package water.basicWater.basicWaterTerrainScene.basicWaterTerrainShaderProgram;

import base.glsl.Shader;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.glsl.uniformSetter.Uniform1i;
import base.glsl.uniformSetter.Uniform4f;
import base.objects.renderer.scene.SceneShaderProgram;
import com.jogamp.opengl.GL4;
import org.joml.Vector4f;

public class BasicWaterTerrainShaderProgram extends SceneShaderProgram {
	private static class Constants {
		public static final String	shaderProgramName		= "basicTerrainShaderProgram";
		public static final String	vertexShaderName		= "basicTerrainVertexShader";
		public static final String	fragmentShaderName		= "basicTerrainFragmentShader";
		public static final String	vertexShaderSource		= "vertexShader.glsl";
		public static final String	fragmentShaderSource	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String projection     = "projection";
		public static final String view           = "view";
		public static final String transform      = "transform";
		public static final String grassTexture   = "grassTexture";
		public static final String sandTexture    = "sandTexture";
		public static final String clippingPlane0 = "clippingPlane0";
		public static final String uniforms[]     = {
				Uniforms.projection,
				Uniforms.view,
				Uniforms.transform,
				Uniforms.grassTexture,
				Uniforms.sandTexture,
				Uniforms.clippingPlane0
		};
	}
	
	public BasicWaterTerrainShaderProgram() {
		super(
				Constants.shaderProgramName,
				Uniforms.uniforms,
				new VertexShader(
						Constants.vertexShaderName,
						Shader.getShaderSource(BasicWaterTerrainShaderProgram.class, Constants.vertexShaderSource)
				),
				new FragmentShader(
						Constants.fragmentShaderName,
						Shader.getShaderSource(BasicWaterTerrainShaderProgram.class, Constants.fragmentShaderSource)
				)
		);
	}
	
	@Override
	protected String getViewMatrixUniformName() {
		return Uniforms.view;
	}
	
	@Override
	protected String getProjectionMatrixUniformName() {
		return Uniforms.projection;
	}
	
	@Override
	protected String getTransformMatrixUniformName() {
		return Uniforms.transform;
	}
	
	@Override
	public int getVertexAttributeLocation() {
		return 0;
	}
	
	public int getTexelAttributeLocation() {
		return 1;
	}
	
	public void setGrassTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.grassTexture), textureUnit));
	}

	public void setSandTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.sandTexture), textureUnit));
	}
	
	public void setClippingPlaneUniform(GL4 gl, Vector4f clippingPlane) {
		super.setUniform(gl, new Uniform4f(super.getUniformLocation(Uniforms.clippingPlane0), clippingPlane));
	}
}

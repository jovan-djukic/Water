package water.basicWater.waterTile.waterTileShaderProgram;

import base.glsl.Shader;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.glsl.uniformSetter.Uniform1f;
import base.glsl.uniformSetter.Uniform1i;
import base.glsl.uniformSetter.Uniform3f;
import base.objects.renderer.scene.SceneShaderProgram;
import com.jogamp.opengl.GL4;
import org.joml.Vector3f;

public class WaterTileShaderProgram extends SceneShaderProgram {
	private static class Constants {
		public static final String	shaderProgramName		= "waterTileShaderProgram";
		public static final String	vertexShaderName		= "waterTileVertexShader";
		public static final String	fragmentShaderName		= "waterTileFragmentShader";
		public static final String	vertexShaderSource		= "vertexShader.glsl";
		public static final String	fragmentShaderSource	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String projection         = "projection";
		public static final String view               = "view";
		public static final String transform          = "transform";
		public static final String reflectionTexture  = "reflectionTexture";
		public static final String refractionTexture  = "refractionTexture";
		public static final String scaleX             = "scaleX";
		public static final String scaleY             = "scaleY";
		public static final String dudvTexture        = "dudvTexture";
		public static final String waveStrength       = "waveStrength";
		public static final String moveFactor         = "moveFactor";
		public static final String distortionStrength = "distortionStrength";
		public static final String cameraPosition     = "cameraPosition";
		public static final String waterReflectivity  = "waterReflectivity";
		public static final String uniforms[]         = {
				Uniforms.projection,
				Uniforms.view,
				Uniforms.transform,
				Uniforms.reflectionTexture,
				Uniforms.refractionTexture,
				Uniforms.scaleX,
				Uniforms.scaleY,
				Uniforms.dudvTexture,
				Uniforms.waveStrength,
				Uniforms.moveFactor,
				Uniforms.distortionStrength,
				Uniforms.cameraPosition,
				Uniforms.waterReflectivity
		};
	}
	
	public WaterTileShaderProgram() {
		super(
				Constants.shaderProgramName,
				Uniforms.uniforms,
				new VertexShader(
						Constants.vertexShaderName,
						Shader.getShaderSource(WaterTileShaderProgram.class, Constants.vertexShaderSource)
				),
				new FragmentShader(
						Constants.fragmentShaderName,
						Shader.getShaderSource(WaterTileShaderProgram.class, Constants.fragmentShaderSource)
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
	
	public void setReflectionTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.reflectionTexture), textureUnit));
	}
	
	public void setRefractionTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.refractionTexture), textureUnit));
	}
	
	public void setScaleXUniform(GL4 gl, float scaleX) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.scaleX), scaleX));
	}
	
	public void setScaleYUniform(GL4 gl, float scaleY) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.scaleY), scaleY));
	}
	
	public void setDudvTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.dudvTexture), textureUnit));
	}
	
	public void setWaveStrengthUniform(GL4 gl, float waveStrength) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.waveStrength), waveStrength));
	}
	
	public void setMoveFactorUniform(GL4 gl, float moveFactor) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.moveFactor), moveFactor));
	}
	
	public void setDistortionStrengthUniform(GL4 gl, float distortionStrength) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.distortionStrength), distortionStrength));
	}
	
	public void setCameraPositionUniform(GL4 gl, Vector3f cameraPosition) {
		super.setUniform(gl, new Uniform3f(super.getUniformLocation(Uniforms.cameraPosition), cameraPosition));
	}
	
	public void setWaterReflectivityUniform(GL4 gl, float waterReflectivity) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.waterReflectivity), waterReflectivity));
	}
}

package water.basicWater.waterTile.waterTileShaderProgram;

import base.glsl.Shader;
import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.VertexShader;
import base.glsl.uniformSetter.Uniform1f;
import base.glsl.uniformSetter.Uniform1i;
import base.glsl.uniformSetter.Uniform3f;
import base.glsl.uniformSetter.Uniform4f;
import base.objects.renderer.scene.SceneShaderProgram;
import com.jogamp.opengl.GL4;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class WaterTileShaderProgram extends SceneShaderProgram {
	private static class Constants {
		public static final String	shaderProgramName		= "waterTileShaderProgram";
		public static final String	vertexShaderName		= "waterTileVertexShader";
		public static final String	fragmentShaderName		= "waterTileFragmentShader";
		public static final String	vertexShaderSource		= "vertexShader.glsl";
		public static final String	fragmentShaderSource	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String projection               = "projection";
		public static final String view                     = "view";
		public static final String transform                = "transform";
		public static final String reflectionTexture        = "reflectionTexture";
		public static final String refractionTexture        = "refractionTexture";
		public static final String scaleX                   = "scaleX";
		public static final String scaleY                   = "scaleY";
		public static final String dudvTexture              = "dudvTexture";
		public static final String waveStrength             = "waveStrength";
		public static final String moveFactor               = "moveFactor";
		public static final String distortionStrength       = "distortionStrength";
		public static final String cameraPosition           = "cameraPosition";
		public static final String waterReflectivity        = "waterReflectivity";
		public static final String normalMapTexture         = "normalMapTexture";
		public static final String lightPosition            = "lightPosition";
		public static final String lightColor               = "lightColor";
		public static final String shineDamper              = "shineDamper";
		public static final String lightReflectivity        = "lightReflectivity";
		public static final String normalEqualizationFactor = "normalEqualizationFactor";
		public static final String uniforms[]               = {
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
				Uniforms.waterReflectivity,
				Uniforms.normalMapTexture,
				Uniforms.lightPosition,
				Uniforms.lightColor,
				Uniforms.shineDamper,
				Uniforms.lightReflectivity,
				Uniforms.normalEqualizationFactor
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
	
	public void setNormalMapTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.normalMapTexture), textureUnit));
	}
	
	public void setLightPositionUniform(GL4 gl, Vector3f lightPositon) {
		super.setUniform(gl, new Uniform3f(super.getUniformLocation(Uniforms.lightPosition), lightPositon));
	}
	
	public void setLightColorUniform(GL4 gl, Vector4f lightColor) {
		super.setUniform(gl, new Uniform4f(super.getUniformLocation(Uniforms.lightColor), lightColor));
	}
	
	public void setShineDamperUniform(GL4 gl, float shineDamper) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.shineDamper), shineDamper));
	}
	
	public void setLightReflectivityUniform(GL4 gl, float lightReflectivity) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.lightReflectivity), lightReflectivity));
	}
	
	public void setNormalEqualizationFactorUniform(GL4 gl, float normalEqualizationFactor) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.normalEqualizationFactor), normalEqualizationFactor));
	}
}

package water.basicWater.waterTerrain.causticsWaterTerrain.causticsWaterTerrainShaderProgram;

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
import water.basicWater.rendererDecorators.ClippingPlaneRenderDecorator;
import water.basicWater.rendererDecorators.DistortionRenderDecorator;
import water.basicWater.rendererDecorators.LightRenderDecorator;
import water.basicWater.rendererDecorators.NormalMapRenderDecorator;

public class CausticsWaterTerrainShaderProgram extends SceneShaderProgram
	implements DistortionRenderDecorator.IDistortionShaderProgram,
		NormalMapRenderDecorator.INormalMapShaderProgram,
		LightRenderDecorator.ILightShaderProgram,
		ClippingPlaneRenderDecorator.IClippingPlaneShaderProgram {
	
	private static class Constants {
		public static final String	shaderProgramName		= "causticsTerrainShaderProgram";
		public static final String	vertexShaderName		= "causticsTerrainVertexShader";
		public static final String	fragmentShaderName		= "causticsTerrainFragmentShader";
		public static final String	vertexShaderSource		= "vertexShader.glsl";
		public static final String	fragmentShaderSource	= "fragmentShader.glsl";
	}
	
	public static class Uniforms {
		public static final String projection               = "projection";
		public static final String view                     = "view";
		public static final String transform                = "transform";
		public static final String terrainTexture           = "terrainTexture";
		public static final String clippingPlane0           = "clippingPlane0";
		public static final String waterHeight              = "waterHeight";
		public static final String dudvTexture              = "dudvTexture";
		public static final String normalMapTexture         = "normalMapTexture";
		public static final String moveFactor               = "moveFactor";
		public static final String distortionStrength       = "distortionStrength";
		public static final String shineDamper              = "shineDamper";
		public static final String lightReflectivity        = "lightReflectivity";
		public static final String normalEqualizationFactor = "normalEqualizationFactor";
		public static final String lightPosition            = "lightPosition";
		public static final String lightColor               = "lightColor";
		public static final String uniforms[]               = {
				Uniforms.projection,
				Uniforms.view,
				Uniforms.transform,
				Uniforms.terrainTexture,
				Uniforms.clippingPlane0,
				Uniforms.waterHeight,
				Uniforms.dudvTexture,
				Uniforms.normalMapTexture,
				Uniforms.moveFactor,
				Uniforms.distortionStrength,
				Uniforms.shineDamper,
				Uniforms.lightReflectivity,
				Uniforms.normalEqualizationFactor,
				Uniforms.lightPosition,
				Uniforms.lightColor,
		};
	}
	
	public CausticsWaterTerrainShaderProgram() {
		super(
				Constants.shaderProgramName,
				Uniforms.uniforms,
				new VertexShader(
						Constants.vertexShaderName,
						Shader.getShaderSource(CausticsWaterTerrainShaderProgram.class, Constants.vertexShaderSource)
				),
				new FragmentShader(
						Constants.fragmentShaderName,
						Shader.getShaderSource(CausticsWaterTerrainShaderProgram.class, Constants.fragmentShaderSource)
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
	
	public void setTerrainTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.terrainTexture), textureUnit));
	}
	
	public void setClippingPlaneUniform(GL4 gl, Vector4f clippingPlane) {
		super.setUniform(gl, new Uniform4f(super.getUniformLocation(Uniforms.clippingPlane0), clippingPlane));
	}
	
	@Override
	public void setDudvTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.dudvTexture), textureUnit));
	}
	
	@Override
	public void setMoveFactorUniform(GL4 gl, float moveFactor) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.moveFactor), moveFactor));
	}
	
	@Override
	public void setDistortionStrengthUniform(GL4 gl, float distortionStrength) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.distortionStrength), distortionStrength));
	}
	
	@Override
	public void setLightPositionUniform(GL4 gl, Vector3f lightPosition) {
		super.setUniform(gl, new Uniform3f(super.getUniformLocation(Uniforms.lightPosition), lightPosition));
	}
	
	@Override
	public void setLightColorUniform(GL4 gl, Vector4f lightColor) {
		super.setUniform(gl, new Uniform4f(super.getUniformLocation(Uniforms.lightColor), lightColor));
	}
	
	@Override
	public void setNormalMapTextureUniform(GL4 gl, int textureUnit) {
		super.setUniform(gl, new Uniform1i(super.getUniformLocation(Uniforms.normalMapTexture), textureUnit));
	}
	
	@Override
	public void setNormalEqualizationFactorUniform(GL4 gl, float normalEqualizationFactor) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.normalEqualizationFactor), normalEqualizationFactor));
	}
	
	public void setWaterHeightUniform(GL4 gl, float waterHeight) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.waterHeight), waterHeight));
	}
	
	public void setShineDamperUniform(GL4 gl, float shineDamper) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.shineDamper), shineDamper));
	}
	
	public void setLightReflectivityUniform(GL4 gl, float lightReflectivity) {
		super.setUniform(gl, new Uniform1f(super.getUniformLocation(Uniforms.lightReflectivity), lightReflectivity));
	}
}

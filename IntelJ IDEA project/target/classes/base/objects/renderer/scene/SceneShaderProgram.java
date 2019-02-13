package base.objects.renderer.scene;

import base.glsl.Shader;
import base.glsl.ShaderProgram;
import base.glsl.uniformSetter.UniformMatrix4fv;
import com.jogamp.opengl.GL4;

public abstract class SceneShaderProgram extends ShaderProgram {
	
	public SceneShaderProgram(String name, String[] uniforms, Shader... shaders) {
		super(name, uniforms, shaders);
	}
	
	protected abstract String getViewMatrixUniformName();
	
	public void setViewMatrixUniform(GL4 gl, float viewMatrix[]) {
		super.setUniform(gl, new UniformMatrix4fv(super.getUniformLocation(this.getViewMatrixUniformName()), 1, false, viewMatrix, 0));
	}
	
	protected abstract String getProjectionMatrixUniformName();
	
	public void setProjectionMatrixUniform(GL4 gl, float projectionMatrix[]) {
		super.setUniform(gl, new UniformMatrix4fv(super.getUniformLocation(this.getProjectionMatrixUniformName()), 1, false, projectionMatrix, 0));
	}
	
	protected abstract String getTransformMatrixUniformName();
	
	public void setTransformMatrixUniformName(GL4 gl, float transformMatrix[]) {
		super.setUniform(gl, new UniformMatrix4fv(super.getUniformLocation(this.getTransformMatrixUniformName()), 1, false, transformMatrix, 0));
	}
	
	public abstract int getVertexAttributeLocation();
}

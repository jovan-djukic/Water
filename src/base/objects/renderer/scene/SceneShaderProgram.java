package base.objects.renderer.scene;

import base.glsl.Shader;
import base.glsl.ShaderProgram;
import base.objects.renderer.scene.camera.Camera;
import org.joml.Matrix4f;

import com.jogamp.opengl.GL4;

public abstract class SceneShaderProgram extends ShaderProgram {
	
	public SceneShaderProgram(String name, String[] uniforms, Shader... shaders) {
		super(name, uniforms, shaders);
	}
	
	public SceneShaderProgram(String name, Shader... shaders) {
		super(name, shaders);
	}
	
	public abstract int getViewMatrixUniformPosition();
	
	public abstract int getProjectionMatrixUniformPosition();
}

package base.objects;

import base.glsl.ShaderProgram;
import com.jogamp.opengl.GL4;

public abstract class GraphicsObject extends OpenGLObject implements IRenderable {
	private static class Constants {
		public static final String graphicsObject = "graphicsObject-";
		public static final String renderTag      = Constants.graphicsObject + "render";
	}
	
	protected ShaderProgram shaderProgram;
	
	protected GraphicsObject(String name, ShaderProgram shaderProgram) {
		super(name);
		this.shaderProgram = shaderProgram;
	}
	
	public void init(GL4 gl) {
		if (this.shaderProgram != null) {
			System.out.println(this.shaderProgram.build(gl));
		}
	}
	
	protected void preRender(GL4 gl) {
		if (this.shaderProgram != null) {
			this.shaderProgram.activate(gl);
		}
	}
	protected void postRender(GL4 gl) {
		if (this.shaderProgram != null) {
			this.shaderProgram.deactivate(gl);
		}
	}
	
	protected abstract void drawCommand(GL4 gl);
	
	public void render(GL4 gl) {
		this.preRender(gl);
		this.drawCommand(gl);
		this.postRender(gl);
		this.checkForErrors(gl, Constants.renderTag);
	}
	
	public void destroy(GL4 gl) {
		if (this.shaderProgram != null) {
			this.shaderProgram.delete(gl);
		}
	}
	
	public void setShaderProgram(ShaderProgram shaderProgram) {
		this.shaderProgram = shaderProgram;
	}
	
	public ShaderProgram getShaderProgram() {
		return shaderProgram;
	}
}

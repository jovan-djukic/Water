package base.objects.renderer;;

import base.Utilities;
import base.glsl.ShaderProgram;
import base.objects.OpenGLObject;
import base.objects.model.Model;
import com.jogamp.opengl.GL4;

import java.util.ArrayList;
import java.util.List;


public abstract class Renderer extends RendererBase {
	private static class Constants {
		public static final String renderer      = "renderer-";
		public static final String preRenderTag  = Constants.renderer + "preRender";
		public static final String postRenderTag = Constants.renderer + "postRender";
	}

	private ShaderProgram shaderProgram;
	private List<Model>   modelList;
	
	protected Renderer(String name, ShaderProgram shaderProgram, Model models[], OpenGLObject ...openGLObjects) {
		super(name, Utilities.getInstance().concatenate(OpenGLObject.class, Utilities.getInstance().concatenate(OpenGLObject.class, shaderProgram, openGLObjects), models));
		
		this.shaderProgram = shaderProgram;
		
		this.modelList = new ArrayList<>();
		for (int i = 0; i < models.length; i++) {
			this.modelList.add(models[i]);
		}
	}
	
	@Override
	protected void preRender(GL4 gl) {
		if (this.shaderProgram != null) {
			this.shaderProgram.activate(gl);
		}
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		if (this.shaderProgram != null) {
			this.shaderProgram.deactivate(gl);
		}
		
		this.checkForErrors(gl, Constants.postRenderTag);
	}
	
	@Override
	protected void renderImplementation(GL4 gl) {
		for (Model model : this.modelList) {
			model.render(gl);
		}
	}
}

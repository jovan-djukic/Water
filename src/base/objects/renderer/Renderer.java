package base.objects.renderer;

import base.glsl.ShaderProgram;
import base.objects.CompositeOpenGLObject;
import base.objects.OpenGLObject;
import base.objects.model.ModelBase;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class Renderer extends RendererBase {
	private static class Constants {
		public static final String empty                   = "";
		public static final String renderer                = "graphicsObject-";
		public static final String preRenderTag            = Constants.renderer + "preRender";
		public static final String postRenderTag           = Constants.renderer + "postRender";
		public static final String renderImplementationTag = Constants.renderer + "renderImplementation";
	}
	

	private ShaderProgram shaderProgram;
	private ArrayList<ModelBase> modelBases;
	private Matrix4f transform;
	
	protected Renderer(String name, ShaderProgram shaderProgram, ModelBase modelBases[], OpenGLObject ...openGLObjects) {
		super(name, shaderProgram, new CompositeOpenGLObject(Constants.empty, modelBases), new CompositeOpenGLObject(Constants.empty, openGLObjects));
		
		this.shaderProgram = shaderProgram;
		this.modelBases = new ArrayList<>();
		for (int i = 0; i < modelBases.length; i++) {
			this.modelBases.add(modelBases[i]);
		}
		
		this.transform = new Matrix4f();
		this.transform.identity();
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
	public void renderImplementation(GL4 gl) {
		for (ModelBase modelBase : this.modelBases) {
			modelBase.render(gl, this.transform, this.shaderProgram.getTransformUniformLocation());
		}
		this.checkForErrors(gl, Constants.renderImplementationTag);
	}
	
	@Override
	public void update() {
	
	}
}

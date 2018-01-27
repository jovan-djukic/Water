package base.views;

import base.objects.IRenderable;
import base.objects.IUpdateable;
import base.objects.OpenGLObject;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;

import java.util.ArrayList;

public abstract class GLView implements GLEventListener {
	protected static class Constants {
		public static final String  windowTitle    = "GLView";
		public static final int     alphaBits      = 8;
		public static final int     depthBits      = 24;
		public static final boolean doubleBuffered = true;
		public static final int     fps            = 60;
		public static final int     windowWidth    = 800;
		public static final int     windowHeight   = 800;
		public static final String  shaderProgram  = "shaderProgram";
	}
	
	protected static class FPSAnimatorStopper implements Runnable {
		private FPSAnimator fpsAnimator;
		
		public FPSAnimatorStopper(FPSAnimator fpsAnimator) {
			this.fpsAnimator = fpsAnimator;
		}
		
		@Override
		public void run() {
			if (this.fpsAnimator != null) {
				this.fpsAnimator.stop();
			}
		}
	}
	
	protected GLWindow                glWindow;
	protected FPSAnimator             fpsAnimator;
	protected ArrayList<OpenGLObject> openGLObjects;
	protected ArrayList<IRenderable>  renderables;
	protected ArrayList<IUpdateable>  updateables;
	
	
	protected abstract void fill(ArrayList<OpenGLObject> openGLObjects, ArrayList<IRenderable> renderables, ArrayList<IUpdateable> updateables);
	
	protected abstract void setListeners(GLWindow window);
	
	protected GLView(String windowTitle, int alphaBits, int depthBits, boolean doubleBuffer, int fps, int windowWidth, int windowHeight) {
		
		this.openGLObjects = new ArrayList<OpenGLObject>();
		this.renderables = new ArrayList<IRenderable>();
		this.updateables = new ArrayList<IUpdateable>();
		
		this.fill(this.openGLObjects, this.renderables, this.updateables);
		
		GLProfile glProfile = GLProfile.getMaximum(true);
		System.out.println(glProfile.toString());
		System.out.println(glProfile.getGLImplBaseClassName());
		System.out.println(glProfile.getImplName());
		System.out.println(glProfile.getName());
		System.out.println(glProfile.hasGLSL());
		
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		glCapabilities.setAlphaBits(alphaBits);
		glCapabilities.setDepthBits(depthBits);
		glCapabilities.setDoubleBuffered(true);
		
		this.glWindow = GLWindow.create(glCapabilities);
		this.fpsAnimator = new FPSAnimator(this.glWindow, fps, true);
		
		this.setListeners(this.glWindow);
		this.glWindow.addGLEventListener(this);
		this.glWindow.setWindowDestroyNotifyAction(new FPSAnimatorStopper(this.fpsAnimator));
		this.glWindow.setSize(windowWidth, windowHeight);
		this.glWindow.setTitle(windowTitle);
		this.glWindow.setVisible(true);
		
		this.fpsAnimator.start();
	}
	
	protected GLView() {
		this(Constants.windowTitle, Constants.alphaBits, Constants.depthBits, Constants.doubleBuffered, Constants.fps, Constants.windowWidth, Constants.windowHeight);
	}
	
	protected void render(GLAutoDrawable drawable) {
		GL4 gl = drawable.getGL().getGL4();
		
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_STENCIL_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
		
		for (IRenderable renderable : this.renderables) {
			renderable.render(gl);
		}
	}
	
	protected void update() {
		for (IUpdateable updateable : this.updateables)	{
			updateable.update();
		}
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		GL4 gl = drawable.getGL().getGL4bc();
		
		for (OpenGLObject openGLObject : this.openGLObjects) {
			openGLObject.destroy(gl);
		}
	}
	
	@Override
	public void display(GLAutoDrawable glAutoDrawable) {
		render(glAutoDrawable);
		update();
	}
	
	protected abstract void onReshape(int width, int height);
	
	@Override
	public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
		GL4 gl4 = glAutoDrawable.getGL().getGL4();
		
		gl4.glViewport(x, y, width, height);
		
		this.onReshape(width, height);
	}
	
	@Override
	public void init(GLAutoDrawable glAutoDrawable) {
		GL4 gl = glAutoDrawable.getGL().getGL4();
		
		for (OpenGLObject openGLObject : this.openGLObjects) {
			openGLObject.init(gl);
		}
	}
}

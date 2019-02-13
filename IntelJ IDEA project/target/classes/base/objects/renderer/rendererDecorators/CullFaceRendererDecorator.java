package base.objects.renderer.rendererDecorators;

import base.objects.OpenGLObject;
import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.RendererBase;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public class CullFaceRendererDecorator extends CompositeRenderer {
	private static class Constants {
		public static final String cullFaceRendererDecorator = "cullFaceRendererDecorator";
		public static final String preRenderTag              = Constants.cullFaceRendererDecorator + "preRender";
		public static final String postRenderTag             = Constants.cullFaceRendererDecorator + "postRender";
	}
	
	private int face;
	private boolean isCullFaceEnabled;
	private IntBuffer oldCullFace;
	
	public CullFaceRendererDecorator(String name, RendererBase[] rendererBases, int face, OpenGLObject... openGLObjects) {
		super(name, rendererBases, openGLObjects);
		
		this.face = face;
		this.oldCullFace = IntBuffer.allocate(1);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		this.isCullFaceEnabled = gl.glIsEnabled(GL4.GL_CULL_FACE);
		
		if (this.isCullFaceEnabled) {
			gl.glGetIntegerv(GL4.GL_CULL_FACE, this.oldCullFace);
			this.oldCullFace.rewind();
		} else {
			gl.glEnable(GL4.GL_CULL_FACE);
		}
		
		gl.glCullFace(this.face);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		
		if (this.isCullFaceEnabled) {
			gl.glCullFace(this.oldCullFace.get(0));
			this.oldCullFace.rewind();
		} else {
			gl.glDisable(GL4.GL_CULL_FACE);
		}
	
		this.checkForErrors(gl, Constants.postRenderTag);
	}
}

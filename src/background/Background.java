package background;

import background.shaderProgram.BackgroundShaderProgram;
import base.objects.GraphicsObject;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

public class Background extends GraphicsObject {
	
	private static class Constants {
		public static final String background     = "background-";
		public static final String initTag        = Constants.background + "init";
		public static final String preRenderTag   = Constants.background + "preRender";
		public static final String drawCommandTag = Constants.background + "drawCommand";
		public static final String postRenderTag  = Constants.background + "postRender";
		public static final String destroyTag     = Constants.background + "destroy";
	}
	
	protected Texture backgroundTexture;
	protected Class scope;
	protected String fileName, fileSuffix;
	protected int vertexArrayObjectID, backgroundLocation;
	protected boolean isDepthTestEnabled;
	
	public Background(String name, Class scope, String fileName, String fileSuffix) {
		super(name, new BackgroundShaderProgram());
		
		this.scope = scope;
		this.fileName = fileName;
		this.fileSuffix = fileSuffix;
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenVertexArrays(1, intBuffer);
		this.vertexArrayObjectID = intBuffer.get(0);
		
		try (InputStream inputStream = this.scope.getResourceAsStream(this.fileName)) {
			this.backgroundTexture = TextureIO.newTexture(inputStream, false, this.fileSuffix);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		this.backgroundLocation = gl.glGetUniformLocation(super.shaderProgram.getProgramID(), BackgroundShaderProgram.Uniforms.background);
		
		this.checkForErrors(gl, Constants.initTag);
	}
	
	@Override
	public void destroy(GL4 gl) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		intBuffer.put(this.vertexArrayObjectID);
		intBuffer.rewind();
		gl.glDeleteVertexArrays(1, intBuffer);
		
		this.backgroundTexture.destroy(gl);
		
		this.checkForErrors(gl, Constants.destroyTag);
		super.destroy(gl);
	}
	
	@Override
	protected void preRender(GL4 gl) {
		super.preRender(gl);
		
		gl.glBindVertexArray(this.vertexArrayObjectID);
		
		gl.glActiveTexture(GL4.GL_TEXTURE0);
		this.backgroundTexture.bind(gl);
		
		gl.glUniform1i(this.backgroundLocation, 0);
		
		this.isDepthTestEnabled = gl.glIsEnabled(GL4.GL_DEPTH_TEST);
		gl.glDisable(GL4.GL_DEPTH_TEST);
		
		this.checkForErrors(gl, Constants.preRenderTag);
	}
	
	@Override
	protected void drawCommand(GL4 gl) {
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
		
		this.checkForErrors(gl, Constants.drawCommandTag);
	}
	
	@Override
	protected void postRender(GL4 gl) {
		gl.glBindVertexArray(0);
		
		if (this.isDepthTestEnabled == true) {
			gl.glEnable(GL4.GL_DEPTH_TEST);
		}
		
		this.checkForErrors(gl, Constants.postRenderTag);
		
		super.postRender(gl);
	}
}

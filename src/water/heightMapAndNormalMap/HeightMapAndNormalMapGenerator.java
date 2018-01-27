package water.heightMapAndNormalMap;

import base.glsl.ShaderProgram;
import base.objects.IRenderable;
import base.objects.frameBuffer.FrameBuffer;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public abstract class HeightMapAndNormalMapGenerator extends FrameBuffer implements IRenderable {
	protected static class Constants {
		public static final int    xIndex                         = 0;
		public static final int    yIndex                         = 1;
		public static final int    widthIndex                     = 2;
		public static final int    heightIndex                    = 3;
		public static final String heightMapAndNormalMapGenerator = "heightMapAndNormalMapGenerator-";
		public static final String renderTag                      = Constants.heightMapAndNormalMapGenerator + "render";
	}
	
	protected int width, height;
	private IntBuffer oldViewportBuffer, oldPolygonMode;
	private boolean isDepthTestEnabled, isBlendEnabled;
	private ShaderProgram heightMapShaderProgram, normalMapShaderProgram;
	
	public HeightMapAndNormalMapGenerator(String name, ShaderProgram heightMapShaderProgram, ShaderProgram normalMapShaderProgam, int width, int height) {
		super(name);
		this.width = width;
		this.height = height;
		this.heightMapShaderProgram = heightMapShaderProgram;
		this.normalMapShaderProgram = normalMapShaderProgam;
		this.oldViewportBuffer = IntBuffer.allocate(4);
		this.oldPolygonMode = IntBuffer.allocate(1);
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		if (this.heightMapShaderProgram != null) {
			System.out.println(this.heightMapShaderProgram.build(gl));
		}
		
		if (this.normalMapShaderProgram != null) {
			System.out.println(this.normalMapShaderProgram.build(gl));
		}
	}
	
	protected abstract void setupHeightMapUniforms(GL4 gl);
	
	protected abstract void setupHeightMapDrawBuffers(GL4 gl);
	
	protected abstract void setupNormalMapUniforms(GL4 gl);
	
	protected abstract void setupNormalMapDrawBuffers(GL4 gl);
	
	protected void saveAttributes(GL4 gl) {
		gl.glGetIntegerv(GL4.GL_VIEWPORT, this.oldViewportBuffer);
		gl.glGetIntegerv(GL4.GL_POLYGON_MODE, this.oldPolygonMode);
		this.isDepthTestEnabled = gl.glIsEnabled(GL4.GL_DEPTH_TEST);
		this.isBlendEnabled = gl.glIsEnabled(GL4.GL_BLEND);
		
		gl.glViewport(0, 0, this.width, this.height);
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_FILL);
		gl.glDisable(GL4.GL_DEPTH_TEST);
		gl.glDisable(GL4.GL_BLEND);
	}
	
	protected void restoreAttributes(GL4 gl) {
		if (this.isDepthTestEnabled == true) {
			gl.glEnable(GL4.GL_DEPTH_TEST);
		}
		
		if (this.isBlendEnabled == true) {
			gl.glEnable(GL4.GL_BLEND);
		}
		
		gl.glViewport(this.oldViewportBuffer.get(Constants.xIndex), this.oldViewportBuffer.get(Constants.yIndex), this.oldViewportBuffer.get(Constants.widthIndex), this.oldViewportBuffer.get(Constants.heightIndex));
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, this.oldPolygonMode.get(0));
		
		
		this.oldViewportBuffer.rewind();
		this.oldPolygonMode.rewind();
	}
	
	protected void clearHeightMapBuffersDrawBuffers(GL4 gl) {
		gl.glClearColor(0, 0,0 , 0);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
	}
	
	protected void clearNormalMapBuffersDrawBuffers(GL4 gl) {
		gl.glClearColor(0, 0,0 , 0);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
	}
	
	protected void drawHeightMap(GL4 gl) {
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	protected void drawNormalMap(GL4 gl) {
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	@Override
	public void render(GL4 gl) {
		super.bind(gl);
		
		this.saveAttributes(gl);
		
		if (this.heightMapShaderProgram != null) {
			this.heightMapShaderProgram.activate(gl);
			this.setupHeightMapUniforms(gl);
			this.setupHeightMapDrawBuffers(gl);
			this.clearHeightMapBuffersDrawBuffers(gl);
			this.drawHeightMap(gl);
		}
		
		if (this.normalMapShaderProgram != null) {
			this.normalMapShaderProgram.activate(gl);
			this.setupNormalMapUniforms(gl);
			this.setupNormalMapDrawBuffers(gl);
			this.clearNormalMapBuffersDrawBuffers(gl);
			this.drawNormalMap(gl);
		}
		
		this.restoreAttributes(gl);
		
		super.unbind(gl);
		
		this.checkForErrors(gl, Constants.renderTag);
	}
	
	public abstract int getHeightMapTextureID();
	
	public abstract int getNormalTextureID();
}

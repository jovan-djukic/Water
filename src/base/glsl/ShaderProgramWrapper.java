package base.glsl;

import java.util.ArrayList;

import com.jogamp.opengl.GL4;

import base.glsl.shaders.FragmentShader;
import base.glsl.shaders.GeometryShader;
import base.glsl.shaders.VertexShader;

public abstract class ShaderProgramWrapper extends ShaderProgram {
	
	private VertexShader	vertexShader;
	private FragmentShader	fragmentShader;
	private GeometryShader	geometryShader;
	
	public ShaderProgramWrapper(String name) {
		super(name);
	}
	
	// vertex and fragment shaders are necessary
	protected abstract String getVertexShaderSource() throws Exception;
	
	protected abstract String getVertexShaderName() throws Exception;
	
	protected abstract String getFragmentShaderSource() throws Exception;
	
	protected abstract String getFragmentShaderName() throws Exception;
	
	// geometry shader is not necessary
	protected boolean hasGeometryShader() {
		return false;
	}
	
	protected String getGeometryShaderSource() throws Exception {
		return null;
	}
	
	protected String getGeometryShaderName() throws Exception {
		return null;
	}
	
	protected ArrayList<String> getUniforms() {
		return new ArrayList<String>();
	}
	
	@Override
	public String build(GL4 gl) {
		try {
			String vertexShaderSource = this.getVertexShaderSource();
			String vertexShaderName = this.getVertexShaderName();
			this.vertexShader = new VertexShader(vertexShaderName);
			this.vertexShader.setShaderSource(vertexShaderSource);
			super.addShader(this.vertexShader);
			
			String fragmentShaderSource = this.getFragmentShaderSource();
			String fragmentShaderName = this.getFragmentShaderName();
			this.fragmentShader = new FragmentShader(fragmentShaderName);
			this.fragmentShader.setShaderSource(fragmentShaderSource);
			super.addShader(this.fragmentShader);
			
			if (this.hasGeometryShader()) {
				String geometryShaderSource = this.getVertexShaderSource();
				String geometryShaderName = this.getVertexShaderName();
				this.geometryShader = new GeometryShader(geometryShaderName);
				this.geometryShader.setShaderSource(geometryShaderSource);
				super.addShader(this.geometryShader);
			}
			
			return super.build(gl, this.getUniforms());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}

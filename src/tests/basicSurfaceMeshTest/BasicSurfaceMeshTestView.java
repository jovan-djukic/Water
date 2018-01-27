package tests.basicSurfaceMeshTest;

import java.util.ArrayList;

import base.objects.scene.Scene;
import org.joml.Matrix4f;

import com.jogamp.opengl.GL4;

import base.objects.scene.camera.Camera;
import basic.BasicCamera;
import basic.BasicView;
import water.surfaceMesh.SurfaceMesh;
import water.surfaceMesh.SurfaceMeshShaderProgram;

public class BasicSurfaceMeshTestView extends BasicView {
	private static class Constants {
		public static final String	surfaceMeshName	= "BasicSurfaceMesh";
		public static final float	x				= -1;
		public static final float	z				= -1;
		public static final float	width			= 2;
		public static final float	height			= 2;
		public static final int		rows			= 20;
		public static final int		columns			= 20;
	}
	
	private static class BasicSurfaceMeshShaderProgram extends SurfaceMeshShaderProgram {
		
		private static class Constants {
			public static final String	shaderProgramName		= "BasicSurfaceMeshShaderProgram";
			public static final String	vertexShaderName		= "BasicSurfaceMeshVertexShader";
			public static final String	fragmentShaderName		= "BasicSurfaceMeshFragmentShader";
			public static final String	vertexShaderSource		= "vertexShader.glsl";
			public static final String	fragmentShaderSource	= "fragmentShader.glsl";
		}
		
		public static class Uniforms {
			public static final String transform = "transform";
		}
		
		public BasicSurfaceMeshShaderProgram(Camera camera) {
			super(Constants.shaderProgramName, camera);
		}
		
		@Override
		public int getVertexAttributePosition() {
			return 0;
		}
		
		@Override
		public int getTextureCoordinateAttributePosition() {
			return 1;
		}
		
		@Override
		protected String getVertexShaderSource() throws Exception {
			return new String(this.getClass().getResourceAsStream(Constants.vertexShaderSource).readAllBytes());
		}
		
		@Override
		protected String getVertexShaderName() throws Exception {
			return Constants.vertexShaderName;
		}
		
		@Override
		protected String getFragmentShaderSource() throws Exception {
			return new String(this.getClass().getResourceAsStream(Constants.fragmentShaderSource).readAllBytes());
		}
		
		@Override
		protected String getFragmentShaderName() throws Exception {
			return Constants.fragmentShaderName;
		}
		
		@Override
		public void applyTransform(GL4 gl, Matrix4f transform) {
			super.applyTransform(gl, transform);
			int transformLocation = super.getUniformLocation(Uniforms.transform);
			
			gl.glUniformMatrix4fv(transformLocation, 1, false, super.transformArray, 0);
		}
		
		@Override
		protected ArrayList<String> getUniforms() {
			ArrayList<String> uniforms = super.getUniforms();
			
			uniforms.add(Uniforms.transform);
			
			return uniforms;
		}
	}
	
	@Override
	protected Scene getScene(BasicCamera basicCamera) {
		Scene scene = super.getScene(basicCamera);
		BasicSurfaceMeshShaderProgram shaderProgram = new BasicSurfaceMeshShaderProgram(basicCamera);
		SurfaceMesh surfaceMesh = new SurfaceMesh(Constants.surfaceMeshName, shaderProgram, Constants.x, Constants.z, Constants.width, Constants.height, Constants.rows, Constants.columns);
		
		scene.add(surfaceMesh);
		
		return scene;
	}
	
}

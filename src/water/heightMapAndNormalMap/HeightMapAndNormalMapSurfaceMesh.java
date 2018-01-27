package water.heightMapAndNormalMap;

import org.joml.Matrix4f;

import com.jogamp.opengl.GL4;

import water.surfaceMesh.SurfaceMesh;
import water.surfaceMesh.SurfaceMeshShaderProgram;

public class HeightMapAndNormalMapSurfaceMesh extends SurfaceMesh {
	
	protected HeightMapAndNormalMapGenerator heightMapAndNormalMapGenerator;
	
	public HeightMapAndNormalMapSurfaceMesh(String name, SurfaceMeshShaderProgram shaderProgram, float x, float z, float width, float height, int rows, int columns, HeightMapAndNormalMapGenerator heightMapAndNormalMapGenerator) {
		super(name, shaderProgram, x, z, width, height, rows, columns);
		
		this.heightMapAndNormalMapGenerator = heightMapAndNormalMapGenerator;
	}
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		
		this.heightMapAndNormalMapGenerator.init(gl);
	}
	
	@Override
	public void render(GL4 gl, Matrix4f parentTransform) {
		this.heightMapAndNormalMapGenerator.render(gl);
		super.render(gl, parentTransform);
	}
}

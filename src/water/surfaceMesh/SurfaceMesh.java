package water.surfaceMesh;

import shapes.basicShapes.Mesh;

public class SurfaceMesh extends Mesh {
	
	protected SurfaceMeshShaderProgram	shaderProgram;
	
	public SurfaceMesh(String name, SurfaceMeshShaderProgram shaderProgram, float x, float z, float width, float height, int rows, int columns) {
		super(name, shaderProgram, x, z, width, height, rows, columns);
		
		this.shaderProgram = shaderProgram;
	}
	
	@Override
	public void update() {
		
	}
}

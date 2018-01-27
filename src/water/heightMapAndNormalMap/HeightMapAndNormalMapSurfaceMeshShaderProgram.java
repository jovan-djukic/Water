package water.heightMapAndNormalMap;

import com.jogamp.opengl.GL4;

import base.objects.scene.camera.Camera;
import water.surfaceMesh.SurfaceMeshShaderProgram;

public abstract class HeightMapAndNormalMapSurfaceMeshShaderProgram extends SurfaceMeshShaderProgram {
	
	protected HeightMapAndNormalMapGenerator heightMapAndNormalMapGenerator;
	
	public HeightMapAndNormalMapSurfaceMeshShaderProgram(String name, Camera camera, HeightMapAndNormalMapGenerator heightMapAndNormalMapGenerator) {
		super(name, camera);
		
		this.heightMapAndNormalMapGenerator = heightMapAndNormalMapGenerator;
	}
	
	protected abstract void setupHeightMap(GL4 gl, int heightMapTextureID);
	
	protected abstract void setupNormalMap(GL4 gl, int normalMapTextureID);
	
	@Override
	public void activate(GL4 gl) {
		super.activate(gl);
		
		this.setupHeightMap(gl, this.heightMapAndNormalMapGenerator.getHeightMapTextureID());
		this.setupNormalMap(gl, this.heightMapAndNormalMapGenerator.getNormalTextureID());
	}
}

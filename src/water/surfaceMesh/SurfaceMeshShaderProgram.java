package water.surfaceMesh;

import base.objects.scene.SceneShaderProgram;
import base.objects.scene.camera.Camera;

public abstract class SurfaceMeshShaderProgram extends SceneShaderProgram {
	
	public SurfaceMeshShaderProgram(String name, Camera camera) {
		super(name, camera);
	}
	
	@Override
	public boolean hasTextureCoordinates() {
		return true;
	}
	
	@Override
	public abstract int getTextureCoordinateAttributePosition();
}

package tests.renderTextureTest;

import base.objects.scene.Scene;
import tests.renderTextureTest.renderTextureShaderProgram.RenderTextureShaderProgram;
import tests.renderTextureTest.textureGenerator.TextureGenerator;
import basic.BasicCamera;
import basic.BasicView;
import water.heightMapAndNormalMap.HeightMapAndNormalMapSurfaceMesh;

public class RenderTextureTestView extends BasicView {
	
	private static class Constants {
		public static final String	surfaceMeshName	= "RenderTextureTestSurfaceMesh";
		public static final float	x				= -1;
		public static final float	z				= -1;
		public static final float	width			= 2;
		public static final float	height			= 2;
		public static final int		rows			= 20;
		public static final int		columns			= 20;
	}
	
	@Override
	protected Scene getScene(BasicCamera basicCamera) {
		Scene scene = super.getScene(basicCamera);
		TextureGenerator textureGenerator = new TextureGenerator();
		RenderTextureShaderProgram shaderProgram = new RenderTextureShaderProgram(basicCamera, textureGenerator);
		HeightMapAndNormalMapSurfaceMesh heightMapAndNormalMapSurfaceMesh = new HeightMapAndNormalMapSurfaceMesh(Constants.surfaceMeshName, shaderProgram, Constants.x, Constants.z, Constants.width, Constants.height, Constants.rows, Constants.columns, textureGenerator);
		
		scene.add(heightMapAndNormalMapSurfaceMesh);
		
		return scene;
	}
	
}

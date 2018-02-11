package tests.perlinNoiseTest;

import base.objects.renderer.CompositeRenderer;
import base.objects.renderer.RendererBase;
import base.views.GLView;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import perlinNoise.perlinNoiseTextureGenerator.PerlinNoiseTextureGenerator;

import java.util.ArrayList;

public class PerlinNoiseTestView extends GLView {
	private static class Constants {
		public static final String name                    = "perlinNoiseTestRenderer";
		public static final String perlinNoiseTextureGenerator        = "perlinNoiseTextureGenerator";
		public static final int    numberOfOctaves         = 3;
		public static final float  persistence             = 0.3f;
		public static final int    textureWidth            = 256;
		public static final int    textureHeight           = 256;
		public static final float  scaleX                  = 8.0f / textureWidth;
		public static final float  scaleY                  = 8.0f / textureHeight;
		public static final String perlinNoiseTestBuffer   = Constants.name + "-";
		public static final String renderImplementationTag = Constants.perlinNoiseTestBuffer + "renderImplementationTag";
	}
	@Override
	protected ArrayList<RendererBase> getRenderers() {
		ArrayList<RendererBase> rendererBases =  super.getRenderers();
		
		PerlinNoiseTextureGenerator perlinNoiseTextureGenerator = new PerlinNoiseTextureGenerator(
				Constants.perlinNoiseTextureGenerator,
				Constants.numberOfOctaves,
				Constants.persistence,
				Constants.scaleX,
				Constants.scaleY,
				Constants.textureWidth,
				Constants.textureHeight
		);
		
		PerlinNoiseTextureRenderer perlinNoiseTextureRenderer = new PerlinNoiseTextureRenderer(
				perlinNoiseTextureGenerator.getPerlinNoiseTexture()
		);
		
		rendererBases.add(perlinNoiseTextureGenerator);
		rendererBases.add(perlinNoiseTextureRenderer);
		
		return rendererBases;
	}
	
	@Override
	protected void setListeners(GLWindow window) {
	
	}
	
	@Override
	protected void onReshape(int width, int height) {
	
	}
}

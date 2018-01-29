package perlinNoise.perlinNoiseTextureGenerator;

import base.objects.model.Model;
import com.jogamp.opengl.GL4;

public class PerlinNoiseModel extends Model {
	private static class Constants {
		public static final String name        = "perlinNoiseModel-";
		public static final String perlinNoise = "graphicsObject-";
		public static final String drawCommand = Constants.perlinNoise + "drawCommand";
	}
	
	
	public PerlinNoiseModel() {
		super(Constants.name);
	}
	
	@Override
	public void drawCommand(GL4 gl) {
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
		
		this.checkForErrors(gl, Constants.drawCommand);
	}
}

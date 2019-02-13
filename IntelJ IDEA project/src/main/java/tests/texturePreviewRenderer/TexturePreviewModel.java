package tests.texturePreviewRenderer;

import base.objects.model.Model;
import com.jogamp.opengl.GL4;

public class TexturePreviewModel extends Model {
	private static class Constants {
		public static final String texturePreviewModel = "texturePreviewModel";
		public static final String drawCommandTag      = Constants.texturePreviewModel + "drawCommandTag";
	}
	public TexturePreviewModel(String name) {
		super(name);
	}
	
	@Override
	protected void drawCommand(GL4 gl) {
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
		this.checkForErrors(gl, Constants.drawCommandTag);
	}
}

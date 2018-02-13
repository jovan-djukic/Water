package shapes;

import base.objects.model.loaders.Loader;
import base.objects.model.Model;
import base.objects.model.loaders.TexelsLoader;
import base.objects.model.loaders.VerticesLoader;
import base.objects.renderer.scene.sceneModel.SceneModel;
import com.jogamp.opengl.GL4;
import org.joml.Vector3f;

public class Quad extends SceneModel {
	private static class Constants {
		public final static String quad                 = "quad";
		public final static String vertexPositionLoader = Constants.quad + "vertexPositionLoader";
		public final static String texelsLoader         = Constants.quad + "texelsLoader";
		public final static String drawCommandTag       = Constants.quad + "drawCommand";
	}
	
	protected static class QuadVerticesLoader extends VerticesLoader {
		private Vector3f bottomLeft, bottomRight, topLeft, topRight;
		
		protected QuadVerticesLoader(String name,  Vector3f topLeft, Vector3f topRight, Vector3f bottomLeft, Vector3f bottomRight, int vertexPositionAttributeLocation) {
			super(name, bottomLeft, vertexPositionAttributeLocation);
			
			this.topLeft = topLeft;
			this.topRight = topRight;
			this.bottomLeft = bottomLeft;
			this.bottomRight = bottomRight;
		}
		
		@Override
		protected float[] getVertexData() {
			return new float[] {
					this.bottomLeft.x, this.bottomLeft.y, this.bottomLeft.z,
					this.bottomRight.x, this.bottomRight.y, this.bottomRight.z,
					this.topLeft.x, this.topLeft.y, this.topLeft.z,
					this.topRight.x, this.topRight.y, this.topRight.z
				};
		}
		
	}
	
	protected static class QuadTexelsLoader extends TexelsLoader {
		
		public QuadTexelsLoader(String name, int textureCoordinatesAttributeLocation) {
			super(name, textureCoordinatesAttributeLocation);
		}
		
		@Override
		protected float[] getTextureCoordinatesData() {
			return new float[] {
				 0,  0,
				+1,  0,
				 0, +1,
				+1, +1
			};
		}
	}
	
	protected Quad(String name, QuadVerticesLoader quadVerticesLoader, Loader... loaders) {
		super(name, quadVerticesLoader, loaders);
	}
	
	public Quad(String name,  Vector3f topLeft, Vector3f topRight, Vector3f bottomLeft, Vector3f bottomRight, int vertexAttributeLocation) {
		this(
				name,
				new QuadVerticesLoader(Constants.vertexPositionLoader, topLeft, topRight, bottomLeft, bottomRight,  vertexAttributeLocation)
		);
	}
	
	public Quad(String name,  Vector3f topLeft, Vector3f topRight, Vector3f bottomLeft, Vector3f bottomRight, int vertexAttributeLocation, int texelAttributeLocation) {
		this(
				name,
				new QuadVerticesLoader(Constants.vertexPositionLoader, topLeft, topRight, bottomLeft, bottomRight,  vertexAttributeLocation),
				new QuadTexelsLoader(Constants.texelsLoader, texelAttributeLocation)
		);
	}
	
	@Override
	protected void drawCommand(GL4 gl) {
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
		
		this.checkForErrors(gl, Constants.drawCommandTag);
	}
}

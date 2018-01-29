package base.objects.model.loaders;

import base.objects.model.Loader;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

import java.nio.IntBuffer;

public abstract class IndicesLoader extends Loader {
	protected static class Constants {
		public static final String indicesLoader = "vertexPositionLoader-";
		public static final String initTag       = Constants.indicesLoader + "init";
	}
	
	public IndicesLoader(String name) {
		super(name);
	}
	
	protected abstract int[] getIndicesData();
	
	@Override
	public void init(GL4 gl) {
		super.init(gl);
		int       indexData[]     = this.getIndicesData();
		IntBuffer indexDataBuffer = Buffers.newDirectIntBuffer(indexData);
		
		gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, super.getBufferID());
		gl.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indexData.length * Integer.BYTES, indexDataBuffer, GL4.GL_STATIC_DRAW);
		
		this.checkForErrors(gl, Constants.initTag);
	}
}

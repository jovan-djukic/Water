package base.objects.model;

import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class CompositeModel extends ModelBase {
	
	private ArrayList<ModelBase> modelBases;
	
	protected CompositeModel(String name, ModelBase ...modelBases) {
		super(name, modelBases);
		
		this.modelBases = new ArrayList<>();
		
		for (int i = 0; i < modelBases.length; i++) {
			this.modelBases.add(modelBases[i]);
		}
	}
	
	@Override
	public void render(GL4 gl, Matrix4f parentTransform, int transformUniformLocation) {
		Matrix4f transform = null;
		if (parentTransform != null && transformUniformLocation >= 0) {
			transform = super.applyTransform(parentTransform);
		}
		
		for (ModelBase modelBase : this.modelBases)	{
			modelBase.render(gl, transform, transformUniformLocation);
		}
	}
}

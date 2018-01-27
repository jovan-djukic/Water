package base.objects.scene;

import java.util.ArrayList;

import base.objects.IRenderable;
import base.objects.IUpdateable;
import base.objects.OpenGLObject;
import base.objects.scene.camera.Camera;
import org.joml.Matrix4f;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

public class Scene extends OpenGLObject implements IRenderable, IUpdateable {
	private static class Constants {
		public static final String name = "Scene";
	}
	protected Camera                 camera;
	protected ArrayList<SceneObject> sceneObjects;
	protected Matrix4f               transform;
	
	public Scene(String name, Camera camera, SceneObject ...sceneObjects) {
		super(name);
		this.camera = camera;
		this.sceneObjects = new ArrayList<SceneObject>();
		
		for (int i = 0; i < sceneObjects.length; i++) {
			this.sceneObjects.add(sceneObjects[i]);
		}
		
		this.transform = new Matrix4f();
		this.transform.identity();
	}
	
	public Scene(Camera camera, SceneObject ...sceneObjects) {
		this(Constants.name, camera, sceneObjects);
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void add(SceneObject sceneObject) {
		sceneObjects.add(sceneObject);
	}
	
	public void update() {
		camera.update();
		for (SceneObject sceneObject : sceneObjects) {
			sceneObject.update();
		}
	}
	
	@Override
	public void render(GL4 gl) {
		for (SceneObject sceneObject : sceneObjects) {
			sceneObject.render(gl, this.transform);
		}
	}
	
	public void destroy(GL4 gl4) {
		for (SceneObject sceneObject : sceneObjects) {
			sceneObject.destroy(gl4);
		}
	}
	
	public void init(GL4 gl4) {
		camera.init();
		for (SceneObject sceneObject : this.sceneObjects) {
			sceneObject.init(gl4);
		}
	}
}

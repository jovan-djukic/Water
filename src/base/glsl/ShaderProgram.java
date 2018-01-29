package base.glsl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import base.objects.CompositeOpenGLObject;
import com.jogamp.opengl.GL4;

import javax.swing.plaf.synth.SynthTabbedPaneUI;

public abstract class ShaderProgram extends CompositeOpenGLObject {
	private static class Messages {
		public static final String	shaderProgramBuildLog	= "Shader program %s build log: \n\n";
		public static final String	shaderProgramNotCreated	= "Shader program %s not created" + System.lineSeparator();
		public static final String	compilingShader			= "Compiling shader %s " + System.lineSeparator();
		public static final String	programLinkStatus		= "Program %s link status: ";
		public static final String	success					= "Success";
		public static final String	failure					= "Failure";
		public static final String	programUniformNotFound	= "Shader program %s : uniform %s not found" + System.lineSeparator();
	}
	
	public enum Status {
		PROGRAM_NOT_COMPLETE, PROGRAM_COMPLETE
	};
	
	private Dictionary<String, Integer>	uniformLocations;
	private ArrayList<String> 			listOfUniforms;
	private ArrayList<Shader>			shaders;
	private int							programID;
	private StringBuilder				buildLog;
	private Status						programStatus;
	
	public ShaderProgram(String name, String uniforms[], Shader ...shaders) {
		super(name, shaders);
		this.programStatus = Status.PROGRAM_NOT_COMPLETE;
		this.uniformLocations = new Hashtable<>();
		this.listOfUniforms = new ArrayList<>();
		this.shaders = new ArrayList<>();
		
		for (int i = 0; i < uniforms.length; i++) {
			this.listOfUniforms.add(uniforms[i]);
		}
		
		for (int i = 0; i < shaders.length; i++) {
			this.shaders.add(shaders[i]);
		}
	}
	
	public ShaderProgram(String name, Shader ...shaders) {
		this(name, new String[0], shaders);
	}
	
	public ShaderProgram addShader(Shader shader) {
		this.shaders.add(shader);
		return this;
	}
	
	public int getProgramID() {
		return this.programID;
	}
	
	public Status getProgramStatus() {
		return this.programStatus;
	}
	
	public String getBuildLog() {
		return this.buildLog.toString();
	}
	
	public int getUniformLocation(String uniformName) {
		Integer location = this.uniformLocations.get(uniformName);
		
		if (location == null) {
			return -1;
		} else {
			return location.intValue();
		}
	}
	
	public void init(GL4 gl) {
		super.init(gl);
		if (!Status.PROGRAM_NOT_COMPLETE.equals(this.programStatus)) {
			return;
		}
		
		if (this.programID == 0) {
			this.programID = gl.glCreateProgram();
		}
		
		this.programStatus = Status.PROGRAM_NOT_COMPLETE;
		
		this.buildLog = new StringBuilder();
		this.buildLog.append(String.format(Messages.shaderProgramBuildLog, super.getName()));
		
		boolean allSuccessful = true;
		
		for (Shader shader : this.shaders) {
			if (!Shader.Status.COMPILED_SUCCESS.equals(shader.getCompilationStatus())) {
				allSuccessful = false;
			}
			
			this.buildLog.append(shader.getCompilationLog());
		}
		
		if (!allSuccessful) {
			this.buildLog.append(String.format(Messages.shaderProgramNotCreated, super.getName()));
			gl.glDeleteProgram(this.programID);
			System.out.println(this.buildLog.toString());
			return ;
		}
		
		for (Shader shader : this.shaders) {
			gl.glAttachShader(this.programID, shader.getShaderID());
		}
		
		gl.glLinkProgram(this.programID);
		
		int[] params = new int[1];
		
		gl.glGetProgramiv(this.programID, GL4.GL_LINK_STATUS, params, 0);
		
		this.buildLog.append(String.format(Messages.programLinkStatus, super.getName()));
		
		if (params[0] == GL4.GL_TRUE) {
			this.buildLog.append(Messages.success);
			this.programStatus = Status.PROGRAM_COMPLETE;
		} else {
			this.buildLog.append(Messages.failure);
		}
		
		this.buildLog.append(System.lineSeparator());
		
		if (Status.PROGRAM_COMPLETE.equals(this.programStatus)) {
			for (String uniform : this.listOfUniforms) {
				int location = gl.glGetUniformLocation(this.programID, uniform);
				if (location > -1) {
					this.uniformLocations.put(uniform, location);
				} else {
					this.buildLog.append(String.format(Messages.programUniformNotFound, super.getName(), uniform));
				}
			}
		}
		
		gl.glGetProgramiv(this.programID, GL4.GL_INFO_LOG_LENGTH, params, 0);
		int infoLogLength = params[0];
		ByteBuffer infoLog = ByteBuffer.allocate(infoLogLength);
		gl.glGetProgramInfoLog(this.programID, infoLogLength, null, infoLog);
		this.buildLog.append(new String(infoLog.array())).append(System.lineSeparator());
		
		System.out.println(this.buildLog.toString());
	}
	
	public void destroy(GL4 gl) {
		super.destroy(gl);
		if (this.programID != 0) {
			gl.glDeleteProgram(this.programID);
			this.programID = 0;
		}
	}
	
	public void activate(GL4 gl) {
		gl.glUseProgram(this.programID);
	}
	
	public void deactivate(GL4 gl) {
		gl.glUseProgram(0);
	}
	
	public int getTransformUniformLocation() {
		return -1;
	}
}

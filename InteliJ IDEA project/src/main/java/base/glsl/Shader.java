package base.glsl;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;

import base.objects.OpenGLObject;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;

public abstract class Shader extends OpenGLObject {
    private static class Messages {
        public static final String	cannotCreateShader	= "Error creating shader %s (%s)";
        public static final String	compilationStatus	= "Shader %s (%s) compilation status: ";
        public static final String	success				= "Success";
        public static final String	failure				= "Failure";
    }

    public enum Status {
        UNCOMPILED, COMPILED_FAILURE, COMPILED_SUCCESS, CANNOT_CREATE_OBJECT
    };

    public static String getShaderSource(Class scope, String shaderFileName) {
        String returnString = null;

        try (InputStream inputStream = scope.getResourceAsStream(shaderFileName)) {
            returnString = new String(inputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnString;
    }

    private int          shaderObjectID;
    private int          shaderType;
    private String[]     shaderSource;
    private StringBuffer compilationLog;
    private Status       compilationStatus;

    protected Shader(String shaderName, int shaderType, String[] shaderSource) {
        super(shaderName);
        this.shaderType = shaderType;
        this.shaderSource = shaderSource;
        this.compilationStatus = Status.UNCOMPILED;
        this.compilationLog = new StringBuffer();
    }

    protected Shader(String shaderName, int shaderType, String shaderSource) {
        this(shaderName, shaderType, new String[] {shaderSource});
    }

    public Shader setShaderSource(String[] shaderSource) {
        if (shaderSource != null) {
            this.shaderSource = shaderSource;
            this.compilationStatus = Status.UNCOMPILED;
        }
        return this;
    }

    public Shader setShaderSource(String shaderSource) {
        String[] source = { shaderSource };
        return this.setShaderSource(source);
    }

    public String getCompilationLog() {
        return this.compilationLog.toString();
    }

    public int getShaderID() {
        return this.shaderObjectID;
    }

    public Status getCompilationStatus() {
        return this.compilationStatus;
    }

    public void init(GL4 gl) {
        super.init(gl);
        if (!Status.UNCOMPILED.equals(this.compilationStatus)) {
            return;
        }

        this.compilationLog = new StringBuffer();

        if (this.shaderObjectID == 0) {
            this.shaderObjectID = gl.glCreateShader(this.shaderType);
            if (gl.glGetError() != GL.GL_NO_ERROR) {
                this.compilationLog.append(String.format(Messages.cannotCreateShader, super.getName(), this.getClass().getName()));
                this.compilationStatus = Status.CANNOT_CREATE_OBJECT;
                return;
            }
        }

        gl.glShaderSource(this.shaderObjectID, this.shaderSource.length, this.shaderSource, null);
        gl.glCompileShader(this.shaderObjectID);

        int[] params = new int[1];

        gl.glGetShaderiv(this.shaderObjectID, GL4.GL_COMPILE_STATUS, params, 0);

        this.compilationLog.append(String.format(Messages.compilationStatus, super.getName(), this.getClass().getName()));

        if (params[0] == 1) {
            this.compilationLog.append(Messages.success);
            this.compilationStatus = Status.COMPILED_SUCCESS;
        } else {
            this.compilationLog.append(Messages.failure);
            this.compilationStatus = Status.COMPILED_FAILURE;
        }

        this.compilationLog.append(System.lineSeparator());

        gl.glGetShaderiv(this.shaderObjectID, GL4.GL_INFO_LOG_LENGTH, params, 0);

        int infoLogLength = params[0];

        ByteBuffer shaderInfoLog = ByteBuffer.allocate(infoLogLength);
        gl.glGetShaderInfoLog(this.shaderObjectID, infoLogLength, null, shaderInfoLog);
        this.compilationLog.append(new String(shaderInfoLog.array())).append(System.lineSeparator());
    }

    @Override
    public void destroy(GL4 gl) {
        super.init(gl);
        gl.glDeleteShader(this.shaderObjectID);
        this.shaderObjectID = 0;
        this.compilationStatus = Status.UNCOMPILED;
    }
}

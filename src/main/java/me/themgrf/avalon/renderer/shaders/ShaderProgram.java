package me.themgrf.avalon.renderer.shaders;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.lights.Light;
import me.themgrf.avalon.entities.lights.PointLight;
import me.themgrf.avalon.utils.Maths;
import me.themgrf.avalon.utils.colour.RGB;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.List;

public abstract class ShaderProgram {

    private static final int MAX_LIGHTS = 4;

    private final String file;
    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int[] locationLightPosition;
    private int[] locationLightColour;
    private int[] locationLightAttenuation;
    private int locationShineDamper;
    private int locationReflectivity;
    private int locationSkyColour;

    private static final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile) {
        file = vertexFile;
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }

    public void getAllUniformLocations() {
        locationTransformationMatrix = getUniformLocation("transformationMatrix");
        locationProjectionMatrix = getUniformLocation("projectionMatrix");
        locationViewMatrix = getUniformLocation("viewMatrix");
        locationShineDamper = getUniformLocation("shineDamper");
        locationReflectivity = getUniformLocation("reflectivity");
        locationSkyColour = getUniformLocation("skyColour");

        locationLightPosition = new int[MAX_LIGHTS];
        locationLightColour = new int[MAX_LIGHTS];
        locationLightAttenuation = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            locationLightPosition[i] = getUniformLocation("lightPosition[" + i + "]");
            locationLightColour[i] = getUniformLocation("lightColour[" + i + "]");
            locationLightAttenuation[i] = getUniformLocation("lightAttenuation[" + i + "]");
        }
    }

    public void loadSkyColour(RGB rgb) {
        loadVector(locationSkyColour, rgb.asVector3f());
    }

    public void loadShineVariables(float damper, float reflectivity) {
        loadFloat(locationShineDamper, damper);
        loadFloat(locationReflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadLights(List<Light> lights) {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                Light light = lights.get(i);
                loadVector(locationLightPosition[i], light.getPosition());
                loadVector(locationLightColour[i], light.getColour().asVector3f());
                if (light instanceof PointLight) {
                    loadVector(locationLightAttenuation[i], ((PointLight) light).getAttenuation());
                } else {
                    loadVector(locationLightAttenuation[i], new Vector3f(1, 0, 0));
                }
            } else {
                loadVector(locationLightPosition[i], new Vector3f(0, 0, 0));
                loadVector(locationLightColour[i], new Vector3f(0, 0, 0));
                loadVector(locationLightAttenuation[i], new Vector3f(1, 0, 0));
            }
        }
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        loadMatrix(locationViewMatrix, viewMatrix);
    }

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    public void start() {
        GL20.glUseProgram(programID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String varName) {
        GL20.glBindAttribLocation(programID, attribute, varName);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadBoolean(int location, boolean value) {
        GL20.glUniform1f(location, value ? 1 : 0);
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader! " + file);
            System.exit(-1);
        }
        return shaderID;
    }

    public String getFile() {
        return file;
    }
}

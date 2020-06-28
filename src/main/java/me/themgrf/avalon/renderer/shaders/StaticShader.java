package me.themgrf.avalon.renderer.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {

    private static final String PATH = "src/main/java/me/themgrf/avalon/renderer/shaders/";
    private static final String VERTEX_FILE = PATH + "vertexShader.txt";
    private static final String FRAGMENT_FILE = PATH + "fragmentShader.txt";

    private int locationTransformationMatrix;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    protected void getAlluniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }
}

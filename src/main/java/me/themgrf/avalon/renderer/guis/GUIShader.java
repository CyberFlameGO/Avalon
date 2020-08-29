package me.themgrf.avalon.renderer.guis;

import me.themgrf.avalon.renderer.shaders.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;

public class GUIShader extends ShaderProgram {

    private static final String PATH = "src/main/java/me/themgrf/avalon/renderer/shaders/guis/";
    private static final String VERTEX_FILE = PATH + "guiVertexShader.txt";
    private static final String FRAGMENT_FILE = PATH + "guiFragmentShader.txt";

    private int location_transformationMatrix;

    public GUIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    @Override
    public void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }



}

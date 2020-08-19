package me.themgrf.avalon.renderer.shaders;

import me.themgrf.avalon.renderer.shaders.uniforms.UniformMatrix;
import me.themgrf.avalon.renderer.shaders.uniforms.UniformVector2;
import me.themgrf.avalon.renderer.shaders.uniforms.UniformVector3;

public class TerrainShader extends ShaderProgram {

    private static final String PATH = "src/main/java/me/themgrf/avalon/renderer/shaders/";
    private static final String VERTEX_FILE = PATH + "terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = PATH + "terrainFragmentShader.txt";

    public UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
    public UniformVector3 lightDirection = new UniformVector3("lightDirection");
    public UniformVector3 lightColour = new UniformVector3("lightColour");
    public UniformVector2 lightBias = new UniformVector2("lightBias");

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public TerrainShader(String vertexFile, String fragmentFile) {
        super(PATH + vertexFile, PATH + fragmentFile);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
    }

}

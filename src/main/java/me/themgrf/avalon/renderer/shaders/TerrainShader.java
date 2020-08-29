package me.themgrf.avalon.renderer.shaders;

public class TerrainShader extends ShaderProgram {

    private static final String PATH = "src/main/java/me/themgrf/avalon/renderer/shaders/";
    private static final String VERTEX_FILE = PATH + "terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = PATH + "terrainFragmentShader.glsl";

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
    }

}

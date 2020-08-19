package me.themgrf.avalon.terrain;

import me.themgrf.avalon.entities.Camera;
import me.themgrf.avalon.entities.Light;
import me.themgrf.avalon.renderer.renderers.TerrainRenderer;
import me.themgrf.avalon.utils.opengl.VAO;

public class LowPolyTerrain {

    private final VAO vao;
    private final int vertexCount;
    private final TerrainRenderer renderer;

    public LowPolyTerrain(VAO vao, int vertexCount, TerrainRenderer renderer){
        this.vao = vao;
        this.vertexCount = vertexCount;
        this.renderer = renderer;
    }

    public int getVertexCount(){
        return vertexCount;
    }

    public VAO getVao(){
        return vao;
    }

    public void render(Camera camera, Light light){
        renderer.render(this, camera, light);
    }

    public void delete(){
        vao.delete(true);
    }

}

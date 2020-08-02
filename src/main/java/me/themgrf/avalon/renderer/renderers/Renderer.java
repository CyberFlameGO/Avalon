package me.themgrf.avalon.renderer.renderers;

import me.themgrf.avalon.renderer.shaders.ShaderProgram;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public abstract class Renderer {

    private final ShaderProgram shader;

    public Renderer(ShaderProgram shader) {
        this.shader = shader;
    }

    public ShaderProgram getShader() {
        return shader;
    }

    public abstract void prepare(Object object);

    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public abstract void loadModelMatrix(Object object);
}

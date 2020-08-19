package me.themgrf.avalon.renderer.shaders.uniforms;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

public class UniformMatrix extends Uniform {

    private static final FloatBuffer MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);

    public UniformMatrix(String name) {
        super(name);
    }

    public void loadMatrix(Matrix4f matrix){
        matrix.store(MATRIX_BUFFER);
        MATRIX_BUFFER.flip();
        GL20.glUniformMatrix4(super.getLocation(), false, MATRIX_BUFFER);
    }

}

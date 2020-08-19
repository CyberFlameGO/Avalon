package me.themgrf.avalon.renderer.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;

public class UniformVector2 extends Uniform {

    private float currentX;
    private float currentY;
    private boolean used = false;

    public UniformVector2(String name) {
        super(name);
    }

    public void loadVector2(Vector2f vector) {
        loadVector2(vector.x, vector.y);
    }

    public void loadVector2(float x, float y) {
        if (!used || x != currentX || y != currentY) {
            this.currentX = x;
            this.currentY = y;
            used = true;
            GL20.glUniform2f(super.getLocation(), x, y);
        }
    }

}

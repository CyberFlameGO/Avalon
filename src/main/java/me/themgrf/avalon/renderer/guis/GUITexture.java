package me.themgrf.avalon.renderer.guis;

import org.lwjgl.util.vector.Vector2f;

public class GUITexture {

    private final int texture;
    private final Vector2f position, scale;

    public GUITexture(int texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
    }

    public int getTextureID() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
}

package me.themgrf.avalon.renderer.guis.elements;

import me.themgrf.avalon.renderer.guis.GUITexture;

public class GUIElement {

    private final String id;
    private final GUITexture texture;

    public GUIElement(String id, GUITexture texture) {
        this.id = id;
        this.texture = texture;
    }

    public String getId() {
        return id;
    }

    public GUITexture getTexture() {
        return texture;
    }
}

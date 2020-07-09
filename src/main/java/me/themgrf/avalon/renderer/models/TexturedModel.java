package me.themgrf.avalon.renderer.models;

import me.themgrf.avalon.renderer.textures.ModelTexture;

public class TexturedModel {

    private final RawModel rawModel;
    private final ModelTexture modelTexture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.rawModel = model;
        this.modelTexture = texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getModelTexture() {
        return modelTexture;
    }
}

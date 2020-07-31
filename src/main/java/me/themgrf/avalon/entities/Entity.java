package me.themgrf.avalon.entities;

import me.themgrf.avalon.renderer.models.RawModel;
import me.themgrf.avalon.renderer.models.TexturedModel;
import me.themgrf.avalon.utils.Location;
import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.util.vector.Vector3f;

public class Entity {

    private TexturedModel texturedModel;
    private RawModel model;
    private Location location;
    private float scale;

    public Entity(RawModel model, Location location, float scale) {
        this.model = model;
        this.location = location;
        this.scale = scale;
    }

    public Entity(TexturedModel texturedModel, Location location, float scale) {
        this.texturedModel = texturedModel;
        this.model = texturedModel.getRawModel();
        this.location = location;
        this.scale = scale;
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public void setTexturedModel(TexturedModel texturedModel) {
        this.texturedModel = texturedModel;
    }

    public RawModel getModel() {
        return model;
    }

    public void setModel(RawModel model) {
        this.model = model;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}

package me.themgrf.avalon.renderer;

import me.themgrf.avalon.utils.Rotation;
import org.lwjgl.util.vector.Vector3f;

/**
 * Transformation class that amalgamates an entities
 * translation, rotation and scale.
 */
public class EntityTransformation {

    private Vector3f translation;
    private Rotation rotation;
    private float scale;

    public EntityTransformation(Vector3f translation, Rotation rotation, float scale) {
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}

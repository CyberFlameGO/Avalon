package me.themgrf.avalon.renderer;

import org.lwjgl.util.vector.Vector3f;

/**
 * Transformation class that amalgamates an entities
 * translation, rotation and scale.
 */
public class EntityTransformation {

    private Vector3f translation;
    private float rx, ry, rz;
    private int scale;

    public EntityTransformation(Vector3f translation, float rx, float ry, float rz, int scale) {
        this.translation = translation;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.scale = scale;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public float getRx() {
        return rx;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public float getRy() {
        return ry;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }

    public float getRz() {
        return rz;
    }

    public void setRz(float rz) {
        this.rz = rz;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
